import React, {useEffect, useState} from "react";
import {IoIosStar} from "react-icons/io";
import {Rate, Spin} from "antd";
import './style.css'
import {useParams} from "react-router-dom";
import {fetchComment} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import ModalRate from "./ModalRate.tsx";

interface RateCommon {
    comments : Rate[],
    rate : number
}
interface Rate {
    id : string,
    star : number,
    content : string,
    timeStamp : string,
    customer : Customer,
}
interface Customer {
    name : string,
    phoneNumber : string,
    email : string,
    userName : string,
    password : string,
}
const initRateCommon = {
    comments : [],
    rate : 0,
}

const RatePage : React.FC = () => {

    const param = useParams();
    const [loading, setLoading] = useState<boolean>(false);
    const [rateList, setRateList] = useState<RateCommon>(initRateCommon);

    const [isOpen, setIsOpen] = useState<boolean>(false);

    useEffect(() => {
        handleFetchRate();
    }, []);

    const handleFetchRate = async () => {
        if( param.id === undefined ){
            toast.error(<p className={'w-full'}>Tải đánh giá lỗi</p>)
            return;
        }
        setLoading(true);
        const response = await fetchComment(param.id);
        setLoading(false);
        if( response.status != 200 ){
            toast.error(<p className={'w-full'}>{response.response.data.message}</p>);
            return;
        }
        setRateList(response.data.data.data);
    }

    return (
        <>
            <Spin tip={"Đang tải..."} spinning={loading} size={"default"}>
                <div className={'flex justify-center items-center mt-[60px]'}>
                    <div className={'container'}>
                        <div className={'border-2 border-main p-4 rounded-xl'}>
                            <div className={'flex justify-between px-[40px]'}>
                                <div className={'flex items-center gap-2'}>
                                    <p className={'text-main'}>Đánh giá chung : </p>
                                    <span className={'text-white'}>{rateList.rate}</span>
                                    <div className={'text-main'}><IoIosStar/></div>
                                </div>
                                <div>
                                    <div className={'cursor-pointer'}>
                                        <button onClick={()=>setIsOpen(true)} className={'text-white'}>Xem Thêm</button>
                                        <div className={'h-[1px] w-full bg-white'}></div>
                                    </div>
                                </div>
                            </div>
                            <div className={'h-[1px] w-full bg-main my-4'}></div>
                            <div className={'max-h-[200px] overflow-y-hidden'}>
                                {
                                    rateList && rateList.comments.map( item => {
                                        return (
                                            <>
                                                <div
                                                    className={'flex justify-between items-center px-[40px] border-b-2 border-gray-800 pb-4'}>
                                                    <div>
                                                        <div>
                                                            <Rate className={'text-xs'} value={5} disabled/>
                                                        </div>
                                                        <div>
                                                            <p className={'text-main text-[16px]'}>{item.content}</p>
                                                        </div>
                                                        <div>
                                                            <p className={'text-xs text-white'}>{item.timeStamp}</p>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <p className={'text-white'}>{item.customer.name}</p>
                                                    </div>
                                                </div>
                                            </>
                                        )
                                    })
                                }
                                {
                                    rateList.comments.length === 0 &&
                                    <div className={'flex justify-center'}>
                                        <p>Chưa có bình luận nào?</p>
                                    </div>
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </Spin>
            <ModalRate isOpen={isOpen} setIsOpen={setIsOpen} filmId={param.id}/>
        </>
    )
}

export default RatePage;