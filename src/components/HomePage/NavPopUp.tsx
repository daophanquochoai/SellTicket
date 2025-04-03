import React, {lazy, useEffect, useState} from "react";
import {CloseOutlined} from "@ant-design/icons";
import {removeToken} from "../../Helper/Helper.ts";
import {useNavigate} from "react-router-dom";
import {useCommonContext} from "../../context/CommonContext.tsx";
import { motion } from "framer-motion";
import Info from "../Info/Info.tsx";



interface NavPropUps {
    propUp : boolean;
    setPropUp : (arg : boolean) => void
}

const NavPopUp : React.FC<NavPropUps> = ({propUp, setPropUp}) => {

    const navigation = useNavigate();
    const {isLogin,setLogin} = useCommonContext();
    const [open, setOpen] = useState<boolean>(true);

    const handleLogout = () => {
        removeToken();
        if (setLogin) {
            setLogin(false);
        }
        navigation('/login');
    }

    return (
        <>
            <div
                className={`${propUp ? 'w-[300px]' : 'w-0'} h-[100vh] bg-main absolute top-0 right-0 z-10 transition-all duration-500`}>
                <div
                    className={`${propUp ? 'block' : 'hidden'} transition-all duration-700`}>
                    <div className={'flex justify-start px-4 py-2'}>
                        <div className={'cursor-pointer text-[22px] text-white hover:text-black'}
                             onClick={() => setPropUp(false)}><CloseOutlined/></div>
                    </div>
                    {
                        isLogin &&
                            <>
                                <motion.div
                                    initial={{height: 120}}
                                    animate={{height: open  ? 170 : 0}}
                                    transition={{duration: 0.2, ease: "easeInOut"}}
                                    className={'overflow-hidden'}
                                >
                                    <ul className={'mt-4'}>
                                        <li
                                            onClick={() => setOpen(false)}
                                            className={'flex text-white px-4 py-2 justify-center hover:text-black hover:bg-white border-2 border-main cursor-pointer duration-150'}>
                                            <p className={'text-[18px]'}>Thông tin người dùng</p>
                                        </li>
                                        <li className={'flex text-white px-4 py-2 justify-center hover:text-black hover:bg-white border-2 border-main cursor-pointer duration-150'}
                                            onClick={() => handleLogout()}>
                                            <p className={'text-[18px]'}>Đăng xuất</p>
                                        </li>
                                    </ul>
                                </motion.div>
                            </>
                    }
                </div>
                {
                    !open &&
                    <Info setOpen={setOpen}/>
                }
            </div>
        </>
    )
}
export default NavPopUp;