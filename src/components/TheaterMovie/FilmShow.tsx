import React, {useEffect, useState} from "react";
import {Spin} from "antd";
import {fetchFilmShow} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

interface Props {
    filmId : string,
    branchId : string,
    time : string,
    subId : string,
    nameBranch : string
}
interface FilmShow {
    id : number,
    timeEnd : string,
    timeStart : string,
    subFilmId : string,
    roomId : string,
    timestamp : string,
}

const FilmShow : React.FC<Props> = ( props ) => {

    const [loading, setLoading] = useState<boolean>(false);
    const [filmShow, setFilmShow] = useState<FilmShow[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        handleFetchFilmShow();
    }, [props.branchId, props.time, props.filmId]);

    const handleFetchFilmShow = async () => {
        setLoading(true);
        const response = await  fetchFilmShow(props.branchId, props.subId, props.filmId, props.time);
        setLoading(false);
        console.log(response);
        if( response.status !== 200 ){
            return;
        }
        setFilmShow(response.data.data);
    }

    const handleToMovie = () => {
        navigate(`/film/${props.filmId}?time=${props.time}&film=${props.filmId}&branchId=${props.branchId}&namebranch=${props.nameBranch}&subId=${props.subId}`)
    }

    return (
        <>
            <Spin spinning={loading} tip={"Đang tải..."} size={"default"}>
                <div className={'flex flex-wrap gap-2'}>
                    {
                        filmShow.map( fs => {
                            return (
                                <div
                                    onClick={() => handleToMovie()}
                                    key={fs.id}
                                    className={'border-2 border-border min-w-[100px] px-2 py-1 text-textCol cursor-pointer hover:bg-main transition-all duration-300'}>
                                    <p>{fs.timeStart.slice(0, 5)} - {fs.timeEnd.slice(0, 5)}</p>
                                </div>
                            )
                        })
                    }
                </div>
            </Spin>
        </>
    )
}
export default FilmShow;