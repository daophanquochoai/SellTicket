import {Input, Spin} from "antd";
import {EyeInvisibleOutlined, EyeTwoTone} from "@ant-design/icons";
import * as React from "react";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import "./style.css";
import {handleLoginByUsernameAndPassword, parseJwt, saveToken} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useCommonContext} from "../../context/CommonContext.tsx";

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

const LoginAdmin : React.FC = () => {

    const [account, setAccount] = useState<Account>(initAccount);
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();
    const {setInfo, setLogin} = useCommonContext();

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        const response = await handleLoginByUsernameAndPassword(account.username, account.password);
        setLoading(false);
        if( response.status != 200){
            toast.error(<p className={'w-full'}>Đăng nhập thất bại</p>)
            return;
        }
        const data : string = await response.data.accessToken;
        const user : User = parseJwt(data);
        saveToken(data);
        if(user.roles[0] == 'ROLE_ADMIN'){
            if (setInfo) {
                setInfo(user);
            }
            if (setLogin) {
                setLogin(true);
            }
            navigate('/dashboard');
        }else{
            navigate('/');
        }
    }


    return (
        <>
            <div className={'w-full flex items-center justify-center h-dvh bg-red-400 loginContainer'}>
                <div className={'flex-1'}></div>
                <div className={'flex-1 flex justify-center items-center'}>
                    <Spin tip={"Đang đăng nhập..."} spinning={loading}>
                        <form onSubmit={(e) => handleLogin(e)} className="flex flex-col">
                            <div className="flex flex-col mb-5">
                                <label className="text-textAdmin uppercase font-bold mb-2">Email :</label>
                                <Input placeholder="Enter Email"
                                       className="px-4 py-3 outline-0 border-b-2 min-w-[350px] rounded-none"
                                       required
                                       value={account.username}
                                       onChange={(e) => setAccount({...account, username: e.target.value})}
                                />
                            </div>
                            <div className="flex flex-col mb-2">
                                <label className="text-textAdmin uppercase font-bold mb-2">Password :</label>
                                <Input.Password
                                    className={"px-4 py-3 rounded-none"}
                                    placeholder="Enter Password Again"
                                    iconRender={(visible) => (visible ? <EyeTwoTone/> : <EyeInvisibleOutlined/>)}
                                    required
                                    value={account.password}
                                    onChange={(e) => setAccount({...account, password: e.target.value})}
                                />
                            </div>
                            <button
                                className="min-w-[350px] bg-textAdmin text-white px-4 py-3 transition-all duration-300">Login
                            </button>
                        </form>
                    </Spin>
                </div>
            </div>
        </>
    )
}

export default LoginAdmin;