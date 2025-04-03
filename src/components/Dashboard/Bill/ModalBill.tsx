import React from "react";
import {Modal} from "antd";


interface Bill {
    id : string,
    totalPrice : number,
    transactionCode : string,
    paymentMethodId : string,
    paymentMethod : string,
    active : string,
    chairs : Chair[],
    dishes: Dish[],
    timestamp : string,
    status : string,
    filmShowTimeId : number,
    timeEnd : string,
    timeStart : string,
    timeStampSee : string,
    roomId : string,
    nameRoom : string,
    nameBranch : string,
    address : string,
    filmId : string,
    nameFilm : string,
    userName : string,
    email : string,
    numberPhone : string,
    qrCode : string
}
interface Chair {
    id: string,
    chairCode: string,
    price : number,
    ticket : Ticket,
    active : string
}
interface Ticket {
    id : string,
    active : string,
    conditionUse : string,
    name : string,
    price : 1000
    typeTicket : string,
    slot : 1
}
interface Dish {
    id : string,
    active : string,
    price : number,
    amount : number,
    dishDto : DishDetail
}
interface DishDetail {
    id : string,
    price : number,
    active : string,
    name : string,
    image : string,
}

interface Props {
    isOpen : boolean,
    setIsOpen : (arg:boolean) => void,
    data : Bill,
}
const ModalBill : React.FC<Props> = ( props ) => {

    const {isOpen, setIsOpen, data} = props;

    const handleCancel = () => {
        setIsOpen(false);
    }

    return (
        <>
            <Modal
                open={isOpen}
                title={<p className={'text-main text-xl uppercase'}>Hóa ĐƠn</p>}
                footer={[]}
                onCancel={()=>handleCancel()}
                width={1000}
            >
                <div className={'flex justify-between gap-4'}>
                    <div className={'flex-1 flex flex-col gap-2'}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Mã hóa đơn <span className={'text-red-500'}>*</span></label>
                            <p>{data.id}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Mã giao dịch <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.transactionCode}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Thời gian giao dịch <span className={'text-red-500'}>*</span></label>
                            <p>{data.timestamp}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Trạng thái hoóa đơn <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.active}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Thời gian xem <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.timeStampSee}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Tên phòng <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.nameRoom}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Tên phim <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.nameFilm}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Tên người dùng <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.userName}</p>
                        </div>
                    </div>
                    <div className={'flex-1 '}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Tổng tiền <span className={'text-red-500'}>*</span></label>
                            <p>{data.totalPrice.toLocaleString()}Đ</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Phương thức thanh toán <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.paymentMethod}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Thời gian bắt đầu <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.timeStart}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Thời gian kết thúc <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.timeEnd}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Tên chi nhánh <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.nameBranch}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Địa chỉ <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.address}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Số điện thoại <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.numberPhone}</p>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Địa chỉ email <span
                                className={'text-red-500'}>*</span></label>
                            <p>{data.email}</p>
                        </div>
                        <div>
                            <label className={'text-main'}>Qrcode <span
                                className={'text-red-500'}>*</span></label>
                            {
                                !data.qrCode ?
                                    <div className={"w-[100px] h-[100px] bg-border"}></div>
                                    :
                                    <img alt={'qrcode'} src={data.qrCode} className={'w-[100px] h-[100px]'}/>
                            }
                        </div>
                    </div>
                </div>
                <div className={'w-full border-2 border-textAdmin border-dashed my-[20px]'}></div>
                <div>
                    {
                        data.chairs.length > 0 &&
                        <div className={'mt-[10px]'}>
                            <p className={'text-border font-bold uppercase mb-[10px]'}>Danh sách ghế</p>
                            <table className="w-full border-collapse border border-gray-300 text-left">
                                <thead>
                                <tr className="bg-gray-100">
                                    <th className="border border-gray-300 px-4 py-2">ID</th>
                                    <th className="border border-gray-300 px-4 py-2">Mã ghế</th>
                                    <th className="border border-gray-300 px-4 py-2">Giá</th>
                                    <th className="border border-gray-300 px-4 py-2">Tên vé</th>
                                </tr>
                                </thead>
                                <tbody>
                                {data.chairs.map((item, index) => (
                                    <tr key={index} className="hover:bg-gray-50 even:bg-gray-50">
                                        <td className="border border-gray-300 px-4 py-2">{item.id}</td>
                                        <td className="border border-gray-300 px-4 py-2">
                                            {String.fromCharCode(parseInt(item.chairCode[1]) + 65)}
                                            {item.chairCode[3]}
                                        </td>
                                        <td className="border border-gray-300 px-4 py-2">{item.price}</td>
                                        <td className="border border-gray-300 px-4 py-2">{item.ticket.name}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>

                        </div>
                    }
                </div>
                <div className={'w-full border-2 border-textAdmin border-dashed my-[20px]'}></div>
                <div>
                    {
                        data.dishes.length > 0 &&
                        <div>
                            <p className="text-gray-700 font-bold uppercase mb-2">Danh sách đồ ăn</p>
                            <table className="w-full border-collapse border border-gray-300 text-left">
                                <thead>
                                <tr className="bg-gray-100">
                                    <th className="border border-gray-300 px-4 py-2">ID</th>
                                    <th className="border border-gray-300 px-4 py-2">Tên</th>
                                    <th className="border border-gray-300 px-4 py-2">Hình ảnh</th>
                                    <th className="border border-gray-300 px-4 py-2">Số lượng</th>
                                    <th className="border border-gray-300 px-4 py-2">Tổng cộng</th>
                                </tr>
                                </thead>
                                <tbody>
                                {data.dishes.map((item, index) => (
                                    <tr key={index} className="hover:bg-gray-50 even:bg-gray-50">
                                        <td className="border border-gray-300 px-4 py-2">{item.id}</td>
                                        <td className="border border-gray-300 px-4 py-2">{item.dishDto.name}</td>
                                        <td className="border border-gray-300 px-4 py-2">
                                            <img src={item.dishDto.image} alt={item.dishDto.name}
                                                 className="w-[50px] h-[80px] object-cover rounded-md"/>
                                        </td>
                                        <td className="border border-gray-300 px-4 py-2">{item.amount}</td>
                                        <td className="border border-gray-300 px-4 py-2 font-semibold text-red-600">{item.price}đ</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>

                    }
                </div>
            </Modal>
        </>
    )
}
export default ModalBill;