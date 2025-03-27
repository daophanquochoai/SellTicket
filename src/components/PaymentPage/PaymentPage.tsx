import React, {lazy, useEffect, useRef, useState} from "react";
import {useCommonContext} from "../../context/CommonContext.tsx";
import {toast} from "react-toastify";
import {paymentBill} from "../../Helper/Helper.ts";
import {Spin} from "antd";
import {useNavigate} from "react-router-dom";

const Step_2 = lazy(() => import("./Step_2.tsx"));
const Step_3 = lazy(() => import("./Step_3.tsx"));
interface Time {
    minute : string,
    second : string
}
interface User{
    userName : string,
    phoneNumber : string,
    email : string,
    quyDinh : boolean,
    dieuKhoan : boolean
}
const initTime = {
    minute : "5",
    second : "00"
}
const initUser = {
    userName : "",
    phoneNumber : "",
    email : "",
    quyDinh : false,
    dieuKhoan : false
}
const PaymentPage : React.FC = () => {

    const [step, setStep] = useState<number>(1);
    const {bill, setBill} = useCommonContext();
    const [oclock, setOClock] = useState<Time>(initTime);
    const intervalRef = useRef<NodeJS.Timeout | null>(null);
    const [user, setUser] = useState<User>(initUser);
    const [loadingForm, setLoadingForm] = useState<boolean>(false);
    const navigate = useNavigate();

    useEffect(() => {
        handleOClock();
        return () => {
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
            }
        };
    });

    const handleOClock = () => {
        intervalRef.current = setInterval(() => {
            setOClock((prev) => {
                const min = parseInt(prev.minute, 10);
                const sec = parseInt(prev.second, 10);
                if(  min == 0 && sec == 0 ){
                    toast.warning(<p>Đã hết thời gian giữ chỗ</p>);
                    clearInterval(intervalRef.current!);
                    navigate("/")
                    return { minute: '05', second: '00'};
                }
                if (sec === 0) {
                    return { minute: (min - 1).toString().padStart(2, "0"), second: "59" };
                } else {
                    return { minute: prev.minute, second: (sec - 1).toString().padStart(2, "0") };
                }
            });
        }, 1000);
    }
    const handleSubmit = async (e) => {
         e.preventDefault();
         if( !user.quyDinh || !user.dieuKhoan){
             toast.warning(<p className={'w-full'}>Vui lòng đồng ý với các điêu khoản</p>);
             return;
         }
         if( user.email.length < 1 || user.userName.length < 1 || user.phoneNumber.length < 10 || user.phoneNumber.length > 12 ){
             toast.warning(<p className={'w-full'}>Vui lòng điền đầy đủ các thông tin</p>);
             return;
         }
         setBill({...bill, userName : user.userName,email : user.email, numberPhone : user.phoneNumber, paymentMethodId : "1"})

        if( bill == undefined ){
            toast.error(<p className={'w-full'}>Thanh toán thất bại</p>)
        }else{
            if( bill.id == '' ){
                setLoadingForm(true);
                const response = await paymentBill({...bill, userName : user.userName,email : user.email, numberPhone : user.phoneNumber, paymentMethodId : "1"});
                setLoadingForm(false)
                console.log(response);
                if( response.status !== 201){
                    toast.warning(<p className={'w-full'}>Lỗi thông tin hóa đơn</p>)
                    return;
                }
                setBill({...bill, id: response.data.data.id})
                scroll(0,0);
            }
            scroll(0,0);
            setStep(2);

        }
    }

    return (
        <>
            <div className={'flex justify-center items-center mt-[60px]'}>
                <div className={'container'}>
                    <div className={'flex flex-col'}>
                        <div>
                            <p className={'uppercase text-3xl font-bold text-white'}>Trang thanh toán</p>
                        </div>
                        <div className={'flex justify-start items-center gap-4 mt-[40px]'}>
                            <div className={'flex flex-col justify-center items-center'}>
                                <p className={`text-xl font-bold ${step < 1 ? 'text-white' : 'text-main'}`}>1</p>
                                <p className={`text-xl font-bold ${step < 1 ? 'text-white' : 'text-main'}`}>Thông tin khách hàng</p>
                            </div>
                            <div className={`w-[50px] h-[2px] ${step < 2 ? 'bg-gray-800' : 'bg-main'}`}></div>
                            <div className={'flex flex-col justify-center items-center'}>
                                <p className={`text-xl font-bold ${step < 2 ? 'text-white' : 'text-main'}`}>2</p>
                                <p className={`text-xl font-bold ${step < 2 ? 'text-white' : 'text-main'}`}>Thanh toán</p>
                            </div>
                            <div className={`w-[50px] h-[2px] ${step < 3 ? 'bg-gray-800' : 'bg-main'}`}></div>
                            <div className={'flex flex-col justify-center items-center'}>
                                <p className={`text-xl font-bold ${step < 3 ? 'text-white' : 'text-main'}`}>3</p>
                                <p className={`text-xl font-bold ${step < 3 ? 'text-white' : 'text-main'}`}>Thông tin vé phim</p>
                            </div>
                        </div>
                            <div className={'flex gap-4 mt-[40px]'}>
                                {
                                    step !== 3 &&
                                    <>
                                        <div className={'w-1/2'}>
                                            {
                                                step === 1 &&
                                                <Spin tip={"Đang tải..."} spinning={loadingForm} size={"default"}>
                                                    <form onSubmit={(e) => handleSubmit(e)}
                                                          className={'flex flex-col gap-6'}>
                                                        <div className={'flex flex-col gap-2'}>
                                                            <label className={'text-white'}>Họ và tên <span
                                                                className={'text-red-900'}>*</span></label>
                                                            <input
                                                                value={user.userName}
                                                                minLength={10}
                                                                onChange={(e) => setUser({
                                                                    ...user,
                                                                    userName: e.target.value
                                                                })}
                                                                className={'px-6 py-3 outline-0 border-none bg-foreground text-white w-full'}
                                                                placeholder={'Nhập họ và tên'}/>
                                                        </div>
                                                        <div className={'flex flex-col gap-2'}>
                                                            <label className={'text-white'}>Số điện thoại <span
                                                                className={'text-red-900'}>*</span></label>
                                                            <input
                                                                value={user.phoneNumber}
                                                                minLength={10}
                                                                maxLength={12}
                                                                onChange={(e) => setUser({
                                                                    ...user,
                                                                    phoneNumber: e.target.value
                                                                })}
                                                                className={'px-6 py-3 outline-0 border-none bg-foreground text-white w-full'}
                                                                placeholder={'Nhập số điện thoại'}/>
                                                        </div>
                                                        <div className={'flex flex-col gap-2'}>
                                                            <label className={'text-white'}>Email <span
                                                                className={'text-red-900'}>*</span></label>
                                                            <input
                                                                value={user.email}
                                                                onChange={(e) => setUser({
                                                                    ...user,
                                                                    email: e.target.value
                                                                })}
                                                                type={"email"}
                                                                className={'px-6 py-3 outline-0 border-none bg-foreground text-white w-full'}
                                                                placeholder={'Nhập email'}/>
                                                        </div>
                                                        <div>
                                                            <div>
                                                                <div
                                                                    className={'flex items-center justify-start gap-2'}>
                                                                    <input checked={user.quyDinh}
                                                                           onChange={() => setUser({
                                                                               ...user,
                                                                               quyDinh: !user.quyDinh
                                                                           })} type={"checkbox"}
                                                                           className={'accent-main'}/>
                                                                    <p className={'text-white text-[14px]'}>Đảm bảo mua
                                                                        vé
                                                                        đúng
                                                                        quy
                                                                        định tuổi</p>
                                                                </div>
                                                                <div
                                                                    className={'flex items-center justify-start gap-2'}>
                                                                    <input checked={user.dieuKhoan}
                                                                           onChange={() => setUser({
                                                                               ...user,
                                                                               dieuKhoan: !user.dieuKhoan
                                                                           })} type={"checkbox"}
                                                                           className={'accent-main'}/>
                                                                    <p className={'text-white text-[14px]'}>Đồng ý với
                                                                        điều
                                                                        khoản
                                                                        của chúng tôi</p>
                                                                </div>
                                                            </div>
                                                            <div className={'mt-[10px]'}>
                                                                <button
                                                                    className={'w-full flex justify-center items-center bg-main px-4 py-2 uppercase font-bold text-white text-[16px] hover:text-main hover:bg-white transition-all duration-300'}>Tiếp
                                                                    tục
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </Spin>
                                            }
                                            {
                                                step === 2 &&
                                                <>
                                                    <Step_2 setStep={setStep}/>
                                                </>
                                            }
                                        </div>
                                        <div className={'flex flex-1'}>
                                            <div className={'bg-main flex-1 flex-col flex gap-4 p-4'}>
                                                <div className={'flex justify-between items-center'}>
                                                    <div>
                                                        <p className={'text-white font-bold text-[26px] uppercase'}>{bill?.nameFilm}</p>
                                                    </div>
                                                    <div className={'flex gap-4 items-center'}>
                                                        <p className={'text-white font-bold'}>Thời gian giữ vé :</p>
                                                        <div className={'bg-foreground px-2 py-1'}>
                                                            <p ref={intervalRef} className={'text-white font-bold'}>
                                                                <span>{oclock.minute}</span>:<span>{oclock.second}</span>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className={'flex flex-col'}>
                                                    <h4 className={'text-border text-xl font-bold uppercase'}>{bill?.nameBranch}</h4>
                                                    <p className={'text-white uppercase'}>{bill?.address}</p>
                                                </div>
                                                <div className={'flex justify-start flex-col'}>
                                                    <h4 className={'text-border text-xl font-bold uppercase'}>Thời
                                                        gian
                                                        :</h4>
                                                    <p className={'text-white uppercase'}>{bill?.timeStart} {bill?.timeStampSee}</p>
                                                </div>
                                                <div className={'flex gap-4 items-center'}>
                                                    <div>
                                                        <h4 className={'text-border text-xl font-bold uppercase'}>Phòng
                                                            chiếu</h4>
                                                        <p className={'text-white uppercase'}>{bill?.nameRoom}</p>
                                                    </div>
                                                </div>
                                                {
                                                    bill?.chairs && bill.chairs.length > 0 &&
                                                    <div className={'flex flex-col gap-2'}>
                                                        <div className={'text-medium font-bold'}>
                                                            <p className={'text-border text-xl font-bold uppercase'}>Danh
                                                                sách ghế ngồi</p>
                                                        </div>
                                                        <table
                                                            className="w-full text-center border-collapse border border-foreground shadow-md">
                                                            <thead>
                                                            <tr className="bg-gray-200 text-main">
                                                                <th className="px-6 py-3 border border-foreground">Mã
                                                                    Ghế
                                                                </th>
                                                                <th className="px-6 py-3 border border-foreground">Giá</th>
                                                                <th className="px-6 py-3 border border-foreground">Vé</th>
                                                            </tr>
                                                            </thead>
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
                                                }
                                                {
                                                    bill?.dishes && bill?.dishes.length > 0 &&
                                                    <div className={'flex flex-col gap-2'}>
                                                        <div className={'text-medium font-bold'}>
                                                            <p className={'text-border text-xl font-bold uppercase'}>Danh
                                                                sách bắp nước</p>
                                                        </div>
                                                        <table
                                                            className="w-full text-center border-collapse border border-foreground shadow-md">
                                                            <thead>
                                                            <tr className="bg-gray-200 text-main">
                                                                <th className="px-6 py-3 border border-foreground">Tên
                                                                </th>
                                                                <th className="px-6 py-3 border border-foreground">Giá</th>
                                                                <th className="px-6 py-3 border border-foreground">Số
                                                                    lượng
                                                                </th>
                                                            </tr>
                                                            </thead>
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
                                                }
                                                <div className={'border-dashed border-2 w-full h-[1px]'}></div>
                                                <div className={'flex justify-between'}>
                                                    <p className={'text-border text-2xl uppercase font-bold'}>Số tiền
                                                        thanh toán</p>
                                                    <p className={'text-2xl font-bold uppercase text-white'}>{bill?.totalPrice.toLocaleString()}Đ</p>
                                                </div>
                                            </div>
                                        </div>
                                    </>
                                }
                                {
                                    step === 3 &&
                                    <>
                                        <Step_3/>
                                    </>
                                }
                            </div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default PaymentPage;