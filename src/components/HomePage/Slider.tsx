import React, {useEffect, useState} from "react";
import 'swiper/css';
import 'swiper/css/navigation';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, Pagination, Navigation } from 'swiper/modules';
import './style.css';
import {getSlider} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {Spin, UploadFile} from "antd";

const Slider :React.FC = () => {

    //var
    const [data,setData] = useState<UploadFile[]>([]);
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        handleFetchSlider();
    }, []);

    const handleFetchSlider = async () => {
        setLoading(true);
        const response = await getSlider();
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải slide</p>)
            return;
        }
        setData([...response.data.data.map(item => {
            return {
                uid: item.id,
                name: item.name,
                status: 'done',
                url: item.image,
            }
        })]);
    }

    return(
        <>
            <div className={'flex justify-center'}>
                <div className={"container"}>
                    <Spin tip={'Đang tải thông tin...'} spinning={loading}>
                        <Swiper spaceBetween={30}
                                centeredSlides={true}
                                autoplay={{
                                    delay: 2500,
                                    disableOnInteraction: false,
                                }}
                                pagination={{
                                    clickable: true,
                                }}
                                navigation={true}
                                modules={[Autoplay, Pagination, Navigation]}
                                className="mySwiper min-h-[400px]"
                        >
                            {
                                data && data.map( (item, index) => {
                                    return (
                                        <SwiperSlide className={'overflow-hidden'} key={index}>
                                            <img src={item.url} alt={'slide'} className={'w-full h-full object-cover '}/>
                                        </SwiperSlide>
                                    )
                                })
                            }
                        </Swiper>
                    </Spin>
                </div>
            </div>
        </>
    )
}

export default Slider;