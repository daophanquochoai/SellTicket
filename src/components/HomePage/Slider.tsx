import React from "react";
// @ts-ignore
import 'swiper/css';
// @ts-ignore
import 'swiper/css/navigation';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, Pagination, Navigation } from 'swiper/modules';
import './style.css';

const Slider :React.FC = () => {

    const listSlide = [
        '/public/slide1.png',
        '/public/slide2.png',
        '/public/slide3.png',
    ]

    return(
        <>
            <div className={'flex justify-center'}>
                <div className={"container"}>
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
                            listSlide.map( (item, index) => {
                                return (
                                    <SwiperSlide className={'overflow-hidden'} key={index}>
                                        <img src={item} alt={'slide'} className={'w-full h-full object-cover '}/>
                                    </SwiperSlide>
                                )
                            })
                        }
                    </Swiper>
                </div>
            </div>
        </>
    )
}

export default Slider;