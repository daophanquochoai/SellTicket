import "./style.css"
import React, {useState} from "react";
import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import {Input, Spin} from 'antd';
import {useNavigate} from "react-router-dom";
import {handleSignUpByAccount} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";

interface User {
    name : string,
    phoneNumber : string,
    email : string,
    username : string,
    password : string,
    prepassword : string,
    roleId : number,
    status : string
}

const initUser : User = {
    name : "",
    phoneNumber : "",
    email : "",
    username : "",
    password : "",
    prepassword : "",
    roleId : 1,
    status : ""
}

const SignUp : React.FC = () => {

    const navigate = useNavigate();
    const [info, setInfo] = useState<User>(initUser);
    const [processSingUp, setProcessSignUp] = useState<boolean>(false);

    const handleLogin = () => {
        navigate("/login")
    }
    const handleSignUp = async (e : React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if( info.password !== info.prepassword){
            toast.warning(<p className={'w-full'}>Password not equals password again.</p>)
            return;
        }
        setProcessSignUp(true);
        const response = await handleSignUpByAccount(info.name, info.phoneNumber, info.email, info.username, info.password);
        setProcessSignUp(false);

        if( response.status !== 201 ){
            toast.error(<p className={'w-full'}>{response.response.data.message}</p>)
            return;
        }

        navigate('/login')
        toast.success("Create account successfully.")
    }
    return(
        <>
            <Spin tip={<span className={"text-xl"}>Sign Up...</span>} spinning={processSingUp} size={"large"} className={'text-main'}>
                <div className={"boxImageSignUp w-full h-dvh flex"}>
                <div className={'flex-1 flex items-center justify-center'}>
                    <form onSubmit={(e) => handleSignUp(e)}>
                        <div className={'flex flex-col gap-y-4'}>
                            <div className={"flex flex-col"}>
                                <label htmlFor={"name"} className={"text-xl"}>Full name :</label>
                                <input name={"name"} className={"w-[350px] px-4 py-2 outline-0"}
                                       placeholder={"Enter Full Name"}
                                       value={info.name}
                                       onChange={(e) => setInfo({...info, name : e.target.value})}
                                       required
                                       pattern={'{a-zA-Z}'}
                                />
                            </div>
                            <div className={"flex flex-col"}>
                                <label htmlFor={"phoneNumber"} className={"text-xl"}>Phone number :</label>
                                <input name={"phoneNumber"} className={"px-4 py-2 outline-0"}
                                       placeholder={"Enter Phone Number"}
                                       type={"tel"}
                                       required
                                       pattern={"[0-9]{10,11}"}
                                       maxLength={11}
                                       minLength={10}
                                       value={info.phoneNumber}
                                       onChange={(e) => setInfo({...info, phoneNumber : e.target.value})}
                                />
                            </div>
                            <div className={"flex flex-col"}>
                                <label htmlFor={"email"} className={"text-xl"}>Email :</label>
                                <input name={"email"} className={"w-350px px-4 py-2 outline-0"}
                                       placeholder={"Enter Email"}
                                       required
                                       type={"email"}
                                       value={info.email}
                                       onChange={(e) => setInfo({...info, email : e.target.value})}
                                />
                            </div>
                            <div className={"flex flex-col"}>
                                <label htmlFor={"username"} className={"text-xl"}>Username :</label>
                                <input name={"username"} placeholder={"Enter Username"}
                                       className={"px-4 py-2 outline-0"}
                                       required
                                       minLength={6}
                                       value={info.username}
                                       onChange={(e) => setInfo({...info, username : e.target.value})}
                                />
                            </div>
                            <div>
                                <label form={"password"} className={"text-xl"}>Password :</label>
                                <Input.Password
                                    className={"px-4 py-2 rounded-none border-none outline-0"}
                                    placeholder="Enter Password"
                                    iconRender={(visible) => (visible ? <EyeTwoTone/> : <EyeInvisibleOutlined/>)}
                                    value={info.password}
                                    minLength={6}
                                    required
                                    onChange={(e) => setInfo({...info, password : e.target.value})}
                                />
                            </div>
                            <div>
                                <label htmlFor={"repassword"} className={'text-xl'}>Password again:</label>
                                <Input.Password
                                    className={"px-4 py-2 rounded-none border-none"}
                                    placeholder="Enter Password Again"
                                    required
                                    iconRender={(visible) => (visible ? <EyeTwoTone/> : <EyeInvisibleOutlined/>)}
                                    value={info.prepassword}
                                    onChange={(e) => setInfo({...info, prepassword : e.target.value})}
                                />
                            </div>
                        </div>
                        <p className={'underline my-2 cursor-pointer'} onClick={()=> handleLogin()}>You already have an account ?</p>
                        <button
                            className="min-w-[350px] bg-black text-main px-4 py-3 transition-all duration-300">Sign
                            Up
                        </button>
                    </form>
                </div>
                <div className={'flex-1'}></div>
            </div>
            </Spin>
        </>
    );
}
export default SignUp;