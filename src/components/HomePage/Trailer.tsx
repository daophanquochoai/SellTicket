import React from "react";
import {CloseCircleOutlined} from "@ant-design/icons";

interface Trailer {
    active : boolean,
    url : string
}
interface Props {
    active : boolean,
    setActive : (arg:Trailer) => void,
    url : string
}

const TrailerFunction : React.FC<Props> = ({active, setActive, url}) => {
    return (
        <>
            {
                url &&
                <div
                    className={`bg-black/60 w-full h-dvh fixed top-0 z-20 ${active ? 'flex' : 'hidden'} items-center justify-center`}>
                    <div className={'p-[25px] bg-black relative'}>
                        <div className={'absolute top-[-22px] text-white right-[-15px] text-[30px] cursor-pointer'}
                             onClick={() => setActive({active: false, url: ''})}><CloseCircleOutlined/></div>
                        <iframe width="560" height="315" src={url}
                                title="YouTube video player" frameBorder="0"
                                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                                referrerPolicy="strict-origin-when-cross-origin" allowFullScreen/>
                    </div>
                </div>
            }
        </>
    )
}
export default TrailerFunction;
