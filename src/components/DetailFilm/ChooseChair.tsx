import React from "react";
import {FaMinus, FaPlus} from "react-icons/fa";

const ChooseChair : React.FC = () => {
    return (
        <>
            <div className={"flex justify-center items-center mt-[80px]"}>
                <div className={'container'}>
                    <div>
                        <div>
                            <div className={'flex justify-center items-center'}>
                                <p className={'text-3xl text-white font-bold uppercase'}>Chọn loại vé</p>
                            </div>
                            <div className={'grid grid-cols-3 gap-4 mt-[40px]'}>
                                <div className={'col-span-1 border-border border-2 p-4 flex flex-col gap-2'}>
                                    <p className={'text-white'}>Nguười lớn</p>
                                    <p className={'text-main font-bold'}>Đơn</p>
                                    <p className={'text-white'}>45,000Đ</p>
                                    <div>
                                        <div className={'inline-flex gap-4 items-center bg-gray-400 px-2 py-1'}>
                                            <span className={'text-white text-xs cursor-pointer'}><FaMinus/></span>
                                            <span className={'text-white'}>0</span>
                                            <span className={'text-white text-xs cursor-pointer'}><FaPlus/></span>
                                        </div>
                                    </div>
                                </div>
                                <div className={'col-span-1 border-border border-2 p-4 flex flex-col gap-2'}>
                                    <p className={'text-white'}>Nguười lớn</p>
                                    <p className={'text-main font-bold'}>Đơn</p>
                                    <p className={'text-white'}>45,000Đ</p>
                                    <div>
                                        <div className={'inline-flex gap-4 items-center bg-gray-400 px-2 py-1'}>
                                            <span className={'text-white text-xs cursor-pointer'}><FaMinus/></span>
                                            <span className={'text-white'}>0</span>
                                            <span className={'text-white text-xs cursor-pointer'}><FaPlus/></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className={'mt-[80px]'}>
                            <div className={'flex justify-center items-center'}>
                                <p className={'text-3xl font-bold uppercase text-white'}>Chọn ghế</p>
                            </div>
                            <div className={'mt-[40px] flex flex-col justify-center items-center '}>
                                <div>
                                    <img src={'/public/screen.png'} alt={'screen'} className={'w-[500px] h-auto text-black'}/>
                                </div>
                                <div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
};
export default ChooseChair;