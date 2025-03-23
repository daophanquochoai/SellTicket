import React, {useEffect, useRef, useState} from "react";
import './style.css';
import {Input, Spin} from "antd";
import {OTPProps} from "antd/es/input/OTP";
import {toast} from "react-toastify";
import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import {handleAcceptOpt} from "../../Helper/Helper.ts";

interface Visible{
    password : boolean,
    rePass : boolean
}
interface Password {
    pass : string,
    rePass : string
}
const initVisible = {
    password : false,
    rePass : false
}
interface Time {
    minute : string,
    second : string
}
const initTime : Time = {
    minute : "01",
    second : "00"
}
const initPass : Password = {
    pass : '',
    rePass : ''
}

const OPT : React.FC = () => {

    const [opt, setOpt] = useState<string>('');
    const [password, setPassword] = useState<Visible>(initVisible);
    const [time, setTime] = useState<Time>(initTime);
    const optRef = useRef(null);
    const navigate = useNavigate();
    const [loading,setLoading] = useState<boolean>(false);
    const [data, setData] = useState<Password>(initPass);
    const [searchParams] = useSearchParams();

    useEffect(() => {
        optRef.current = setInterval(() => {
            setTime((prev) => {
                const min = parseInt(prev.minute, 10);
                const sec = parseInt(prev.second, 10);
                if(  min == 0 && sec == 0 ){
                    toast.warning(<p>Mã đã hết hạn</p>);
                    clearInterval(optRef.current!);
                    navigate("/forgetpassword")
                    return { minute: '01', second: '00'};
                }
                if (sec === 0) {
                    return { minute: (min - 1).toString().padStart(2, "0"), second: "59" };
                } else {
                    return { minute: prev.minute, second: (sec - 1).toString().padStart(2, "0") };
                }
            });
        }, 1000);
        return () => {
            if( optRef.current ){
                clearInterval(optRef.current);
            }
        }
    }, []);

    const onChange: OTPProps['onChange'] = (text) => {
        setOpt(text.toString());
    };

    const onInput: OTPProps['onInput'] = (value) => {
        setOpt(value.join(''));
    };

    const sharedProps: OTPProps = {
        onChange,
        onInput,
    };

    const handleFetchPasswordNew = async (e) => {
        e.preventDefault();
        if( data.pass != data.rePass || data.pass == ''){
            toast.warning(<p className={'w-full'}>Xác thực chưa khớp</p>)
            return;
        }
        const email : string | null = searchParams.get("email");
        if( email == null ){
            toast.warning(<p className={'w-full'}>Địa chỉ email xảy ra lỗi</p>)
            return;
        }
        setLoading(true);
        console.log(data.pass)
        const response = await handleAcceptOpt(opt,data.pass,email)
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Mã opt không chính xác</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Cập nhật tài khoản thành công</p>)
        navigate('/login');
    }


    return (
        <>
            <div className={'bg_forget w-full h-dvh flex items-center justify-center'} ref={optRef}>
                <Spin tip={"Đang xử lí..."} spinning={loading}>
                    <form className={'p-[30px] shadow flex flex-col gap-4'} onSubmit={(e) => handleFetchPasswordNew(e)}>
                        <div className={'flex flex-col gap-2'}>
                            <p className={'font-bold text-center'}>OPT</p>
                            <Input.OTP formatter={(str) => str.toUpperCase()} {...sharedProps} />
                        </div>
                        <div>
                            <Input.Password
                                placeholder="Nhập mật khẩu"
                                value={data.pass}
                                onChange={e => setData({...data, pass: e.target.value})}
                                visibilityToggle={{
                                    visible: password.password,
                                    onVisibleChange: () => setPassword({...password, password: !password.password})
                                }}
                            />
                        </div>
                        <div>
                            <Input.Password
                                placeholder="Nhập lại mật khẩu"
                                value={data.rePass}
                                onChange={e => setData({...data, rePass: e.target.value})}
                                visibilityToggle={{
                                    visible: password.rePass,
                                    onVisibleChange: () => setPassword({...password, rePass: !password.rePass})
                                }}
                            />
                        </div>
                        <div className={'flex justify-between items-center'}>
                            <div className={'px-2 py-1 border-[1px] text-textAdmin uppercase'}
                                    onClick={() => navigate(-1)}>
                                Quay lại
                            </div>
                            <p className={'text-forget font-bold'}>{time.minute} : {time.second}</p>
                            <button className={'px-2 py-1 uppercase bg-main text-white border-main border-[1px]'}>
                                Xác nhận
                            </button>
                        </div>
                    </form>
                </Spin>
            </div>
        </>
    )
}
export default OPT;