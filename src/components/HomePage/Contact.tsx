import React from "react";
import {IoLocationSharp, IoMailSharp} from "react-icons/io5";
import {FaPhoneAlt} from "react-icons/fa";

const Contact : React.FC = () => {
    return(
        <>
            <div className={'flex items-center justify-center mt-[80px]'}>
                <div className={'container'}>
                    <div className={'flex gap-[100px]'}>
                        <div className={'flex-1 h-full mt-[40px]'}>
                            <div className={'flex justify-center'}>
                                <p className={'uppercase text-2xl font-bold text-main'}>Liên hệ với chúng tôi</p>
                            </div>
                            <div className={'flex items-center justify-start mt-[80px]'}>
                                <div
                                    className={'flex-1 justify-center flex relative bg-main pl-[100px] pr-[30px] py-[15px] ml-[100px] rounded-tr-2xl rounded-br-2xl'}>
                                    <div>
                                        <img src={'/public/facebook.png'} alt={'facebook icon'}
                                             className={'w-[150px] h-auto object-cover absolute top-[-50px] left-[-40px]'}/>
                                    </div>
                                    <div className={'text-white font-bold text-3xl'}>
                                        <p>Facebook</p>
                                    </div>
                                </div>
                            </div>
                            <div className={'mt-[80px] flex justify-end mr-[100px] relative'}>
                                <div className={'flex flex-1 justify-center bg-main pr-[100px] pl-[30px] py-[15px] rounded-tl-2xl rounded-bl-2xl'}>
                                    <div className={'text-white font-bold text-3xl'}>
                                        <p>Zalo Chat</p>
                                    </div>
                                    <div>
                                        <img src={'/public/zalo.png'} alt={'facebook icon'}
                                             className={'w-[150px] h-auto object-cover absolute top-[-15px] right-[-40px]'}/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className={'flex-1 bg-main p-[40px]'}>
                            <div>
                                <div
                                    className={'uppercase text-2xl flex justify-center items-center text-white font-bold'}>
                                    <p>
                                        Thông tin liên lạc
                                    </p>
                                </div>
                                <div className={'flex flex-col gap-4 mt-[20px]'}>
                                    <div className={'flex gap-2 justify-start items-center'}>
                                        <div className={'text-xl'}><IoMailSharp/></div>
                                        <p className={'text-white'}>dpquochoai@gmail.com</p>
                                    </div>
                                    <div className={'flex justify-start items-center gap-2'}>
                                        <div className={'text-xl'}>
                                            <FaPhoneAlt/>
                                        </div>
                                        <div className={'text-white'}>
                                            <p>0779127667</p>
                                        </div>
                                    </div>
                                    <div className={'flex justify-start items-center gap-2'}>
                                        <div className={'text-xl'}>
                                            <IoLocationSharp/>
                                        </div>
                                        <div className={'text-white'}>
                                            <p>0779127667</p>
                                        </div>
                                    </div>
                                </div>
                                <form className={'flex flex-col gap-6 mt-[40px]'}>
                                    <input className={'px-4 py-3 outline-0 border-none'} placeholder={"Họ và Tên"}/>
                                    <input className={'px-4 py-3 outline-0 border-none'} placeholder={"Số điện thoại"}/>
                                    <textarea className={'h-[150px] px-4 py-3 outline-0 border-none'} placeholder={'Nội dung chi tiết...'}/>
                                </form>
                                <div className={'mt-[30px]'}>
                                    <button className={'bg-black text-white px-4 py-2 hover:text-main transition-all duration-300'}>Gửi ngay</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Contact;