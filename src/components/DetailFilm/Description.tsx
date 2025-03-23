import React, {useEffect, useState} from "react";
import {ClockCircleOutlined, PushpinOutlined} from "@ant-design/icons";
import {FaCirclePlay, FaEarthAmericas} from "react-icons/fa6";
import TrailerFunction from "../HomePage/Trailer.tsx";

interface Film {
    id: string,
    name : string,
    age : 0,
    image : string,
    sub : Sub[],
    nation : string,
    duration : string,
    description : string,
    content : string,
    trailer : string,
    typeFilms : TypeFilm[],
    status : string
}
interface Sub {
    id : string,
    sub : string
}
interface TypeFilm {
    id : string,
    name : string,
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
interface Props {
    film : Film,
    bookRef : NodeJS.Timeout | null
}
const Description : React.FC<Props> = ( props ) => {

    const {film} = props;

    const [isExpanded, setIsExpanded] = useState<boolean>(false);
    const [isTrailer, setIsTrailer] = useState<Trailer>(initTrailer);
    const limit = 500;

    useEffect(() => {
        setIsTrailer({
            active : false,
            url : film.trailer
            })
    }, [film]);

    const handleBook = () => {
        props.bookRef.current.scrollIntoView({ behavior: "smooth" });
    }

    return(
        <>
            <div className={'flex justify-center items-center mt-[20px]'}>
                <div className={'container flex gap-6'}>
                    <div className={'w-2/5'}>
                        <img
                            src={film.image || null}
                            alt={'icon'}
                            className={'w-full h-auto object-contain'}
                        />
                    </div>
                    <div className={'w-3/5 py-[20px] px-[40px]'}>
                        <h3 className={'text-[36px] text-white font-bold'}>{film.name}</h3>
                        <div className={'gap-[10px] flex flex-col mt-[20px]'}>
                            {
                                film.sub.length > 0 &&
                                <>
                                    <div className={'flex gap-2'}>
                                        <div className={'text-main text-[18px]'}><PushpinOutlined/></div>
                                        <div className={'text-[18px]'}>
                                            {
                                                film.sub.map( item => {
                                                    return (
                                                        <div key={item.id}>
                                                            <p className={'text-white uppercase'}>{item.sub}</p>
                                                        </div>
                                                    )
                                                })
                                            }
                                        </div>
                                    </div>
                                </>
                            }
                            {
                                film.duration &&
                                <>
                                    <div className={'flex gap-2 items-center'}>
                                        <div className={'text-main text-[18px]'}><ClockCircleOutlined/></div>
                                        <p className={'text-white text-[18px]'}>{film.duration}</p>
                                    </div>
                                </>
                            }
                            {
                                film.nation &&
                                <>
                                    <div className={'flex gap-2 items-center'}>
                                        <div className={'text-main text-[18px]'}><FaEarthAmericas/></div>
                                        <p className={'text-white text-[18px]'}>{film.nation}</p>
                                    </div>
                                </>
                            }
                        </div>
                        {
                            film.content &&
                            <>
                                <div className={'mt-[20px]'}>
                                    <h4 className={'text-[32px] font-bold text-main uppercase'}>Mô tả</h4>
                                    <p className={'text-[16px] text-white'}
                                       dangerouslySetInnerHTML={{__html : film.content}}
                                    />
                                </div>
                            </>
                        }
                        {
                            film.description &&
                            <>
                                <div className={'mt-[20px]'}>
                                    <h4 className={'text-[32px] font-bold text-main uppercase'}>Nội dung</h4>
                                    <div>
                                        <p
                                            className="text-[16px] text-white"
                                            dangerouslySetInnerHTML={{__html: isExpanded ? film.description : film.description.length < limit ? film.description : film.description.substring(0, film.description.lastIndexOf(" ", limit)) + "..."}}
                                        />
                                        <div>
                                            {film.description.length > limit && (
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
                            </>
                        }
                        <div className={'flex justify-between mt-[40px] px-[40px] items-center'}>
                            <div className={'flex items-center gap-2 cursor-pointer'} onClick={() => setIsTrailer({...isTrailer, active : true})}>
                                <p className={'text-[30px] text-white'}><FaCirclePlay/></p>
                                <p className={'text-main underline'}>Xem Trailer</p>
                            </div>
                            <div
                                className="relative flex justify-center overflow-hidden group rounded-xl border-2 border-main text-main cursor-pointer"
                                onClick={()=>handleBook()}
                            >
                                <span
                                    className="absolute inset-0 w-full h-full bg-main -left-full transition-all duration-500 ease-in-out group-hover:left-0"></span>
                                <p className="relative px-4 py-2 z-10 font-medium group-hover:text-white transition-all duration-500 ease-in-out ">Đặt
                                    vé ngay</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <TrailerFunction active={isTrailer.active} url={isTrailer.url} setActive={setIsTrailer}/>
        </>
    )
}
export default Description;