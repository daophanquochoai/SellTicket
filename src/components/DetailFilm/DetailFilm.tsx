import React, {lazy, useEffect, useRef, useState} from "react";
import {fetchFilmById, fetchFilmShow,} from "../../Helper/Helper.ts";
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import {useCommonContext} from "../../context/CommonContext.tsx";

const RatePage = lazy(() => import('./RatePage.tsx'));
const Description = lazy(() => import('./Description.tsx'));
const BookTicket = lazy(() => import('./BookTicket.tsx'))
const MovieTheater = lazy(() => import('./MovieTheater.tsx'));
const ChooseChair = lazy(() => import('./ChooseChair.tsx'));
const ChooseDrink = lazy(() => import('./ChooseDrink.tsx'));
const LoadingPage = lazy(() => import('../LoadingPage/LoadingPage.tsx'));
const Payment = lazy(() => import('./Payment.tsx'));

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
    name : string
}
interface TypeFilm {
    id : string,
    name : string,
    active : string
}
interface SelectMovie {
    branchId : string,
    nameBranch : string,
    subId : string
}
interface FilmShow {
    id : number,
    timeEnd : string,
    timeStart : string,
    subFilmId : string,
    roomId : string,
    timestamp : string,
}
interface Time {
    minute : string,
    second : string
}
interface Room{
    id : string,
    name : string,
    positionChair : number[][],
    branch: {
        id : string,
        nameBranch : string,
        address : string,
    },
}
interface CountTicket {
    id : string,
    price: number,
    name : string,
    slot : number,
    count : number,
    chairCode : number[][]
}
interface Count {
    id : string,
    price : number,
    count : number
}
interface BillChairDto{
    id : string,
    chairCode : string,
    price : string,
    ticket : TicketDto,
}
interface TicketDto {
    id : string,
    conditionUse : string,
    name : string,
    price : string,
    typeTicket : string
}
interface  BillDishDto {
    id : string,
    price : number,
    amount : number,
    dishDto : DishDto
}
interface DishDto{
    id : string,
    price : number,
    name : string,
    image : string,
    typeDish : TypeDishDto
}
interface TypeDishDto{
    id : string,
    name : string
}
interface ChairPayment {
    chairCode : string,
    conditionUse : string,
    name : string,
    price : number,
    typeTicket : string
}
const initFilm : Film = {
    id: "",
    name : "",
    age : 0,
    image : "",
    sub : [],
    nation : "",
    duration : "",
    description : "",
    content : "",
    trailer : "",
    typeFilms : [],
    status : ""
}
const initSelectMovie : SelectMovie = {
    branchId : "",
    nameBranch : "",
    subId : ""
}
const initTime = {
    minute : "05",
    second : "00"
}
const initRoom = {
    id : "",
    name : "",
    positionChair : [[]],
    branch: {
        id : "",
        nameBranch : "",
        address : "",
    },
}


