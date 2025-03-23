import React, {useEffect, useState} from "react";
import "./style.css";
import {handleForgetPassword} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate, useSearchParams} from "react-router-dom";
import {Spin} from "antd";

const ForgetPassword : React.FC = () => {

    const [email, setEmail] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();

    const handleFetchEmail = async (e) => {
        e.preventDefault();
        setLoading(true);
        const response = await handleForgetPassword(email);
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Địa chỉ email không tồn tại</p>)
            return;
        }
        navigate(`/opt?email=${email}`);
    }

    const handleBack = () => {
        navigate(-1);
    }

    return(
        <div className={'bg_forget w-full h-dvh flex items-center justify-center'}>
            <Spin tip={'Đang xử lý...'} spinning={loading}>
                <form className={'bg-white shadow p-[30px] flex flex-col gap-[30px]'}
                      onSubmit={(e) => handleFetchEmail(e)}>
                    <div className={'flex flex-col gap-2'}>
                        <p className={'text-center uppercase'}>Địa chỉ email</p>
                        <input value={email} onChange={e => setEmail(e.target.value)}
                               placeholder={"Nhập địa chỉ email..."}
                               className={'border-textAdmin border-[1px] px-4 py-1 min-w-[300px] outline-0 text-textAdmin'}
                               type={"email"}
                        />
                    </div>
                    <div className={'flex justify-between items-center'}>
                        <div className={'px-2 py-1 border-[1px] text-textAdmin uppercase'}
                                onClick={() => handleBack()}
                        >
                            Quay lại
                        </div>
                        <button className={'px-2 py-1 uppercase bg-main text-white border-main border-[1px]'}
                        >
                            Gửi mã
                        </button>
                    </div>
                </form>
            </Spin>
        </div>
    )
}
export default ForgetPassword;