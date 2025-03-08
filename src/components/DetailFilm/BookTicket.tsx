import React from "react";

const BookTicket : React.FC = () => {
    return(
        <>
            <div className={'flex justify-center items-center mt-[60px]'}>
                <div className={'container'}>
                    <div className={'flex items-center justify-center'}>
                        <p className={'text-white uppercase text-3xl font-bold'}>Lịch Chiếu</p>
                    </div>
                    <div className={'flex justify-center items-center mt-[20px] gap-4'}>
                        <div
                            className={'bg-main text-white px-2 py-2 rounded-[10px] flex justify-center items-center flex-col font-bold cursor-pointer border-2 border-main hover:bg-white hover:text-main transition-all duration-300'}>
                            <p><span>8:30</span> - <span>9:30</span></p>
                            <p>Thứ Bảy</p>
                        </div>
                        <div
                            className={'bg-main text-white px-2 py-2 rounded-[10px] flex justify-center items-center flex-col font-bold cursor-pointer border-2 border-main hover:bg-white hover:text-main transition-all duration-300'}>
                            <p><span>8:30</span> - <span>9:30</span></p>
                            <p>Thứ Bảy</p>
                        </div>
                        <div
                            className={'bg-main text-white px-2 py-2 rounded-[10px] flex justify-center items-center flex-col font-bold cursor-pointer border-2 border-main hover:bg-white hover:text-main transition-all duration-300'}>
                            <p><span>8:30</span> - <span>9:30</span></p>
                            <p>Thứ Bảy</p>
                        </div>
                        <div
                            className={'bg-main text-white px-2 py-2 rounded-[10px] flex justify-center items-center flex-col font-bold cursor-pointer border-2 border-main hover:bg-white hover:text-main transition-all duration-300'}>
                            <p><span>8:30</span> - <span>9:30</span></p>
                            <p>Thứ Bảy</p>
                        </div>s
                    </div>
                </div>
            </div>
        </>
    )
}

export default BookTicket;