import React, {useState} from "react";
import {ClockCircleOutlined, PushpinOutlined} from "@ant-design/icons";
import {FaCirclePlay, FaEarthAmericas} from "react-icons/fa6";

const Description : React.FC = () => {

    const text = "Mỗi ngày trong cuộc sống Ranti đều xoay quanh bởi sự cay nghiệt của mẹ chồng cùng những lời vu cáo của em dâu khiến cô vô cùng thống khổ. Mọi việc trở nên bùng nổ khi họ lần lượt phớt lờ việc cứu lấy cô con gái gặp tai nạn, thúc đẩy Ranti phải “ tiến thêm bước nữa” với ác quỷ ,mà từ đó lần lượt những người đối xử tệ với cô đều phải trả giá bằng mạng sống.";

    const [isExpanded, setIsExpanded] = useState<boolean>(false);
    const limit = 500;

    return(
        <>
            <div className={'flex justify-center items-center mt-[20px]'}>
                <div className={'container flex gap-6'}>
                    <div className={'w-2/5'}>
                        <img
                            src={'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F03_2025%2Fhien-me-cho-quy_1.jpg&w=1920&q=75'}
                            alt={'icon'}
                            className={'w-full h-auto object-contain'}
                        />
                    </div>
                    <div className={'w-3/5 py-[20px] px-[40px]'}>
                        <h3 className={'text-[36px] text-white font-bold'}>CƯỚI MA (T18)</h3>
                        <div className={'gap-[10px] flex flex-col mt-[20px]'}>
                            <div className={'flex gap-2'}>
                                <div className={'text-main text-[18px]'}><PushpinOutlined/></div>
                                <div className={'text-[18px]'}>
                                    <p className={'text-white uppercase'}>Kinh Dị</p>
                                    <p className={'text-white uppercase'}>Tình Cảm</p>
                                </div>
                            </div>
                            <div className={'flex gap-2 items-center'}>
                                <div className={'text-main text-[18px]'}><ClockCircleOutlined/></div>
                                <p className={'text-white text-[18px]'}>122'</p>
                            </div>
                            <div className={'flex gap-2 items-center'}>
                                <div className={'text-main text-[18px]'}><FaEarthAmericas/></div>
                                <p className={'text-white text-[18px]'}>VIỆT NAM</p>
                            </div>
                        </div>
                        <div className={'mt-[20px]'}>
                            <h4 className={'text-[32px] font-bold text-main uppercase'}>Mô tả</h4>
                            <p className={'text-[16px] text-white'}>
                                Đạo diễn: Azhar Kinoi Lubis<br/>
                                Diễn viên: Taskya Namya, Wafda Saifan Lubis, Arla Ailani<br/>
                                Khởi chiếu: Thứ Sáu, 28/02/2025
                            </p>
                        </div>
                        <div className={'mt-[20px]'}>
                            <h4 className={'text-[32px] font-bold text-main uppercase'}>Nội dung</h4>
                            <div >
                                <p className={'text-[16px] text-white'}>
                                    {isExpanded ? text :  text.length < limit ? text : text.substring(0, text.lastIndexOf(" ", limit)) + "..."}
                                </p>
                                <div>
                                    {text.length > limit && (
                                        <span
                                            className="text-main underline cursor-pointer"
                                            onClick={() => setIsExpanded(!isExpanded)}
                                        >
                                        {isExpanded ? "Thu gọn" : "Xem thêm"}
                                    </span>
                                    )}
                                </div>
                            </div>
                        </div>
                        <div className={'flex justify-between mt-[40px] px-[40px] items-center'}>
                            <div className={'flex items-center gap-2 cursor-pointer'}>
                                <p className={'text-[30px] text-white'}><FaCirclePlay /></p>
                                <p className={'text-main underline'}>Xem Trailer</p>
                            </div>
                            <div
                                className="relative flex justify-center overflow-hidden group rounded-xl border-2 border-main text-main cursor-pointer"
                                >
                                <span
                                    className="absolute inset-0 w-full h-full bg-main -left-full transition-all duration-500 ease-in-out group-hover:left-0"></span>
                                    <p className="relative px-4 py-2 z-10 font-medium group-hover:text-white transition-all duration-500 ease-in-out ">Đặt vé ngay</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Description;