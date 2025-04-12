import React, {lazy, useEffect, useState} from "react";
import {TbTheater} from "react-icons/tb";
import {PiFilmReel, PiFilmSlateBold} from "react-icons/pi";
import {Spin} from "antd";
import {fetchBranch, fetchFilmAll} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";

const FilmShow = lazy(() => import("./FilmShow.tsx"));

interface Branch {
    address : string,
    id : string,
    nameBranch : string,
    status : string
}
interface Film {
    id: string,
    name : string,
    age : 0,
    image : string,
    sub : Sub[],
    nation : string,
    duration : string,
    description : string,
    content : string,
    trailer : string,
    typeFilms : TypeFilm[],
    status : string
}
interface Sub {
    id : string,
    name : string
}
interface TypeFilm {
    id : string,
    name : string,
    active : string
}
interface Time {
    date : string,
    dateString : string
}


const initFilm : Film = {
    age : 0,
    content : "",
    description : "",
    duration : "",
    id : "",
    image : "",
    name : "",
    nation : "",
    status : "",
    sub : [],
    trailer : "",
    typeFilms : []
}


const TheaterMovie : React.FC = () => {

    const [loading, setLoading] = useState<boolean>(false);
    const [branchs, setBranchs] = useState<Branch[]>([]);
    const [films, setFilms] = useState<Film[]>([]);
    const [filmSelected, setFilmSelected] = useState<Film>(initFilm)

    // giu select khi chon
    const [selectFilm, setSelectFilm] = useState<string>('-1');
    const [selectBranch, setSelectBranch] = useState<string>('-1');
    const [selectTime, setSelectTime] = useState<string>('0');

    const [time, setTime] = useState<Time[]>([]);

    useEffect(() => {
        const today = new Date();
        const list:Time[] = [];

        for (let i = 0; i < 4; i++) {
            const nextDay = new Date();
            nextDay.setDate(today.getDate() + i);
            let dayOfWeek = nextDay.toLocaleDateString('vi-VN', { weekday: 'long' });
            const dayMonth = nextDay.toLocaleDateString('vi-VN', { day: 'numeric', month: 'numeric', year :'numeric' });
            const [day, month, year] = dayMonth.split("/");
            const formattedDate = `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
            if( i === 0 ) dayOfWeek = "Hôm nay"
            list[i] = {
                date : formattedDate,
                dateString : dayOfWeek
            }
        }
        setTime(list)
        setSelectTime(list[0].date)
    }, []);

    useEffect(() => {
        handleFetchApi();
    }, []);


    useEffect(() => {
        if( selectFilm != '-1'){
            setFilmSelected(films.find( item => item.id === selectFilm))
        }
    }, [selectFilm]);

    const handleFetchApi = async () => {
        setLoading(true);
        const responseBranch = await fetchBranch();
        const responseFilm = await fetchFilmAll();
        setLoading(false);
        if( responseBranch.status !== 200 ){
            toast.error("Tải chi nhánh không thành công")
            return;
        }
        if( responseFilm.status !== 200 ){
            toast.error("Tải phim không thành công")
            return;
        }
        setBranchs(responseBranch.data.data);
        setFilms(responseFilm.data.data);

    }

    return (
        <>
            <Spin tip={"Đang tải..."} spinning={loading}>
                <div className={'flex justify-center items-center mt-[60px]'}>
                    <div className={'container'}>
                        <div className={'flex flex-col'}>
                            <div className={'flex gap-[20px]'}>
                                <div className={'flex flex-col flex-1 px-4 py-2 border-2 border-main rounded-xl'}>
                                    <div className={'flex justify-between items-center'}>
                                        <p className={'text-main text-medium'}>1. Rạp</p>
                                        <div className={'text-2xl text-main'}><TbTheater/></div>
                                    </div>
                                    <div className={'mt-[10px]'}>
                                        <select
                                            className={'w-full outline-0 rounded py-1 px-4 bg-foreground text-white font-bold cursor-pointer'}
                                                aria-placeholder={"Chon"}
                                            onChange={(e) => setSelectBranch(e.target.value)}
                                        >
                                            {selectBranch == '-1' && <option >Chọn chi nhánh</option>}
                                            {
                                                branchs && branchs.map( (branch) => {
                                                    return (
                                                        <option key={branch.id} value={branch.id}>{branch.nameBranch}</option>
                                                    )
                                                })
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className={'flex flex-col flex-1 px-4 py-2 border-2 border-main rounded-xl'}>
                                    <div className={'flex justify-between items-center'}>
                                        <p className={'text-main text-medium'}>2. Phim</p>
                                        <div className={'text-2xl text-main'}><PiFilmReel/></div>
                                    </div>
                                    <div className={'mt-[10px]'}>
                                        <select
                                            disabled={selectBranch == -1}
                                            className={'w-full outline-0 rounded py-1 px-4 bg-foreground text-white font-bold cursor-pointer'}
                                            onChange={(e) => setSelectFilm(e.target.value)}
                                        >
                                            {selectFilm == '-1' && <option >Chọn phim</option>}
                                            {
                                                films && films.map( (film) => {
                                                    return (
                                                        <option key={film.id} value={film.id}>{film.name}</option>
                                                    )
                                                })
                                            }
                                        </select>
                                    </div>
                                </div>
                                <div className={'flex flex-col flex-1 px-4 py-2 border-2 border-main rounded-xl'}>
                                    <div className={'flex justify-between items-center'}>
                                        <p className={'text-main text-medium'}>3. Thời gian</p>
                                        <div className={'text-2xl text-main'}><PiFilmReel/></div>
                                    </div>
                                    <div className={'mt-[10px]'}>
                                        <select
                                            className={'w-full outline-0 rounded py-1 px-4 bg-foreground text-white font-bold cursor-pointer'}
                                            onChange={(e) => setSelectTime(e.target.value)}
                                        >
                                            {
                                                time.map( (i) => {
                                                    return <option key={i.date} value={i.date}>{i.dateString}  {i.date}</option>
                                                })
                                            }
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <hr className={'my-[20px]'}/>
                            {
                                filmSelected.id !== "" &&
                                <div>
                                    <div className={'flex gap-8'}>
                                        <img
                                            src={filmSelected.image || null}
                                            className={'w-[400px] h-auto'}
                                        />
                                        <div className={'w-full'}>
                                            <div className={'w-full'}>
                                                {
                                                    filmSelected.sub.length > 0 ?
                                                        <>
                                                            {
                                                                filmSelected.sub.map( s => {
                                                                    return (
                                                                        <div key={s.id}>
                                                                            <div className={'flex flex-col gap-2 mb-[10px]'}>
                                                                                <div
                                                                                    className={'border-border border-2 flex gap-4 w-full items-center px-4 py-2'}>
                                                                                    <div className={'text-textCol text-3xl'}>
                                                                                        <PiFilmSlateBold/>
                                                                                    </div>
                                                                                    <p className={'text-textCol text-medium'}>{s.name}</p>
                                                                                </div>
                                                                            </div>
                                                                            <FilmShow time={selectTime} nameBranch={branchs.find(i=>i.id==selectBranch).nameBranch} filmId={selectFilm} branchId={selectBranch} subId={s.id}/>
                                                                        </div>
                                                                    )
                                                                })
                                                            }
                                                        </>
                                                        :
                                                        <>
                                                            <div
                                                                className={'border-border border-2 flex gap-4 w-full items-center px-4 py-2'}>
                                                                <div className={'text-textCol text-3xl'}>
                                                                    <PiFilmSlateBold/>
                                                                </div>
                                                                <p className={'text-textCol text-medium'}>Chưa có xuất chiếu</p>
                                                            </div>
                                                        </>
                                                }
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            }
                        </div>
                    </div>
                </div>
            </Spin>
        </>
    )
};
export default TheaterMovie;