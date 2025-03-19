import React, {useEffect, useRef, useState} from "react";
import {FaChevronDown, FaChevronUp} from "react-icons/fa";
import { motion } from "framer-motion";
import {fetchTheater} from "../../Helper/Helper.ts";
import {Spin} from "antd";
import {toast} from "react-toastify";

interface Theater {
    id: string,
    nameBranch : string,
    address : string,
}
interface Collasp {
    id : string,
    open : boolean
}
interface Sub {
    id : string,
    name : string
}
interface SelectMovie {
    branchId : string,
    nameBranch : string,
    subId : string
}
interface MovieTheaterProps {
    subs: Sub[];
    setSelect : (arg :SelectMovie) => void,
    setStep : (arg : number) => void,
    select : SelectMovie,
    filmShow : FilmShow[]
    filmShowId : number,
    setFilmShowId : (arg : number) => void
}
interface FilmShow {
    id : number,
    timeEnd : string,
    timeStart : string,
    subFilmId : string,
    roomId : string,
    timestamp : string,
}

const MovieTheater : React.FC<MovieTheaterProps> = ( props ) => {

    const [listTheater, setListTheater] = useState<Theater[]>([]);
    // const [listAddress, setListAddress] = useState<string[]>([]); /TODO : can phat trien
    const [loading, setLoading] = useState<boolean>(false);
    const [controller, setController] = useState<Collasp[]>([]);
    const theaterRef = useRef(null);

    // bat param render
    const getQueryParams = () => {
        return new URLSearchParams(window.location.search);
    };


    useEffect(() => {
        handleFetchTheater();
    }, []);
    useEffect(() => {
        let list : Collasp[] = [];
        listTheater.forEach( (item, index) => {
            list[index] = {
                id : item.id,
                open : false
            }
        })
        const query = getQueryParams();
        const branchId = query.get("branchId");
        console.log(branchId)
        if( branchId != null ){
            list =list.map( (item) => {
                if( item.id == branchId ){
                    return {
                        id : branchId,
                        open : true
                    }
                }
                return item;
            })
        }
        setController(list);
        const subId = query.get("subId");
        const namebranch = query.get("namebranch");
        if( subId != null && namebranch != null && branchId != null ){
            props.setSelect({
                subId: subId,
                nameBranch : namebranch,
                branchId: branchId
            })
            theaterRef.current?.scrollIntoView({ behavior: "smooth" });
        }
    }, [listTheater]);

    const handleFetchTheater = async () => {
        setLoading(true);
        const response = await fetchTheater();
        setLoading(false);
        if( response.status !== 200 ){
            toast.error(<p className={'w-full'}>Không thể tải dữ liệu?</p>)
            return;
        }
        setListTheater(response.data.data);
    }

    const handleOpen = ( id : string) => {
        const item : Collasp = controller.filter(i => i.id ===id)[0];
        if( item !== undefined){
            setController([...controller.filter(i => i.id !==id), {...item, open: !item.open}]);
        }
    }

    const handleSelectFilmId = (id : number) => {
        props.setFilmShowId(id);
        props.setStep( (prevStep : number) => {
            return prevStep + 1;
        });
    }

    return (
        <>
            <Spin tip={"Đang tải..."} spinning={loading} size={"default"}>
                <div ref={theaterRef} className={'flex justify-center items-center mt-[60px]'}>
                    <div className={'container'}>
                        <div>
                            <div className={'flex justify-center'}>
                                <p className={'text-3xl uppercase font-bold text-white'}>Danh Sách Rạp</p>
                            </div>
                            <div className={'flex flex-col mt-[40px] gap-6'}>
                                {
                                    listTheater != undefined &&
                                    listTheater.map( (item,index) => {
                                        const foundItem = controller.find(col => col.id === item.id);
                                        const open = foundItem ? foundItem.open : false;
                                        return (
                                            <motion.div
                                                key={index}
                                                className={`border-2 border-main pt-[20px] rounded-[10px] bg-main transition-all duration-700 ease-in-out overflow-hidden`}
                                                initial={{height: 80}}
                                                animate={{height: !open  ? 80 : 280}}
                                                transition={{duration: 0.3, ease: "easeInOut"}}
                                            >
                                                <div className={'flex justify-between px-[40px] items-center'}
                                                     onClick={() => handleOpen(item.id)}>
                                                    <p className={`text-white text-2xl`}>{item.nameBranch}</p>
                                                    <div className={`text-2xl text-white p-2 cursor-pointer`}>
                                                        {
                                                            open ?
                                                                <FaChevronDown/>
                                                                :
                                                                <FaChevronUp/>
                                                        }
                                                    </div>
                                                </div>
                                                <div
                                                    className={`bg-foreground p-4 px-[40px] rounded-xl overflow-hidden mt-[30px] flex flex-col gap-4`}>
                                                    <span
                                                        className={'text-white text-medium font-bold'}>Địa chỉ : {item.address}</span>
                                                    <div className={'flex gap-4'}>
                                                        {
                                                            props.subs.length === 0 &&
                                                            <div>
                                                                <p className={'text-white text-medium'}>Sẽ ra mắt sơm thôi !</p>
                                                            </div>
                                                        }
                                                        {
                                                            props.subs &&
                                                            props.subs.map((sub, index) => {
                                                                return (
                                                                    <div key={index}>
                                                                        <div
                                                                             className={` ${props.select.subId === sub.id && item.id === props.select.branchId ? 'bg-white text-main' : 'bg-main hover:bg-white hover:text-main'} px-2 py-1 rounded-xl border-2 border-main transition-all duration-300 cursor-pointer`}
                                                                             onClick={() => props.setSelect({
                                                                                 subId: sub.id,
                                                                                 nameBranch : item.nameBranch,
                                                                                 branchId: item.id
                                                                             })}
                                                                        >
                                                                            <span>{sub.name}</span>
                                                                        </div>
                                                                        {
                                                                            props.select.subId === sub.id && item.id === props.select.branchId
                                                                            &&
                                                                            <>
                                                                                {
                                                                                    props.filmShow.length > 0 ?
                                                                                    <>
                                                                                        <div className={'mt-[10px]'}>
                                                                                            <p className={'text-main'}>Xuất
                                                                                                chiếu</p>
                                                                                            <div
                                                                                                className={'mt-[10px] flex gap-3'}>
                                                                                                {
                                                                                                    props.filmShow.map( item => {
                                                                                                        return (
                                                                                                            <div key={item.id}
                                                                                                                 onClick={() => handleSelectFilmId(item.id)}
                                                                                                                className={`${item.id === props.filmShowId ? 'bg-white text-main' : 'text-white bg-main' } px-2 py-1 border-2 border-main rounded-xl cursor-pointer hover:bg-white text-center`}>
                                                                                                                <p>
                                                                                                                    <span>{item.timeStart.slice(0,5)}</span> - <span>{item.timeEnd.slice(0,5)}</span>
                                                                                                                </p>
                                                                                                            </div>
                                                                                                        )
                                                                                                    })
                                                                                                }
                                                                                            </div>
                                                                                        </div>
                                                                                    </>
                                                                                        :
                                                                                        <>
                                                                                            <div className={'mt-[10px] text-main'}>
                                                                                                <p>Chưa có lịch chiếu</p>
                                                                                            </div>
                                                                                        </>
                                                                                }
                                                                            </>
                                                                        }
                                                                    </div>
                                                                )
                                                            })
                                                        }
                                                    </div>
                                                </div>
                                            </motion.div>
                                        )
                                    })
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </Spin>
        </>
    )
}
export default MovieTheater;