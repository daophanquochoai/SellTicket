import React from "react";
import {FaFacebook, FaYoutube} from "react-icons/fa";
import {AiFillTikTok} from "react-icons/ai";
import {useCommonContext} from "../../context/CommonContext.tsx";
import {Link, useNavigate} from "react-router-dom";
import {removeToken} from "../../Helper/Helper.ts";

const Footer : React.FC = () => {

    const {isLogin, setLogin} = useCommonContext();
    const navigation = useNavigate();

    const handleToLogin = () => {
        navigation('/login');
    }
    const handleToSignUp = () => {
        navigation('/signup')
    }

    const handleLogout = () => {
        removeToken();
        if (setLogin) {
            setLogin(false);
        }
        navigation('/login');
    }


    return (
        <>
            <div className={'flex justify-center items-center bg-foreground mt-[100px] py-[50px]'}>
                <div className={'container flex flex-col justify-center'}>
                    <div className={'flex'}>
                        <div className={'flex w-1/3 justify-start flex-col'}>
                            <div className={'flex flex-col gap-2'}>
                                <p className={'text-4xl font-bold text-main'}>Movie<span
                                    className={'text-white'}>Ticket</span></p>
                                <p className={'text-white uppercase'}>BE HAPPY, BE A STAR</p>
                            </div>
                            <div className={'flex gap-2 items-center mt-[20px]'}>
                                <div className={'text-[20px] text-white'}>
                                    <FaFacebook/>
                                </div>
                                <div className={'text-[24px] text-white'}>
                                    <AiFillTikTok/>
                                </div>
                                <div className={'text-[27px] text-white'}>
                                    <FaYoutube/>
                                </div>
                            </div>
                            <div className={'mt-[20px]'}>
                                <Link
                                    to={"/theater"}
                                    className={'bg-main text-white px-5 py-2 rounded-[10px] hover:bg-white hover:text-main border-2 border-main transition-all duration-300'}>Đặt
                                    vé ngay
                                </Link>
                            </div>
                        </div>
                        <div className={'flex-3 h-[40px] w-2/3'}>
                            <div className={'grid grid-cols-4 gap-y-10 gap-x-5'}>
                                {
                                    isLogin ?
                                        <>
                                            <div className={'col-span-1'}>
                                                <p className={'text-xl font-bold text-main mb-4'}>Tài khoản</p>
                                                <ul className={'flex flex-col gap-1'}>
                                                    <li className={'group cursor-pointer'}>
                                                        <span className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Thông tin tài khoản</span>
                                                    </li>
                                                    <li className={'group cursor-pointer inline'} onClick={()=>handleLogout()}>
                                                        <span className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Đăng xuất</span>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div className={'col-span-1'}>
                                                <p className={'text-xl font-bold text-main mb-4'}>Tài khoản thanh
                                                    toán</p>
                                                <ul className={'flex flex-col gap-1'}>
                                                    <li className={'group cursor-pointer'}>
                                                    <span
                                                        className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Ngân hàng</span>
                                                    </li>
                                                    <li className={'group cursor-pointer inline'}>
                                                    <span
                                                        className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Quản lí tài khoản</span>
                                                    </li>
                                                    <li className={'group cursor-pointer inline'}>
                                                    <span
                                                        className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Khác</span>
                                                    </li>
                                                </ul>
                                            </div>
                                        </>
                                        :
                                        <>
                                            <div className={'col-span-1'}>
                                                <p className={'text-xl font-bold text-main mb-4'}>Tài khoản</p>
                                                <ul className={'flex flex-col gap-1'}>
                                                    <li className={'group cursor-pointer'} onClick={()=>handleToLogin()}>
                                                        <p className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Đăng nhập</p>
                                                    </li>
                                                    <li className={'group cursor-pointer inline'} onClick={()=>handleToSignUp()}>
                                                        <span className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Đăng ký</span>
                                                    </li>
                                                </ul>
                                            </div>
                                        </>
                                }
                                <div className={'col-span-1'}>
                                    <p className={'text-xl font-bold text-main mb-4'}>Xem phim</p>
                                    <ul className={'flex flex-col gap-1'}>
                                        <li className={'group cursor-pointer'}>
                                            <p className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Đang
                                                chiếu</p>
                                        </li>
                                        <li className={'group cursor-pointer inline'}>
                                        <span
                                            className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Sắp ra mặt</span>
                                        </li>
                                    </ul>
                                </div>
                                <div className={'col-span-1'}>
                                    <p className={'text-xl font-bold text-main mb-4'}>Về chúng tôi</p>
                                    <ul className={'flex flex-col gap-1'}>
                                        <li className={'group cursor-pointer'}>
                                            <p className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Giới
                                                thiệu</p>
                                        </li>
                                        <li className={'group cursor-pointer inline'}>
                                        <span
                                            className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Liên hệ</span>
                                        </li>
                                        <li className={'group cursor-pointer inline'}>
                                        <span
                                            className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Thuê rạp chiếu</span>
                                        </li>
                                        <li className={'group cursor-pointer inline'}>
                                        <span
                                            className={'text-[16px] font-light text-white group-hover:text-main transition-all duration-300'}>Các dịch vụ khác</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={'mt-[30px]'}>
                        <p className={'text-white font-thin text-xs text-left'}>© 2025 DoctorHoai. Mọi quyền được bảo lưu. Nghiêm cấm sao chép, phân phối hoặc sử dụng lại nội dung này dưới bất kỳ hình thức nào mà không có sự cho phép bằng văn bản. </p>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Footer;