import React, {useEffect, useState} from "react";
import {CloseOutlined} from "@ant-design/icons";
import {expireToken, getToken, parseJwt, removeToken, saveToken, updateEmploye} from "../../Helper/Helper.ts";
import {useNavigate} from "react-router-dom";
import {useCommonContext} from "../../context/CommonContext.tsx";
import {Modal} from "antd";
import {toast} from "react-toastify";

interface NavPropUps {
    propUp : boolean;
    setPropUp : (arg : boolean) => void
}

interface User {
    username : string,
    email : string,
    cccd : string,
    pass : string,
    repass : string
}
const initUser : User = {
    username : '',
    email : '',
    cccd : '',
    pass : '',
    repass : ''
}

const NavAdmin : React.FC<NavPropUps> = ({propUp, setPropUp}) => {

    const navigation = useNavigate();
    const {isLogin,setLogin, info, setInfo} = useCommonContext();
    const [open, setOpen] = useState<boolean>(false);
    const [account, setAccount] = useState<User>(initUser);
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        setAccount({
            ...account,
            username : info?.name,
            email : info?.email,
            cccd : info?.cccd
        })
    }, [info]);

    const handleLogout = () => {
        removeToken();
        if (setLogin) {
            setLogin(false);
        }
        navigation('/dashboard/login');
    }

    const handleUpdateEmployee = async () => {
        if( account.pass !== account.repass ){
            toast.warning(<p>Xác nhận mật khẩu không trùng khớp</p>)
            return;
        }
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await updateEmploye(account.username,account.email,account.pass,account.cccd,info?.id,token);
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể cập nhật dữ liệu</p>)
            return;
        }
        const tokenNew : string = response.data.data;
        saveToken(tokenNew);
        setInfo(parseJwt(tokenNew));
        toast.success(<p className={'w-full'}>Cập nhật tài khoản thành công</p>)
        setOpen(false);
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
                            <div>
                                <ul className={'mt-4'}>
                                    <li
                                        onClick={() => setOpen(true)}
                                        className={'flex text-white justify-center hover:text-black hover:bg-white cursor-pointer duration-150'}>
                                        <p className={'text-[18px]'}>Thông tin tài khoản</p>
                                    </li>
                                    <li className={'flex text-white justify-center hover:text-black hover:bg-white cursor-pointer duration-150'}
                                        onClick={() => handleLogout()}>
                                        <p className={'text-[18px]'}>Đăng xuất</p>
                                    </li>
                                </ul>
                            </div>
                        </>
                    }
                </div>
                <Modal
                    open={open}
                    loading={loading}
                    onCancel={()=>setOpen(false)}
                    title={<p className={'text-main font-bold text-xl'}>Tài khoản</p>}
                    footer={[]}
                >
                    <div className={'flex flex-col gap-4'}>
                        <div className={'flex flex-col'}>
                            <label>Tên tài khoản<span className={'text-red-700'}>*</span></label>
                            <input value={account.username}
                                   onChange={(e)=>setAccount({...account, username : e.target.value})}
                                   className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                                   required={true}/>
                        </div>
                        <div className={'flex flex-col'}>
                            <label>Địa chỉ email<span className={'text-red-700'}>*</span></label>
                            <input defaultValue={account.email}
                                   onChange={(e)=>setAccount({...account,email : e.target.value})}
                                   className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                                   required={true}/>
                        </div>
                        <div className={'flex flex-col'}>
                            <label>CCCD <span className={'text-red-700'}>*</span></label>
                            <input defaultValue={account.cccd}
                                   onChange={(e)=>setAccount({...account,cccd : e.target.value})}
                                   className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                                   required={true}/>
                        </div>
                        <div className={'flex flex-col'}>
                            <label>Mật khẩu mới <span className={'text-red-700'}>*</span></label>
                            <input placeholder={'*******'}
                                   value={account.pass}
                                   onChange={(e)=>setAccount({...account,pass : e.target.value})}
                                   className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                                   required={true}/>
                        </div>
                        <div className={'flex flex-col'}>
                            <label>Xác nhận mật khẩu <span className={'text-red-700'}>*</span></label>
                            <input
                                value={account.repass}
                                onChange={(e)=>setAccount({...account, repass : e.target.value})}
                                    placeholder={"*******"}
                                   className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                                   required={true}/>
                        </div>
                        <div className={'flex justify-between items-center'}>
                            <button onClick={() => handleUpdateEmployee()} className={'px-4 py-2 bg-main text-white min-w-[120px]'}>Cập nhật</button>
                        </div>
                    </div>
                </Modal>
            </div>
        </>
    )
}
export default NavAdmin;