const DetailFilm : React.FC = () => {

    const param = useParams();
    const [film, setFilm] = useState<Film>(initFilm);
    const [loading, setLoading] = useState<boolean>(false);
    const [step, setStep] = useState<number>(0);
    const [filmShow, setFilmShow] = useState<FilmShow[]>([]);
    const navigate = useNavigate();

    const [selectMovie, setSelectMovie] = useState<SelectMovie>(initSelectMovie);
    const [time, setTime] = useState<string>("");
    const [filmShowId, setFilmShowId] = useState<number>(0);
    const [totalDrink, setTotalDrink] = useState<number>(0);
    const [totalTicket, setTotalTicket] = useState<number>(0);
    const [oclock, setOClock] = useState<Time>(initTime);
    const [runOClock, setRunOClock] = useState<boolean>(false);
    const intervalRef = useRef<NodeJS.Timeout | null>(null);

    //payment
    const [roomCommon, setRoomCommon] = useState<Room>(initRoom);
    const [counter, setCounter] = useState<Count[]>([]);
    const [ticketSlot, setTicketSlot] = useState<CountTicket[]>([]);
    //usecontext
    const {bill, setBill} = useCommonContext();
    const [loadingAgain, setLoadingAgain] = useState<boolean>(false);
    //ref
    const bookTicketRef = useRef<NodeJS.Timeout | null>(null);


    useEffect(() => {
        const id : string[] = [];
        ticketSlot.forEach( item => {
            id.push(item.id);
        })
        const eventSource = new EventSource(`http://localhost:8080/sse/subscribe`)
        eventSource.addEventListener("ticketEmail", (event) => {
            const data : ChairPayment[] = JSON.parse(event.data);
            const state = { checked : false };
            data.forEach(item => {
                handleChairPayment(item, state);
            })
            if( state.checked ){
                toast.warning(<p className={'w-full'}>Chỗ đã được đặt</p>)
            }
        });
        eventSource.onerror = (event) => {
            console.error("Lỗi nhận SSE ", event);
            eventSource.close()
        }
        return () => {
            console.log("Đóng kết nối SSE");
            eventSource.close();
        };
    }, [ticketSlot]);

    useEffect(()=>{
        handleFilmById();
        setStep(0);
    },[])

    useEffect(() => {
        if( runOClock ) {
            handleOClock();
        }
        return () => {
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
            }
        };
    }, [runOClock]);

    useEffect(() => {
        if( time !== "" && selectMovie.branchId !== "" && selectMovie.subId !== ""){
            handleFetchFilmShow();
        }
    }, [selectMovie, time]);

    const handleFetchFilmShow = async () => {
        const response = await  fetchFilmShow(selectMovie.branchId, selectMovie.subId, film.id, time);
        if( response.status !== 200 ){
            toast.error(<p className={'w-full'}>Không thể tải thời gian chiếu</p>)
            return;
        }
        setFilmShow(response.data.data);
    }

    const handleChairPayment = ( chair : ChairPayment, state ) => {
         const  temp : CountTicket[] = ticketSlot.map( item => {
            let checked : boolean = false;
            item.chairCode.forEach( c => {
                if( c[0] == parseInt(chair.chairCode[1]) && c[1] == parseInt(chair.chairCode[3]) ){
                    checked = true;
                    state.checked = true;
                }
            })
            if( checked ){
                setLoadingAgain(!loadingAgain);
                return {...item, chairCode : []}
            }else
                return item;
        })
        setTicketSlot(temp);
    }

    const handleFilmById = async () => {
        if( param.id === undefined ) {
            toast.warning(<p className={'w-full'}>Không thể tìm thấy film</p>);
            return;
        }
        setLoading(true);
        const response = await fetchFilmById(param.id);
        setLoading(false);
        if( response.status !== 200 ){
            toast.error(<p className={'w-full'}>{response.response.data.message}</p>)
            return;
        }
        setFilm(response.data.data)
    }

    const handleOClock = () => {
        intervalRef.current = setInterval(() => {
            setOClock((prev) => {
                const min = parseInt(prev.minute, 10);
                const sec = parseInt(prev.second, 10);
                if(  min == 0 && sec == 0 ){
                    console.log("return")
                    toast.warning(<p>Đã hết thời gian giữ chỗ</p>);
                    clearInterval(intervalRef.current!);
                    setStep(0);
                    setTime("");
                    setRunOClock(false);
                    return { minute: '05', second: '00'};
                }
                if (sec === 0) {
                    return { minute: (min - 1).toString().padStart(2, "0"), second: "59" };
                } else {
                    return { minute: prev.minute, second: (sec - 1).toString().padStart(2, "0") };
                }
            });
        }, 1000);

    }

    const handlePayment = () => {
        const checked : CountTicket[] = ticketSlot.filter(item => item.chairCode.length !== item.count && item.count !== 0);
        console.log(checked)
        if( checked.length > 0){
            toast.warning("Vui lòng chọn đủ ghế đã đặt");
            return;
        }
        const chairs : BillChairDto[] = [];
        const temp : CountTicket[] = ticketSlot.filter(item => item.count != 0);
        temp.forEach( ticket => {
            ticket.chairCode.forEach( chair => {
                const ticketDto : TicketDto = {
                    id : ticket.id,
                    name : ticket.name
                }
                const value : BillChairDto = {
                    chairCode : '[' + chair[0] + ',' + chair[1] + ']',
                    ticket : ticketDto,
                    price : ticket.price.toString()
                }
                chairs.push(value);
            })
        })
        const dishes : BillDishDtop[] = [];
        const counterTemp = counter.filter(item => item.count != 0);
        counterTemp.forEach( count => {
            const dishDto : DishDto = {
                id : count.id
            }
            const value : BillDishDto = {
                amount : count.count,
                price : count.price,
                dishDto : dishDto
            }
            dishes.push(value)
        })
        try{
            let temp = filmShow.find(item => item.id == filmShowId);
            if( temp == undefined ){
                temp = null;
            }
            setBill(
                {
                    ...bill,
                    totalPrice : totalDrink + totalTicket,
                    filmShowTimeId : filmShowId,
                    roomId : roomCommon.id,
                    filmId : film.id,
                    chairs : chairs,
                    dishes : dishes,
                    nameFilm: film.name,
                    nameBranch : roomCommon.branch.nameBranch,
                    address : roomCommon.branch.address,
                    nameRoom : roomCommon.name,
                    timeStampSee : temp == null ? "" : temp.timestamp,
                    timeStart : temp == null ? "" : temp.timeStart,
                    timeEnd : temp == null ? "" : temp.timeEnd
                }
            )

            navigate("/payment");
            scroll(0,0);
        }catch (e){
            console.error(e);
        }
    }


    return (
        <>
            {
                loading ?
                    <LoadingPage />
                    :
                    <>
                        <Description film={film} bookRef={bookTicketRef}/>
                        <RatePage />
                        <BookTicket setStep={setStep} setTime={setTime} bookRef={bookTicketRef}/>
                        {
                            step >= 2 &&
                            <MovieTheater
                                subs={film.sub}
                                setStep={setStep}
                                setSelect={setSelectMovie}
                                select={selectMovie}
                                filmShow={filmShow}
                                filmShowId={filmShowId}
                                setFilmShowId={setFilmShowId}
                            />
                        }
                        {
                            step >= 3 &&
                            <>
                                <ChooseChair roomId={filmShowId === 0 ? 0 : filmShow.find(item=>item.id === filmShowId).roomId}
                                             filmShowId={filmShowId}
                                             setSum={setTotalTicket}
                                             run={runOClock}
                                             loadingAgain={loadingAgain}
                                             setRun={setRunOClock}
                                             setRoomCommon={setRoomCommon}
                                             ticketSlot={ticketSlot}
                                             setTicketSlot={setTicketSlot}
                                />
                                <ChooseDrink setSum={setTotalDrink} counter={counter} setCounter={setCounter}/>
                                <Payment time={oclock} price={totalDrink + totalTicket} filmName={film.name} nameBranch={selectMovie.nameBranch} handlePayment={handlePayment}/>
                            </>
                        }
                    </>
            }
        </>
    )
}
export default DetailFilm;