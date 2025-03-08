import React from "react";
import {IoIosStar} from "react-icons/io";
import {Rate} from "antd";
import './style.css'

const RatePage : React.FC = () => {

    return (
        <>
            <div className={'flex justify-center items-center mt-[60px]'}>
                <div className={'container'}>
                    <div className={'border-2 border-main p-4 rounded-xl'}>
                        <div className={'flex justify-between px-[40px]'}>
                            <div className={'flex items-center gap-2'}>
                                <p className={'text-main'}>Đánh giá chung : </p>
                                <span className={'text-white'}>4.9</span>
                                <div className={'text-main'}><IoIosStar/></div>
                            </div>
                            <div>
                                <div className={'cursor-pointer'}>
                                    <button className={'text-white'}>Xem Thêm</button>
                                    <div className={'h-[1px] w-full bg-white'}></div>
                                </div>
                            </div>
                        </div>
                        <div className={'h-[1px] w-full bg-main my-4'}></div>
                        <div className={'max-h-[200px] overflow-y-hidden'}>
                            <div
                                className={'flex justify-between items-center px-[40px] border-b-2 border-gray-800 pb-4'}>
                                <div>
                                    <div>
                                        <Rate className={'text-xs'} value={5} disabled/>
                                    </div>
                                    <div>
                                        <p className={'text-main text-[16px]'}>Phim hay quá</p>
                                    </div>
                                    <div>
                                        <p className={'text-xs text-white'}>10/03/2003</p>
                                    </div>
                                </div>
                                <div>
                                    <p className={'text-white'}>Dao Phan Quoc Hoai</p>
                                </div>
                            </div>
                            <div
                                className={'flex justify-between items-center px-[40px] border-b-2 border-gray-800 pb-4'}>
                                <div>
                                    <div>
                                        <Rate className={'text-xs'} value={5} disabled/>
                                    </div>
                                    <div>
                                        <p className={'text-main text-[16px]'}>Phim hay quá</p>
                                    </div>
                                    <div>
                                        <p className={'text-xs text-white'}>10/03/2003</p>
                                    </div>
                                </div>
                                <div>
                                    <p className={'text-white'}>Dao Phan Quoc Hoai</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default RatePage;