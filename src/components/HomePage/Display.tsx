import React, {useEffect, useState} from "react";
import {handleSlide} from "../../Helper/Helper.ts";
import {Spin} from "antd";
import {Autoplay, Navigation} from "swiper/modules";
import { Swiper, SwiperSlide } from 'swiper/react';
import {toast} from "react-toastify";
import {ClockCircleOutlined, PushpinOutlined} from "@ant-design/icons";
import { FaEarthAmericas } from "react-icons/fa6";
import TrailerFunction from './Trailer.tsx';
import {useNavigate} from "react-router-dom";

interface Film {
    id : string,
    name : string,
    age : number,
    sub : Sub[],
    image : string,
    description : string,
    content : string,
    trailer : string,
    typeFilms : TypeFilm[],
    status : string,
    nation : string,
    duration : string
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

interface Props{
    title : string,
    active : string
}

interface Trailer {
    active : boolean,
    url : string
}
const initTrailer = {
    active : false,
    url : ''
}

const Display : React.FC<Props> = ({title,active}) => {

    const [loading, setLoading] = useState<boolean>(false);
    const [filmList, setFilmList] = useState<Film[]>([]);
    const [isTrailer, setIsTrailer] = useState<Trailer>(initTrailer);
    const navigation = useNavigate();

    useEffect(() => {
        handleFetchSlider();
    }, []);

    const handleFetchSlider = async ( ) => {
        setLoading(true);
        const response = await handleSlide(active);
        setLoading(false);
        if( response.status !== 200 ){
            toast.error("Không thể kết nối với máy chủ");
            return;
        }
        const data : Film[] = await response.data.data;
        setFilmList(data);
    }

    const handleToDetailFilm = ( id : string) => {
        navigation('/film/' + id);
        scroll(0,0)
    }

    return(
        <>
            <div className={"w-full flex justify-center mt-[50px]"}>
                <div className={"container"}>
                    <div className={'flex justify-center'}>
                        <p className={'text-white text-3xl font-bold uppercase'}>{title}</p>
                    </div>
                    <Spin tip={<span className={"text-xl"}>Loading...</span>} spinning={loading} size={"default"} className={'text-main'} >
                        <Swiper
                        spaceBetween={20}
                        slidesPerView={4}
                        navigation
                        autoplay={{delay: 3000, disableOnInteraction: false}}
                        modules={[Navigation, Autoplay]}
                        className={'mt-[30px]'}
                        breakpoints={{
                            640: { slidesPerView: 2, spaceBetween: 20 }, // >= 640px: 2 slides
                            768: { slidesPerView: 3, spaceBetween: 20 }, // >= 768px: 3 slides
                            1024: { slidesPerView: 4, spaceBetween: 30 }, // >= 1024px: 4 slides
                            1536 : { slidesPerView: 5, spaceBetween: 30 },
                        }}
                        >
                        {
                            filmList && filmList.map((item) => (
                                <SwiperSlide key={item.id}>
                                        <div className={'relative group overflow-hidden cursor-pointer'}>
                                            {
                                                item.image != null
                                                    ?
                                                    <div className={'relative'} onClick={()=>handleToDetailFilm(item.id)}>
                                                        <img
                                                            src={item.image}
                                                            alt={`Film`}
                                                            className={'w-full h-[420px]'}
                                                        />
                                                        {/* hover */}
                                                        <div className={'absolute group-hover:opacity-100 top-0 opacity-0 transition-all duration-700 w-full h-[420px] bg-black/60 z-2 flex items-center justify-start'}>
                                                            <div className={'flex flex-col gap-4 px-8'}>
                                                                <h4 className={'text-xl text-white uppercase'}>{item.name}</h4>
                                                                <div className={'flex items-center gap-2'}>
                                                                    <div className={'text-main'}><ClockCircleOutlined/></div>
                                                                    <p className={'text-white'}>{item.duration}</p>
                                                                </div>
                                                                <div className={'flex gap-2'}>
                                                                    <div className={'text-main'}><PushpinOutlined/></div>
                                                                    <div className={'flex flex-col items-start max-h-[50px] overflow-hidden'}>
                                                                        {
                                                                            item.typeFilms.map( tFilm => {
                                                                                return (
                                                                                    <p className={'text-white'} key={tFilm.id}>{tFilm.name}</p>
                                                                                )
                                                                            })
                                                                        }
                                                                    </div>
                                                                </div>
                                                                <div className={'flex items-center gap-2'}>
                                                                    <div className={'text-main'}><FaEarthAmericas/></div>
                                                                    <p className={'text-white'}>{item.nation}</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    :
                                                    <>
                                                        <div className={'w-full h-[420px] bg-green-50'}></div>
                                                    </>
                                            }
                                            <div className={'flex-col flex justify-start gap-2 absolute z-5 top-5 left-5 group-hover:top-[-100px] transition-all duration-500 max-h-[100px]'}>
                                                {
                                                    item.sub.map(sub => {
                                                        return (
                                                            <div key={sub.id}>
                                                                <div>
                                                                <span
                                                                    className={'p-1 text-main uppercase font-bold text-xs border-main border-2'}>{sub.name}</span>
                                                                </div>
                                                            </div>
                                                        )
                                                    })

                                                }

                                            </div>
                                        </div>
                                        <div className={'w-full'}>
                                            <div className={'flex justify-center h-[100px]'}>
                                                <p className={'text-xl uppercase font-medium text-white mt-5 mb-8'}>{item.name}</p>
                                            </div>
                                            <div className={'flex justify-between px-4 gap-4'}>
                                                <button className={'flex-1 text-main underline font-medium hover:text-white'} onClick={()=>setIsTrailer({active : true, url : item.trailer})}>Xem Trailer</button>
                                                <button
                                                    onClick={()=>handleToDetailFilm(item.id)}
                                                    className={'rounded-xl px-4 py-2 bg-main text-white flex-1 border-2 border-main hover:text-main hover:bg-white transition-all duration-300'}>Đặt vé</button>
                                            </div>
                                        </div>
                                    </SwiperSlide>
                            ))
                        }
                        </Swiper>
                    </Spin>
                </div>
            </div>
            <TrailerFunction active={isTrailer.active} url={isTrailer.url} setActive={setIsTrailer}/>
        </>
    )
}
export default Display;