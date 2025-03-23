import React, {useEffect, useState} from "react";
import {FaMinus, FaPlus} from "react-icons/fa";
import {fetchRoomBooked, fetchRoomById, fetchTicket, fetchTicketByActive} from "../../Helper/Helper.ts";
import {Spin} from "antd";
import {toast} from "react-toastify";

interface Ticket {
    id : string,
    conditionUse : string,
    name : string,
    price : number,
    typeTicket : string,
    slot : number
}

interface CountTicket {
    id : string,
    price: number,
    name : string,
    slot : number,
    count : number,
    chairCode : number[][]
}
interface Props {
    roomId : string,
    filmShowId : number,
    setSum : (arg : number) => void,
    run : boolean,
    setRun : (arg:boolean) => void,
    setRoomCommon : (arg : Room) => void
    ticketSlot : CountTicket[],
    setTicketSlot : (arg : CountTicket[]) => void,
    loadingAgain : boolean
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
interface RoomBooked {
    chairCode : number[]
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

const ChooseChair : React.FC<Props> = ( props ) => {

    const [loadingTicket, setLoadingTicket] = useState<boolean>( false);
    const [loadingRoom, setLoadingRoom] = useState<boolean>(false);
    const [ticket, setTicket] = useState<Ticket[]>([]);
    const {ticketSlot, setTicketSlot} = props;
    const [room, setRoom] = useState<Room>(initRoom);
    const [chairBooked, setChairBooked] = useState<RoomBooked[]>([]);

    //fetch du lieu ve
    useEffect(() => {
        handleFetchTicket();
        handleFetchRoomById();
    }, [props.filmShowId, props.loadingAgain]);

    // scroll khi moi hien
    useEffect(() => {
        const ticketElement = document.getElementById("ticket");
        if (ticketElement) {
            ticketElement.scrollIntoView({ behavior: "smooth" });
        }
    }, []);

    useEffect(() => {
        let temp = 0;
        ticketSlot.forEach(item => {
            temp += item.count * item.price;
        })
        props.setSum(temp);
    }, [ticketSlot]);

    // tao danh sach dem ve
    useEffect(() => {
        const listTicket: CountTicket[] = ticket.map((item) => ({
            id: item.id,
            slot : item.slot,
            name : item.name,
            price : item.price,
            count: 0,
            chairCode : []
        }));
        setTicketSlot(listTicket);
    }, [ticket]);

    //fetch du lieu ve
    const handleFetchTicket = async () => {
        setLoadingTicket(true);
        const response = await fetchTicketByActive();
        setLoadingTicket(false);
        if( response.status !== 200  ){
            toast.error(<p>Không thể tài vé phim</p>);
            return;
        }

        setTicket(response.data.data);
    }

    //fetch vi tri ghe
    const handleFetchRoomById = async () => {
        setLoadingRoom(true);
        const response = await fetchRoomById(props.roomId);
        const responseBooked = await  fetchRoomBooked(props.filmShowId);
        setLoadingRoom(false);
        if( response.status !== 200 || responseBooked.status !== 200){
            toast.error(<p>Không thể tài phòng chiếu phim</p>);
            return;
        }
        setChairBooked(responseBooked.data.data);
        setRoom(response.data.data);
        props.setRoomCommon(response.data.data);
    }

    //tang so luong ve
    const handleRise = ( id : string ) => {
        const item = ticketSlot.filter(item => item.id === id)[0];
        if( item !== undefined ){
            setTicketSlot([...ticketSlot.filter(item => item.id !== id), { id : item.id,slot : item.slot, name : item.name, price : item.price, count : item.count+1, chairCode : item.chairCode}])
        }else{
            toast.warning(<p className={'w-full'}>Hệ thống lỗi</p>)
        }
    }
    //giam so luong ve
    const handleDown = ( id : string) => {
        const item = ticketSlot.filter(item => item.id === id)[0];
        if( item !== undefined && item.count > 0 && item.count > item.chairCode.length){
            setTicketSlot([...ticketSlot.filter(item => item.id !== id), { id : item.id,slot : item.slot, price : item.price,name : item.name, count : item.count-1, chairCode : item.chairCode}])
        }
    }

    //xu ly chon ghe
    const handleChooseChair :
        (slot : number, rowIndex : number, colIndex : number) => void = (slot, rowIndex, colIndex) => {
        const booked = ticketSlot.find(item => item.slot == slot && ( item.chairCode.find( chair => chair[0] == rowIndex && chair[1] == colIndex)));
        if( booked != undefined ){
            booked.chairCode = booked.chairCode.filter(item => item[0] != rowIndex || colIndex != colIndex);
            setTicketSlot([...ticketSlot.filter(item=> item.id !== booked.id), {id : booked.id,name : booked.name, slot : booked.slot,price : booked.price, count : booked.count, chairCode : booked.chairCode}]);
            return;
        }
        const temp = ticketSlot.find(item=> item.slot == slot && item.count > item.chairCode.length);
        if( temp !== undefined){
            temp.chairCode.push([rowIndex,colIndex])
            setTicketSlot([...ticketSlot.filter(item=> item.id != temp.id), {id : temp.id,name : temp.name, slot : temp.slot,price : temp.price, count : temp.count, chairCode : temp.chairCode}]);
        }
        if( !props.run ){
            props.setRun(true);
        }

    }

    return (
        <>
            <div className={"flex justify-center items-center mt-[80px]"} id={"ticket"}>
                <div className={'container'}>
                    <div>
                        <div>
                            <div className={'flex justify-center items-center'}>
                                <p className={'text-3xl text-white font-bold uppercase'}>Chọn loại vé</p>
                            </div>
                            <Spin tip={"Đang tải..."} spinning={loadingTicket} size={"default"}>
                                <div className={'grid grid-cols-3 gap-4 mt-[40px]'}>
                                    {
                                        ticket && ticket.map( (item, index) => {
                                            const countFind = ticketSlot.find(i=>i.id === item.id);
                                            const valueCount = countFind === undefined ? 0 : countFind.count;
                                            return (
                                                <div
                                                    key={index}
                                                    className={'col-span-1 border-border border-2 p-4 flex flex-col gap-2'}>
                                                    <p className={'text-white'}>{item.name}</p>
                                                    <p className={'text-main font-bold'}>{item.typeTicket}</p>
                                                    <p className={'text-white'}>{item.price.toLocaleString()}Đ</p>
                                                    <div>
                                                        <div
                                                            className={'inline-flex gap-4 items-center bg-gray-400 px-2 py-1'}>
                                                            <button
                                                                onClick={() => handleDown(item.id)}
                                                                className={'text-white text-xs cursor-pointer'}><FaMinus/></button>
                                                            <span className={'text-white'}>{valueCount}</span>
                                                            <button
                                                                onClick={()=> handleRise(item.id)}
                                                                className={'text-white text-xs cursor-pointer'}><FaPlus/></button>
                                                        </div>
                                                    </div>
                                                </div>
                                            )
                                        })
                                    }
                                </div>
                            </Spin>
                        </div>
                        <div className={'mt-[80px]'}>
                            <div className={'flex justify-center items-center'}>
                                <p className={'text-3xl font-bold uppercase text-white'}>Chọn ghế</p>
                            </div>
                            <div className={'mt-[40px] flex flex-col justify-center items-center '}>
                                <div>
                                    <img src={'/public/screen.png'} alt={'screen'}
                                         className={'w-[500px] h-auto text-black'}/>
                                </div>
                                <Spin tip={"Đang tải..."} spinning={loadingRoom} size={"default"}>
                                    <table border={10} cellPadding={10} className={'mt-[20px]'}>
                                        <tbody>
                                        {room.positionChair.map((row, rowIndex) => {
                                                let count = 0;
                                                return (
                                                    <tr key={rowIndex}>
                                                        {row.map((col, colIndex) => {
                                                            const bookedFind = chairBooked.find(item => item.chairCode[1] == rowIndex && item.chairCode[3] == colIndex);
                                                            const chairChoosed = ticketSlot.find(item => item.slot == col && ( item.chairCode.find( chair => chair[0] == rowIndex && chair[1] == colIndex)));
                                                            if (count > 0) {
                                                                count--;
                                                            }
                                                            if (col > 1) {
                                                                count = col;
                                                            }
                                                            if (col !== 0) {

                                                                if( bookedFind ){
                                                                    return <td key={colIndex} colSpan={col}
                                                                               className={'text-center'}>
                                                                        <button
                                                                            className={` px-2 py-1 rounded-tl-[10px] rounded-tr-[10px] w-full bg-red-400`}
                                                                        >
                                                                            {String.fromCharCode(65 + rowIndex) + colIndex}
                                                                        </button>
                                                                    </td>
                                                                }else{
                                                                    if( chairChoosed){
                                                                        return <td key={colIndex} colSpan={col}
                                                                                   className={'text-center'}>
                                                                            <button
                                                                                onClick={() => handleChooseChair(col,rowIndex, colIndex)}
                                                                                className={`px-2 py-1 rounded-tl-[10px] rounded-tr-[10px] w-full bg-main`}
                                                                            >
                                                                                {String.fromCharCode(65 + rowIndex) + colIndex}
                                                                            </button>
                                                                        </td>
                                                                    }else{
                                                                        return <td key={colIndex} colSpan={col}
                                                                                   className={'text-center'}>
                                                                            <button
                                                                                onClick={() => handleChooseChair(col,rowIndex, colIndex)}
                                                                                className={`px-2 py-1 rounded-tl-[10px] rounded-tr-[10px] w-full ${col === 1 ? 'bg-gray-200' : 'bg-yellow-900'}`}
                                                                            >
                                                                                {String.fromCharCode(65 + rowIndex) + colIndex}
                                                                            </button>
                                                                        </td>
                                                                    }
                                                                }
                                                            }
                                                            if (count === 0) {
                                                                return (
                                                                    <td key={colIndex} colSpan={1}></td>
                                                                )
                                                            }
                                                        })}
                                                    </tr>
                                                )
                                            }
                                        )}
                                        </tbody>
                                    </table>
                                </Spin>
                            </div>
                            <div className={'mt-[60px]'}>
                                <div className={"flex justify-evenly"}>
                                    <div className={'flex gap-2 items-center'}>
                                        <span className={'w-[30px] h-[30px] rounded-tr-[10px] rounded-tl-[10px] bg-gray-200'}></span>
                                        <p className={'text-white font-bold'}>Ghế Đơn</p>
                                    </div>
                                    <div className={'flex gap-2 items-center'}>
                                        <span className={'w-[60px] h-[30px] rounded-tr-[10px] rounded-tl-[10px] bg-yellow-900'}></span>
                                        <p className={'text-white font-bold'}>Ghế Đôi (2 người)</p>
                                    </div>
                                    <div className={'flex gap-2 items-center'}>
                                        <span className={'w-[30px] h-[30px] rounded-tr-[10px] rounded-tl-[10px] bg-main'}></span>
                                        <p className={'text-white font-bold'}>Ghế Chọn</p>
                                    </div>
                                    <div className={'flex gap-2 items-center'}>
                                        <span className={'w-[30px] h-[30px] rounded-tr-[10px] rounded-tl-[10px] bg-red-400'}></span>
                                        <p className={'text-white font-bold'}>Ghế đã đặt</p>
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
export default ChooseChair;