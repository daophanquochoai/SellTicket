import * as React from "react";
import '../../index.css';
import {useCommonContext} from "../../context/CommonContext.tsx";
import './style.css'
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {EyeInvisibleOutlined, EyeTwoTone} from "@ant-design/icons";
import {Input, Spin} from "antd";
import {getSocialLogin, handleLoginByUsernameAndPassword, parseJwt, saveToken} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";

interface Account {
    username : string,
    password : string
}

interface User {
    email : string,
    id : string,
    name : string,
    roles : []
    sub : string,
    phone : string
}

const initAccount = {
    username : "",
    password : ""
}

const LoginPage : React.FC = () => {

    const navigate = useNavigate();

    const {setLogin, setInfo} = useCommonContext();
    const [processLogin, setProcessLogin] = useState<boolean>(false);
    const [account, setAccount] = useState<Account>(initAccount);


    const handleLogin : (e: React.FormEvent) => void = async (e) => {
        e.preventDefault();
        setProcessLogin(true);
        const response = await handleLoginByUsernameAndPassword(account.username, account.password);
        if( response.status != 200){
            toast.warning(<p className={"w-full"}>Tài khoản không tồn tại</p>);
            setProcessLogin(false);
            return;
        }
        setProcessLogin(false);
        const data = await response.data;
        const token = data.accessToken;
        const valueToken : User = parseJwt(token);
        saveToken(token);
        if (setInfo) {
            setInfo(valueToken);
        }
        if (setLogin) {
            setLogin(true);
        }
        navigate("/")
    }

    const handleSignUp = () => {
        navigate("/signup")
    }

    const handleForgetPassword = () => {
        navigate("/forgetpassword")
    }
    return(
        <>
            <Spin tip={<span className={"text-xl"}>Đang đăng nhập...</span>} spinning={processLogin} className={'text-main'}>
                <div
                    className={"boxImage w-full h-dvh bg-cover flex"}
                >
                    <div className="flex-1"></div>
                    <div className="flex flex-1 items-center justify-center flex-col">
                            <form onSubmit={(e) => handleLogin(e)} className="flex flex-col">
                                <div className="flex flex-col mb-5">
                                    <label className="mb-2">Tài khoản :</label>
                                    <Input placeholder="Nhâp tai khoản"
                                           className="px-4 py-3 outline-0 border-b-2 min-w-[350px] rounded-none border-none"
                                           required
                                           value={account.username}
                                           onChange={(e) => setAccount({...account, username: e.target.value})}
                                    />
                                </div>
                                <div className="flex flex-col mb-2">
                                    <label className="mb-2">Mật khẩu :</label>
                                    <Input.Password
                                        className={"px-4 py-3 rounded-none border-none"}
                                        placeholder="Nhập mật khẩu"
                                        iconRender={(visible) => (visible ? <EyeTwoTone/> : <EyeInvisibleOutlined/>)}
                                        required
                                        value={account.password}
                                        onChange={(e) => setAccount({...account, password: e.target.value})}
                                    />
                                </div>
                                <div className={"mb-2 flex justify-between"}>
                                    <p onClick={() => handleSignUp()} className={'underline cursor-pointer'}>Tạo tài khoản ?</p>
                                    <p onClick={() => handleForgetPassword()} className={'underline cursor-pointer'}>Quên mật khẩu?</p>
                                </div>
                                <button
                                    className="min-w-[350px] bg-black text-main px-4 py-3 transition-all duration-300">Đăng nhập
                                </button>
                            </form>
                            <div className={'my-[20px]'}>--hoặc--</div>
                            <div>
                                <a href={getSocialLogin("github")}>
                                    <div className={'cursor-pointer bg-white w-[50px] p-[5px] h-[50px] rounded'}>
                                        <img src={'/github.png'} className={'w-full h-full'}/>
                                    </div>
                                </a>
                            </div>
                    </div>
                </div>
            </Spin>
        </>
    );
}
export default LoginPage;