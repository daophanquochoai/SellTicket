import {useCommonContext} from "../../context/CommonContext.tsx";
import React, {useEffect, useState} from "react";
import {TbArrowBackUp} from "react-icons/tb";
import {LuPencilLine} from "react-icons/lu";
import {IoIosSave} from "react-icons/io";
import {toast} from "react-toastify";
import {Spin} from "antd";
import {getToken, updateAccountCustomer, updatePassword} from "../../Helper/Helper.ts";
import {useNavigate} from "react-router-dom";
import {MdAccountBox} from "react-icons/md";
import {FaCheck} from "react-icons/fa";

interface Props {
    setOpen : (arg : boolean) => void
}
interface User {
    email : string,
    id : string,
    name : string,
    roles : []
    sub : string,
    phone : string
}
interface Password{
    passNew : string,
    rePass : string,
}
const initPassword : Password = {
    passNew : "",
    rePass : ""
}

const initUser : User = {
    email : "",
    id : "",
    name : "",
    roles : [],
    sub : "",
    phone : ""
}
const Info : React.FC<Props> = ( props ) => {

    const {info,setInfo} = useCommonContext();
    const [edit, setEdit] = useState<boolean>(false);
    const [infoTemp, setInfoTemp] = useState<User>(initUser);
    const [loading, setLoading] = useState<boolean>(false);
    const navigation = useNavigate();
    const [password, setPassword] = useState<boolean>(false);
    const [infoPass, setInfoPass] = useState<Password>(initPassword);

    useEffect(() => {
        if( info != undefined ){
            setInfoTemp(info);
        }else{
            toast.warning(<p>Thông tin người dùng chưa thể tải</p>)
        }
    }, [info]);

    const handleChangeAccount = async () => {
        const token : string = getToken();
        if( token == null ){
            toast.warning(<p className={'w-full'}>Tài khoản đăng nhập đã hết hạn</p>)
            navigation("/login");
            return;
        }
        console.log(infoTemp.name + '  ' + infoTemp.email + '  ' + infoTemp.phone,info?.id + '  ' + token)
        setLoading(true);
        const response = await updateAccountCustomer(infoTemp.name, infoTemp.email, infoTemp.phone,info?.id, token);
        setLoading(false);
        setEdit(false);
        if( response.status != 200){
            toast.warning(<p className={'w-full'}>Tài khoản không thể thay đổi được</p>)
            return;
        }else{
            const data =  await response.data.data;
            setInfo({
                ...info,
                email : data.email,
                id : data.id,
                name : data.name,
                phone : data.phoneNumber,
            })
            console.log({
                ...info,
                email : data.email,
                id : data.id,
                name : data.name,
                phone : data.phoneNumber,
            })
            toast.success(<p className={'w-full'}>Cập nhật thành công</p>)
        }

    }
    const handleChangePassword = async (e) => {
        e.preventDefault();
        if( infoPass.passNew == "" || infoPass.rePass == ""){
            toast.warning(<p className={'w-full'}>Vui lòng điền đầy đủ các giá trị</p>)
            return;
        }
        if( infoPass.passNew != infoPass.rePass ){
            toast.warning(<p className={'w-full'}>Xác nhận mật khẩu chưa chính xác</p>)
            return;
        }
        const token : string = getToken();
        if( token == null){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            return;
        }
        setLoading(true);
        const response = await updatePassword( infoPass.passNew, info?.id, token);
        console.log(response)
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Thay đổi mật khẩu không thành công</p>)
            return;
        }else{
            toast.success(<p className={'w-full'}>Thay đổi mật khẩu thành công</p>)
            setPassword(false);
            setInfoPass(initPassword);
            return;
        }

    }

    return (
        <>
            <Spin tip={"Đang tải..."} spinning={loading}>
                <div className={'w-full h-[100%] px-[10px]'}>
                    <div className={'flex flex-col'}>
                        <label className={'text-medium text-border'}>Họ và tên <span
                            className={'text-red-700'}>*</span></label>
                        <input
                            onChange={(e) => setInfoTemp({...infoTemp, name: e.target.value})}
                            disabled={!edit} value={infoTemp.name} className={'px-2 py-1 text-textCol bg-border'}/>
                    </div>
                    <div className={'flex flex-col'}>
                        <label className={'text-medium text-border'}>Email <span
                            className={'text-red-700'}>*</span></label>
                        <input
                            onChange={(e) => setInfoTemp({...infoTemp, email: e.target.value})}
                            disabled={!edit} value={infoTemp.email} className={'px-2 py-1 text-textCol bg-border'}/>
                    </div>
                    <div className={'flex flex-col'}>
                        <label className={'text-medium text-border'}>Số điện thoại <span
                            className={'text-red-700'}>*</span></label>
                        <input
                            onChange={(e) => setInfoTemp({...infoTemp, phone: e.target.value})}
                            disabled={!edit} value={infoTemp.phone} className={'px-2 py-1 text-textCol bg-border'}/>
                    </div>
                    <div className={'flex justify-between items-center mt-[10px]'}>
                        <button
                            onClick={() => props.setOpen(true)}
                            className={'hover:text-main text-textCol bg-border px-4 py-2'}><TbArrowBackUp/></button>
                        <button
                            onClick={() => setPassword(!password)}
                            className={`hover:text-main ${!password ? 'bg-border text-textCol' : 'bg-textCol text-main'} border-2 border-border px-4 py-2`}><MdAccountBox /></button>
                        {
                            edit ?
                                <button
                                    onClick={() => handleChangeAccount()}
                                    className={'hover:text-main text-textCol bg-border px-4 py-2'}><IoIosSave/>
                                </button>
                                :
                                <button
                                    onClick={() => setEdit(true)}
                                    className={'hover:text-main text-textCol bg-border px-4 py-2'}><LuPencilLine/>
                                </button>
                        }
                    </div>
                    {
                        password &&
                        <form onSubmit={(e) => handleChangePassword(e)} className={'mt-[10px]'}>
                            <div className={'flex flex-col'}>
                                <label className={'text-medium text-border'}>Mật khẩu mới <span
                                    className={'text-red-700'}>*</span></label>
                                <input
                                    type={"password"}
                                    minLength={8}
                                    onChange={(e) => setInfoPass({...infoPass, passNew: e.target.value})}
                                    value={infoPass.passNew}
                                    className={'px-2 py-1 text-textCol bg-border'}/>
                            </div>
                            <div className={'flex flex-col'}>
                                <label className={'text-medium text-border'}>Xác nhận lại <span
                                    className={'text-red-700'}>*</span></label>
                                <input
                                    type={"password"}
                                    minLength={8}
                                    onChange={(e) => setInfoPass({...infoPass, rePass: e.target.value})}
                                    value={infoPass.rePass}
                                    className={'px-2 py-1 text-textCol bg-border'}/>
                            </div>
                            <div className={'mt-[10px]'}>
                                <button
                                    className={' w-full flex bg-textCol justify-center px-2 py-2 text-xl text-border hover:text-main'}>
                                    <FaCheck/></button>
                            </div>
                        </form>
                    }
                </div>
            </Spin>
        </>
    )
}
export default Info;