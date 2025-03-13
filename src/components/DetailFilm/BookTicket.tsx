import React, {useEffect, useState} from "react";
import {useLocation, useParams} from "react-router-dom";

interface Time {
    date : string,
    dateString : string
}
interface Props {
    setStep : ( arg : number ) => void,
    setTime : (arg : string) => void
}

const BookTicket : React.FC<Props> = (props) => {

    const [time, setTime] = useState<Time[]>([]);
    const [selected, setSelected] = useState<number>(-1);


    // bat param render
    const getQueryParams = () => {
        return new URLSearchParams(window.location.search);
    };
    const calculateDaysDifference = (dateString) => {
        const inputDate = new Date(dateString);
        const today = new Date();

        // Đặt về đầu ngày để so sánh chính xác (bỏ phần giờ, phút, giây)
        inputDate.setHours(0, 0, 0, 0);
        today.setHours(0, 0, 0, 0);

        const diffTime = inputDate - today;
        const diffDays = Math.round(diffTime / (1000 * 60 * 60 * 24));

        return diffDays;
    };

    useEffect(() => {
        const query = getQueryParams();
        const time = query.get("time");
        if( time != null ){
            setSelected(calculateDaysDifference(time));
            props.setTime(time);
            props.setStep(2);
        }
    }, []);

    useEffect(() => {
        const today = new Date();
        const list:Time[] = [];

        for (let i = 0; i < 4; i++) {
            let nextDay = new Date();
            nextDay.setDate(today.getDate() + i);
            let dayOfWeek = nextDay.toLocaleDateString('vi-VN', { weekday: 'long' });
            let dayMonth = nextDay.toLocaleDateString('vi-VN', { day: 'numeric', month: 'numeric', year :'numeric' });
            list[i] = {
                date : dayMonth,
                dateString : dayOfWeek
            }
        }
        setTime(list)
    }, []);

    const handleClick = (index : number, date : string) => {
        props.setStep(2);
        setSelected(index);
        const [day, month, year] = date.split("/");
        const formattedDate = `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
        props.setTime(formattedDate);
    }

    return(
        <>
            <div className={'flex justify-center items-center mt-[60px]'}>
                <div className={'container'}>
                    <div className={'flex items-center justify-center'}>
                        <p className={'text-white uppercase text-3xl font-bold'}>Lịch Chiếu</p>
                    </div>
                    <div className={'flex justify-center items-center mt-[20px] gap-4'}>
                        {
                            time.map( (item, index) => {
                                return (
                                    <div key={index}
                                         onClick={() => handleClick(index, item.date)}
                                        className={`${selected === index ? 'bg-main' : ''} w-[100px] text-white px-2 py-2 rounded-[10px] flex justify-center items-center flex-col font-bold cursor-pointer border-2 border-main hover:bg-white hover:text-main`}>
                                        <p>{item.date}</p>
                                        <p>{item.dateString}</p>
                                    </div>
                                )
                            })
                        }
                    </div>
                </div>
            </div>
        </>
    )
}

export default BookTicket;