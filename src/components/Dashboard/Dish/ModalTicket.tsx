import {Modal} from "antd";
import React from "react";
import {createTicket, expireToken, getToken, updateTicket} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

interface Ticket {
    id : string,
    active : string,
    conditionUse : string,
    name : string,
    price : number,
    typeTicket : string,
    slot : number
}

const initTicket = {
    id : '',
    active : 'ACTIVE',
    conditionUse : '',
    name : '',
    price : 1000,
    typeTicket : 'ĐƠN',
    slot : 1
}
interface Props {
    loading : boolean,
    setLoading : (arg :boolean) => void,
    isOpen : boolean,
    setIsOpen : (arg : boolean) => void,
    data : Ticket,
    setData : (arg : Ticket) => void,
    active : string,
    setDataAll : (arg:Ticket[]) => void,
    dataAll : Ticket[]
}
const ModalTicket : React.FC<Props> = (props) => {

    const navigate = useNavigate();
    const {loading, setLoading, isOpen, setIsOpen, data, setData, active,dataAll,setDataAll} = props;

    const handleCloseModal = () => {
        setIsOpen(false);
    }
    const handleUpdateTicket = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        console.log(data)
        const response = await updateTicket(data.id,data.active,data.conditionUse,data.name,data.price.toString(),data.typeTicket,data.slot.toString(),token);
        console.log(response)
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể cập nhật dữ liệu</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Cập nhật dữ liệu thành công</p>)
        setData(initTicket);
        setIsOpen(false);
        setDataAll([
            ...dataAll.map(item => {
                if( item.id == data.id){
                    return data;
                }
                return item;
            })
        ]);
    }
    const handleCreateTicket = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        console.log(data)
        const response = await createTicket(data.id,data.active,data.conditionUse,data.name,data.price.toString(),data.typeTicket,data.slot.toString(),token);
        console.log(response)
        setLoading(false);
        if( response.status != 201 ){
            toast.warning(<p className={'w-full'}>Không thể tạo dữ liệu</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Tạo dữ liệu thành công</p>)
        setData(initTicket);
        setIsOpen(false);
        setDataAll([
            ...dataAll,
            response.data.data
        ]);
    }
    return (
        <>
            <Modal
                title={<p className={'text-xl text-main uppercase font-bold'}>Vé xem phim</p>}
                open={isOpen}
                onCancel={()=>handleCloseModal()}
                loading={loading}
                footer={
                    active == 'CREATE' ?
                        <button
                            onClick={()=>handleCreateTicket()}
                            className={'bg-main px-4 py-2 text-white'}>Tạo</button>
                        :
                        <div>
                            <button
                                onClick={()=>handleUpdateTicket()}
                                className={'bg-main px-4 py-2 text-white'}>Cập nhật</button>
                        </div>
                }
            >
                <div className={'flex flex-col gap-2'}>
                    {
                        active != 'CREATE' &&
                        <div className={'flex flex-col gap-2'}>
                            <label>Id<span className={'text-red-700'}>*</span></label>
                            <input value={data.id}
                                   onChange={(e) => setData({...data, id: e.target.value})}
                                   disabled={true}
                                   placeholder={"Nhập mã id"}
                                   className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                                   required={true}/>
                        </div>
                    }
                    <div className={'flex flex-col gap-2'}>
                        <label>Tên vé<span className={'text-red-700'}>*</span></label>
                        <input value={data.name}
                               placeholder={"Nhập tên vé"}
                               onChange={(e) => setData({...data, name: e.target.value})}
                               className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                               required={true}/>
                    </div>
                    <div className={'flex flex-col gap-2'}>
                        <label>Điều kiện áp dụng<span className={'text-red-700'}>*</span></label>
                        <input value={data.conditionUse}
                               placeholder={"Nhập điều kiện sử dụng"}
                               onChange={(e) => setData({...data, conditionUse: e.target.value})}
                               className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                               required={true}/>
                    </div>
                    <div className={'flex flex-col gap-2'}>
                        <label>Giá<span className={'text-red-700'}>*</span></label>
                        <input value={data.price}
                               placeholder={"Nhập giá"}
                               onChange={(e) => setData({...data, price: e.target.value < 0 ? 0 : e.target.value})}
                               type={"number"}
                               className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                               required={true}/>
                    </div>
                    <div className={'flex justify-between'}>
                        <div className={'flex flex-col gap-2'}>
                            <label>Loại vé<span className={'text-red-700'}>*</span></label>
                            <select value={data.typeTicket}
                                    className={'outline-0'}
                                    onChange={(e) => setData({...data, typeTicket: e.target.value})}>
                                <option value={'ĐƠN'}>Đơn</option>
                                <option value={'ĐÔI'}>Đôi</option>
                            </select>
                        </div>
                        <div className={'flex flex-col gap-2'}>
                            <label>Số lượng áp dụng<span className={'text-red-700'}>*</span></label>
                            <select value={data.slot}
                                    className={'outline-0'}
                                    onChange={(e) => setData({...data, slot : e.target.value})}>
                                <option value={'1'}>1 người</option>
                                <option value={'2'}>2 Người</option>
                            </select>
                        </div>
                        <div className={'flex flex-col gap-2'}>
                            <label>Trạng thái <span className={'text-red-700'}>*</span></label>
                            <select value={data.active}
                                    className={'outline-0'}
                                    onChange={(e) => setData({...data, active: e.target.value})}>
                                <option value={'ACTIVE'}>Còn kinh doanh</option>
                                <option value={'DELETE'}>Ngừng kinh doanh</option>
                            </select>
                        </div>
                    </div>
                </div>
            </Modal>
        </>
    )
}
export default ModalTicket;