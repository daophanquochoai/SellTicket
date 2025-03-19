import React, {useEffect, useState} from "react";
import {Modal} from "antd";
import {FaStar} from "react-icons/fa";
import {activeRate, deleteRate, expireToken, getToken} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

interface Rate {
    id : string,
    star : number,
    content : string,
    timeStamp : string,
    customer : Customer,
    film : Film,
    active : string
}
interface Customer {
    id : string,
    name : string,
    phoneNumber : string,
    email : string,
}
interface Film{
    id : 1,
    name : string,
    age : number,
    image : string,
    nation : string,
    duration : string,
    sub : Sub[],
    description : string,
    content : string,
    trailer : string,
    typeFilms : TypeFilm[],
    status : string
}
interface Sub {
    id : string,
    name : string
}
interface TypeFilm {
    id : string,
    name : string,
    active : string
}
interface  Props {
    data : Rate,
    isOpen : boolean,
    rates : Rate[],
    setRates : (arg:Rate[])=> void,
    setIsOpen : (arg : boolean) => void
}

const ModalRate : React.FC<Props> = (props) => {

    const {data,isOpen, setIsOpen,rates,setRates} = props;
    const [active, setActive] = useState<string>('ACTIVE');
    const navigate = useNavigate();
    const [loading, settLoading] = useState<boolean>(false);

    useEffect(() => {
        setActive(data.active)
    }, [data]);

    const handleCancel = () => {
        setIsOpen(false);
    }

    const handleUpdateRate = async () => {
        const item : Rate = rates.find(i=>i.id == data.id);
        if( item != undefined){
            if( item.active == active){
                return;
            }
        }
        const token: string = getToken();
        if (expireToken(token)) {
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        settLoading(true);
        let response = null;
        if( active != 'ACTIVE'){
            response = await deleteRate(data.id,token);
        }else{
            response = await activeRate(data.id,token);
        }
        console.log(response)
        settLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setRates(
            rates.map(item => {
                if( item.id == data.id){
                    return {
                        ...item,
                        active : active
                    }
                }
                return item;
            })
        )
        toast.success(<p className={'w-full'}>Cập nhật thành công</p>)
        setIsOpen(false);
    }

    return (
        <>
            <Modal
                loading={loading}
                title={<p className={'text-main text-xl uppercase font-bold'}>Đánh giá</p>}
                open={isOpen}
                onCancel={() => handleCancel()}
                footer={[
                    <button
                        onClick={()=> handleUpdateRate()}
                        key={'update'} className={'px-4 py-2 text-white bg-main'}>Cập nhật</button>
                ]}
            >
                <div className={'mb-[10px]'}>
                    <p className={'text-border font-bold uppercase'}>Thông tin đánh giá</p>
                </div>
                <div className={'flex gap-4'}>
                    <div className={'flex flex-col gap-4 flex-1'}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>ID<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.id}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Đánh giá<span
                                className={'text-red-500'}>*</span></label>
                            <div className={'flex items-center gap-2'}>{data.star}
                                <div className={'text-main'}><FaStar/></div>
                            </div>
                        </div>
                    </div>
                    <div className={'flex flex-col gap-4 flex-1'}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Nội dung đánh giá<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.content}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Thời gian<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.timeStamp}</p>
                        </div>
                    </div>
                </div>
                <div className={'w-full border-2 border-textAdmin border-dashed my-[20px]'}></div>
                <div className={'mb-[10px]'}>
                    <p className={'text-border font-bold uppercase'}>Thông tin người dùng</p>
                </div>
                <div className={'flex gap-4'}>
                    <div className={'flex flex-col gap-4 flex-1'}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>ID<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.customer.id}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Tên người dùng<span
                                className={'text-red-500'}>*</span></label>
                            <p className={'flex items-center gap-2'}>{data.customer.name}
                            </p>
                        </div>
                    </div>
                    <div className={'flex flex-col gap-4 flex-1'}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Địa chỉ email<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.customer.email}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Số điện thoại<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.customer.phoneNumber}</p>
                        </div>
                    </div>
                </div>
                <div className={'w-full border-2 border-textAdmin border-dashed my-[20px]'}></div>
                <div className={'mb-[10px]'}>
                    <p className={'text-border font-bold uppercase'}>Thông tin phim</p>
                </div>
                <div className={'flex gap-4'}>
                    <div className={'flex flex-col gap-4 flex-1'}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>ID<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.film.id}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Ảnh phim<span
                                className={'text-red-500'}>*</span></label>
                            <div className={'flex items-center gap-2'}>
                                <img src={data.film.image} className={'w-[50px] h-[80px]'} alt={'image film'}/>
                            </div>
                        </div>
                    </div>
                    <div className={'flex flex-col gap-4 flex-1'}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Quốc gia<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.film.nation}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Thời lượng<span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.film.duration}</p>
                        </div>
                    </div>
                </div>
                <div className={'w-full border-2 border-textAdmin border-dashed my-[20px]'}></div>
                <div>
                    <p>Trạng thái <span className={'text-red-700'}>*</span></p>
                    <select value={active}
                            className={'outline-0 min-w-[150px]'}
                            onChange={(e) => setActive(e.target.value)}
                    >
                        <option value={'ACTIVE'}>Hiển thị</option>
                        <option value={"DELETE"}>Ẩn đi</option>
                    </select>
                </div>
            </Modal>
        </>
    )
}
export default ModalRate;