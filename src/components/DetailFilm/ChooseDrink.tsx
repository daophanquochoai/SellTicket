import React, {useEffect, useRef, useState} from "react";
import {FaMinus, FaPlus} from "react-icons/fa";
import {Spin} from "antd";
import {fetchDish} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";

interface TypeDish {
    id : string,
    name : string,
    dishes : Dish[]
}
interface Dish  {
    id : string,
    price : 0,
    name : string,
    image : string,
}
interface Count {
    id : string,
    price : number,
    count : number,
    name : string
}
interface Props {
    setSum : ( arg : number ) => void,
    counter: Count[],
    setCounter : (arg : Count[]) => void
}

const ChooseDrink : React.FC<Props> = ( props ) => {

    const [loadingTypeDish, setLoadingTypeDish] = useState<boolean>(false);
    const [dish, setDish] = useState<TypeDish[]>([]);
    const {counter, setCounter} = props;
    const sectionRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        handleFetchTypeDish();
    }, []);
    useEffect(() => {
        let temp = 0;
        counter.forEach( item => {
            temp += item.price*item.count;
        })
        props.setSum(temp);
    }, [counter]);

    // fetch do an
    const handleFetchTypeDish = async () => {
        setLoadingTypeDish(true);
        const response = await fetchDish();
        setLoadingTypeDish(false);
        if( response.status !== 200 ){
            toast.warning(<p>Không thể tài đồ ăn rồi</p>);
            return;
        }
        const list : Count[] = [];
        response.data.data.forEach( item => {
            item.dishes.forEach( dish => {
                list[list.length] = {
                    id : dish.id,
                    price : dish.price,
                    count : 0
                }
            })
        })
        setCounter(list);
        setDish(response.data.data);
    }

    const handleRise : (arg : string) => void = ( id : string, name : string) => {
        const item = counter.find(item => item.id === id);
        if( item === undefined){
            return;
        }
        setCounter([...counter.filter(item=>item.id!==id), { id: id, price : item.price, count : item.count + 1, name: name}])
    }
    const handleDown : (arg : string) => void = ( id : string, name : string ) => {
        const item = counter.find(item => item.id === id);
        if( item === undefined){
            return;
        }
        if( item.count === 0 ){
            return;
        }
        setCounter([...counter.filter(item=>item.id!==id), { id: id, price : item.price, count : item.count - 1, name: name}])
    }

    return (
        <>
            <div ref={sectionRef} className={'flex items-center justify-center mt-[80px]'}>
                <div className={'container'}>
                    <div>
                        <div className={'flex items-center justify-center'}>
                            <p className={'uppercase text-3xl font-bold text-white'}>Chọn bắp nước</p>
                        </div>
                        <div className={'mt-[40px]'}>
                            <Spin tip={"Đang tải..."} spinning={loadingTypeDish}>
                                {
                                    dish &&
                                    dish.map( (item, index) => {
                                        return (
                                            <div key={index}>
                                                <div className={'flex justify-center items-center'}>
                                                    <p className={'text-2xl text-main uppercase font-medium'}>{item.name}</p>
                                                </div>
                                                <div className={'grid grid-cols-3 gap-4  mt-[40px]'}>
                                                    {
                                                        item.dishes.map( (i , indexDish) => {
                                                            const temp = counter.find(dish=> i.id === dish.id);
                                                            return (
                                                                <div key={indexDish} className={'flex border-border border-2 gap-4 col-span-1 group h-[200px] justify-center items-center cursor-pointer'}>
                                                                    <div className={'w-1/3 overflow-hidden'}>
                                                                        <img
                                                                            src={i.image || null}
                                                                            alt={'drink'}
                                                                            className={'group-hover:scale-105 group-hover:rotate-1 h-[150px] w-auto transition-all duration-300'}
                                                                        />
                                                                    </div>
                                                                    <div className={'p-2 gap-2 flex flex-col'}>
                                                                        <p className={'text-xl group-hover:text-main transition-all duration-300'}>{i.name}</p>
                                                                        <span className={'text-main'}>{i.price.toLocaleString()}Đ</span>
                                                                        <div>
                                                                            <div className={'inline-flex gap-4 items-center bg-gray-400 px-2 py-1'}>
                                                                                <button className={'text-white text-xs cursor-pointer'}
                                                                                      onClick={() => handleDown(i.id, i.name)}
                                                                                ><FaMinus/></button>
                                                                                <span className={'text-white'}>{temp.count || 0}</span>
                                                                                <button className={'text-white text-xs cursor-pointer'}
                                                                                      onClick={() => handleRise(i.id, i.name)}
                                                                                ><FaPlus/></button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            )
                                                        })
                                                    }
                                                </div>
                                            </div>
                                        )
                                    })
                                }
                            </Spin>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};
export default ChooseDrink;