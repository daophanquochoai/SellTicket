import React from "react";

interface Time {
    minute : string,
    second : string
}
interface Props {
    time : Time,
    price : number,
    filmName : string,
    nameBranch : string,
    payment : boolean,
    handlePayment : () => void
}

const Payment : React.FC<Props> = ( props ) => {
    return (
        <>
            <div className={'flex justify-center items-center mt-[80px] mb-[-100px] bg-border sticky bottom-0'}>
                <div className={'container py-2'}>
                    <div className={'flex justify-between items-center'}>
                        <div>
                            <div>
                                <p className={'text-2xl font-medium text-main'}>{props.filmName}</p>
                                <span className={'text-white'}>{props.nameBranch}</span>
                            </div>
                        </div>
                        <div className={'flex items-center gap-4'}>
                            <div className={'bg-main py-2 px-4'}>
                                <p className={'text-white font-[500]'}>Thời gian giữ vé:</p>
                                <span className={'text-3xl font-bold text-white'}>{props.time.minute}:{props.time.second}</span>
                            </div>
                            <div className={'flex flex-col gap-4'}>
                                <div className={'flex gap-4 items-center justify-between'}>
                                    <span className={'font-[500] text-white'}>Tạm tính</span>
                                    <span className={'text-2xl font-bold text-white'}>{props.price.toLocaleString()} VND</span>
                                </div>
                                <button className={`bg-main px-4 py-2 flex justify-center w-[200px]`} onClick={() => props.handlePayment()}>
                                    <p className={'text-white font-bold'}>Đặt vé</p>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Payment;