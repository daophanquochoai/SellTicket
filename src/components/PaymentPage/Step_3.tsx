import {useCommonContext} from "../../context/CommonContext.tsx";
import React from "react";
import {Result} from "antd";
import {useNavigate} from "react-router-dom";

const Step_3 : React.FC = () => {

    const {bill} = useCommonContext();
    const navigation = useNavigate();

    const handleBack = async () => {
        navigation("/");
        scroll(0,0);
    }
    return (
        <div className={'flex flex-col gap-4 w-full'}>
            <Result
                className={'bg-white'}
                status="success"
                title="Đặt vé thành công"
                subTitle="Chúc mừng bạn đã đặt vé thành công!"
                extra={[
                    <button onClick={() => handleBack()} className={'bg-main px-4 py-2'} key="console">
                        Trở về
                    </button>
                ]}
            />
            <div className={'w-full'}>
                <div className={'flex items-start justify-between bg-main'}>
                    <div className={'w-1/2 flex flex-col gap-4 border-r-2 border-dashed  px-8 py-4'}>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium font-bold text-border uppercase'}>Tên phim : </h4>
                            <p className={'text-white uppercase'}>{bill?.nameFilm}</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium font-bold text-border uppercase'}>Phòng chiếu : </h4>
                            <p className={'text-white uppercase'}>{bill?.nameRoom}</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium font-bold text-border uppercase'}>Thời gian : </h4>
                            <p className={'text-white uppercase'}>{bill?.timeStart} - {bill?.timeEnd} <span className={'text-foreground'}>(Ngày:{bill?.timeStampSee})</span></p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium text-border font-bold uppercase'}>Mã giao dịch : </h4>
                            <p className={'text-white uppercase'}>{bill?.transactionCode}</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium text-border font-bold uppercase'}>Tổng tiền : </h4>
                            <p className={'text-white'}>{bill?.totalPrice.toLocaleString()} VND</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium text-border font-bold uppercase'}>Khách hàng : </h4>
                            <p className={'text-white'}>{bill?.userName}</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium font-bold text-border'}>Email : </h4>
                            <p className={'text-white'}>{bill?.email}</p>
                        </div>
                        <div className={'flex items-center gap-4'}>
                            <h4 className={'text-medium text-border font-bold'}>Số điện thoại : </h4>
                            <p className={'text-white'}>{bill?.numberPhone}</p>
                        </div>
                    </div>
                    <div className={'flex-1 flex flex-col bg-main gap-4  px-8 py-4'}>
                        <div className={"flex gap-4"}>
                            <p className={'text-medium text-border font-bold'}>QRcode :</p>
                            <img
                                src={bill?.qrCode}
                                className={'w-[100px] h-[100px]'}
                            />
                        </div>
                        <div className={'flex flex-col gap-4'}>
                            <p className={'text-xl text-border font-bold'}>Danh sách ghế</p>
                            <table>
                                <tr className={'border-border border-2'}>
                                    <th>Ghế</th>
                                    <th>Loại vé</th>
                                    <th>Giá</th>
                                </tr>
                                <tbody>
                                {bill?.chairs.map((chair, index) => (
                                    <tr key={index}
                                        className="odd:bg-white even:bg-gray-100 transition">
                                        <td className="px-6 py-3 border border-foreground">
                                            {String.fromCharCode(65 + parseInt(chair.chairCode[1])) + chair.chairCode[3]}
                                        </td>
                                        <td className="px-6 py-3 border border-foreground">
                                            {parseInt(chair.price).toLocaleString()} VND
                                        </td>
                                        <td className="px-6 py-3 border border-foreground">
                                            {chair.ticket.name}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                        <div className={'flex flex-col'}>
                            <p className={'text-xl text-border font-bold'}>Danh sách món ăn</p>
                            <table>
                                <tr className={'border-border border-2'}>
                                    <th>Món ăn</th>
                                    <th>Loại</th>
                                    <th>Số lượng</th>
                                    <th>Giá</th>
                                </tr>
                                <tbody>
                                {bill?.dishes.map((dish, index) => (
                                    <tr key={index}
                                        className="odd:bg-white even:bg-gray-100 transition">
                                        <td className="px-6 py-3 border border-foreground">
                                            {dish.dishDto.name}
                                        </td>
                                        <td className="px-6 py-3 border border-foreground">
                                            {dish.price.toLocaleString()} VND
                                        </td>
                                        <td className="px-6 py-3 border border-foreground">
                                            {dish.amount}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Step_3;