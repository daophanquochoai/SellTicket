import {useCommonContext} from "../../context/CommonContext.tsx";
import NavPopUp from "./NavPopUp.tsx";
import { useState} from "react";
import {useNavigate} from "react-router-dom";

const Header : React.FC = () => {

    const {isLogin, info} = useCommonContext();
    const [propUp, setPropUp] = useState<boolean>(false);
    const navigate = useNavigate();

    const handleSignIn = () => {
        navigate('/login')
        scroll(0,0);
    }
    const handleSignUp = () => {
        navigate('/signup');
        scroll(0,0);
    }
    const handleToHome = () => {
        navigate('/');
        scroll(0,0);
    }
    const handleToTheaterMovie = () => {
        navigate('/theater')
        scroll(0,0);
    }
    const handleToIntro = () => {
        navigate('/intro')
        scroll(0,0);
    }

    return(
        <>
            <div className={"bg-foreground w-full flex justify-center h-[80px] fixed z-[100]"}>
                <div className={"container flex justify-between items-center py-[10px]"}>
                    <div onClick={() => handleToHome()} className={'cursor-pointer'}>
                        <p className={"text-main text-[35px] font-bold"}>Movie<span
                            className={'text-[30px] text-white'}>Ticket</span></p>
                    </div>
                    <nav>
                        <ul className={'flex gap-9'}>
                            <li className={'group cursor-pointer'}>
                                <div>
                                    <p className={'tet-[32px] text-white group-hover:text-main transition-all duration-300 font-bold'}
                                       onClick={() => handleToHome()}>Trang Chủ</p>
                                    <div
                                        className={'h-[1px] w-0 group-hover:w-full transition-all duration-200 bg-main'}></div>
                                </div>
                            </li>
                            <li className={'group cursor-pointer'} onClick={() => handleToTheaterMovie()}>
                                <p className={'tet-[32px] text-white group-hover:text-main transition-all duration-300 font-bold'}>Lịch
                                    Chiếu</p>
                                <div
                                    className={'h-[1px] w-0 group-hover:w-full transition-all duration-200 bg-main'}></div>
                            </li>
                            <li className={'group cursor-pointer'} onClick={() => handleToIntro()}>
                                <p className={'tet-[32px] text-white group-hover:text-main transition-all duration-300 font-bold'}>Giới thiệu</p>
                                <div
                                    className={'h-[1px] w-0 group-hover:w-full transition-all duration-200 bg-main'}></div>
                            </li>
                        </ul>
                    </nav>
                    <div className="py-2">
                        {
                            isLogin ? (
                                <>
                                    <div className="flex items-center justify-center gap-2"
                                         onClick={() => setPropUp(true)}>
                                        <img
                                            src="/loading.png"
                                            className="w-[40px] bg-red-500 p-2 rounded-full cursor-pointer"
                                        />
                                        <p className="text-[14px] hover:text-main cursor-pointer text-white">
                                            {info?.name}
                                        </p>
                                    </div>

                                    <NavPopUp propUp={propUp} setPropUp={setPropUp}/>
                                </>
                            ) : (
                                <>
                                    <div className={'flex gap-4 items-center'}>
                                        <div className="relative flex justify-center overflow-hidden group w-[100px] rounded-xl border-2 border-main text-main cursor-pointer" onClick={()=> handleSignIn()}>
                                            <span className="absolute inset-0 w-full h-full bg-main -left-full transition-all duration-500 ease-in-out group-hover:left-0"></span>
                                            <p className="relative px-4 py-2 z-10 font-medium group-hover:text-white transition-all duration-500 ease-in-out ">Sign In</p>
                                        </div>
                                        <div className="relative flex justify-center overflow-hidden bg-main group w-[100px] rounded-xl border-2 border-main text-main cursor-pointer" onClick={()=> handleSignUp()}>
                                            <span className="absolute inset-0 w-full h-full bg-foreground -left-full transition-all duration-500 ease-in-out group-hover:left-0 "></span>
                                            <p className="relative px-4 py-2 z-10 font-medium text-white group-hover:text-main transition-all duration-500 ease-in-out ">Sign Up</p>
                                        </div>
                                    </div>
                                </>
                            )
                        }
                    </div>

                </div>
            </div>
            <div className={'w-full h-[80px]'}></div>
        </>
    )
}
export default Header;