import React from "react";

const Intro : React.FC = () => {
    return (
        <>
            <div className={'flex justify-center items-center mt-[40px]'}>
                <div className={'container'}>
                    <div>
                        <div className={'relative bg-url("/intro.png")'}>
                            <img src={'/intro.png'} className={'w-full h-full'}/>
                            <div className={'bg-border absolute bottom-0 w-full h-full opacity-70 z-[5] flex flex-col justify-center items-center px-[40px]'}>
                             </div>
                            <div className={'absolute top-0 left-0 z-[6] flex justify-center items-center flex-col h-full'}>
                                <h4 className={'text-white text-2xl font-bold'}>HỆ THỐNG CỤM RẠP TRÊN TOÀN QUỐC</h4>
                                <p className={'text-center mt-[10px] px-[40px] text-textCol'}>Rạp chiếu phim MovieTicket mang đến trải nghiệm điện ảnh đẳng cấp với hệ thống phòng
                                    chiếu hiện đại, âm thanh sống động và màn hình sắc nét. Chúng tôi cung cấp đa dạng
                                    thể loại phim, từ bom tấn Hollywood đến phim Việt đặc sắc, cùng các chương trình ưu
                                    đãi hấp dẫn dành cho khách hàng. Với không gian sang trọng, dịch vụ chuyên nghiệp và
                                    tiện ích đi kèm như bắp rang, nước uống phong phú, MovieTicket hứa hẹn là điểm đến
                                    lý tưởng cho mọi tín đồ điện ảnh.</p>
                            </div>
                        </div>
                        <div className={'mt-[60px]'}>
                            <div className={'flex flex-col items-center justify-center gap-4'}>
                                <h4 className={'text-3xl text-textCol font-bold'}>THUÊ SỰ KIỆN</h4>
                                <p className={'text-textCol'}>Lên kế hoạch cho một sự kiện?</p>
                                <p className={'text-textCol'}>Chúng tôi có nhiều lựa chọn để giúp sự kiện của bạn trở nên khó quên.</p>
                            </div>
                            <div className={'mt-[40px]'}>
                                <div className={'flex items-center'}>
                                    <div className={'w-1/2 pr-[40px] gap-4 flex flex-col justify-start'}>
                                        <h5 className={'text-2xl text-textCol font-bold uppercase'}>Fanclub, Cầu hôn, Sinh nhật</h5>
                                        <p className={'text-textCol'}>MovieTicket kỳ vọng sẽ đứng đằng sau làm sân khấu để tôn vinh câu chuyện
                                            của doanh nghiệp bạn.

                                            Để biết thêm thông tin về việc thuê, vui lòng gọi: 09662623**</p>
                                        <div>
                                            <div
                                                className="relative inline-flex justify-center overflow-hidden bg-main group border-2 border-main text-main cursor-pointer"
                                            >
                                                <span
                                                    className="absolute inset-0 w-full h-full bg-textCol -left-full transition-all duration-500 ease-in-out group-hover:left-0 "></span>
                                                <p className="relative px-4 py-2 z-10 font-medium text-white group-hover:text-main transition-all duration-500 ease-in-out ">Liên hệ ngay</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div className={'flex-1'}>
                                        <img src={'/sk_1.png'} className={'w-full'}/>
                                    </div>
                                </div>
                                <div className={'flex items-center'}>
                                    <div className={'flex-1'}>
                                        <img src={'/sk_2.png'} className={'w-full'}/>
                                    </div>
                                    <div className={'w-1/2 flex flex-col text-start gap-4 px-[40px]'}>
                                        <h5 className={'text-textCol text-2xl font-bold uppercase'}>Ra Mắt Chương Trình, Họp Nôị Bộ, Music Video, Ra
                                            Mắt Phim</h5>
                                        <p className={'text-textCol'}>MovieTicket kỳ vọng sẽ đứng đằng sau làm sân khấu
                                            để tôn vinh câu chuyện của doanh nghiệp bạn.

                                            Để biết thêm thông tin về việc thuê, vui lòng gọi: 0966262**</p>
                                        <div>
                                            <div
                                                className="relative inline-flex justify-center overflow-hidden bg-main group border-2 border-main text-main cursor-pointer"
                                            >
                                                <span
                                                    className="absolute inset-0 w-full h-full bg-textCol -left-full transition-all duration-500 ease-in-out group-hover:left-0 "></span>
                                                <p className="relative px-4 py-2 z-10 font-medium text-white group-hover:text-main transition-all duration-500 ease-in-out ">Liên
                                                    hệ ngay</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className={'flex items-center'}>
                                    <div className={'w-1/2 gap-4 flex flex-col pr-[40px]'}>
                                        <h5 className={'text-textCol text-2xl font-bold uppercase'}>Nội Bộ, Film Festival</h5>
                                        <p className={'text-textCol'}>MovieTicket kỳ vọng sẽ đứng đằng sau làm sân khấu để tôn vinh câu chuyện của
                                            doanh nghiệp bạn.

                                            Để biết thêm thông tin về việc thuê, vui lòng gọi: 09662623**</p>
                                        <div>
                                            <div
                                                className="relative inline-flex justify-center overflow-hidden bg-main group border-2 border-main text-main cursor-pointer"
                                            >
                                                <span
                                                    className="absolute inset-0 w-full h-full bg-textCol -left-full transition-all duration-500 ease-in-out group-hover:left-0 "></span>
                                                <p className="relative px-4 py-2 z-10 font-medium text-white group-hover:text-main transition-all duration-500 ease-in-out ">Liên
                                                    hệ ngay</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div className={'flex-1'}>
                                        <img src={'/sk_3.png'} className={'w-full'}/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
};
export default Intro;