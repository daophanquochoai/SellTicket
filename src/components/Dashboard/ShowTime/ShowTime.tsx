import React, {useEffect, useRef, useState} from "react";
import { Calendar, Views, momentLocalizer } from "react-big-calendar";
import { DatePicker } from 'antd';
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import {
    addShowTime, deleteShowTime,
    expireToken,
    fetchFilmShowByRoomAndTime,
    fetchRoomAll,
    fetchSubFilmAll,
    fetchTheater,
    getRoomByBranchId, getToken
} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {Modal, Spin} from "antd";
import {FieldTimeOutlined} from "@ant-design/icons";
import { TimePicker } from 'antd';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
import {useNavigate} from "react-router-dom";

interface Room {
    id : string,
    name : string,
    positionChair : number[][],
    branch : Branch,
    status : string
}
interface Branch {
    id : string,
    nameBranch : string,
    address : string,
    status : string
}
interface FilmShow {
    id : number,
    timeEnd : string,
    timeStart : string,
    subName : string,
    filmName : string,
    roomId : string,
    timestamp : string,
    status : string
}
interface Event {
    id: number,
    title: string,
    start: Date,
    end: Date,
}
interface SubFilm {
    id: string,
    filmDto : Film,
    subDto : Sub
}
interface Film{
    id : string,
    name : string,
    age : number,
    image : string,
    sub : string,
    nation : string,
    duration : string,
    description : string,
    content : string,
    trailer : string,
}
interface Sub{
    id : string,
    name : string
}
interface DataPost {
    id : number,
    timeEnd : string,
    timeStart : string,
    timestamp : string,
}
const localizer = momentLocalizer(moment);

const initFilmShow : DataPost = {
    id : 0,
    timeEnd : '',
    timeStart : '',
    timestamp : '',
}

const ShowTime : React.FC = () => {

    const [branchs, setBranchs] = useState<Branch[]>([]);
    const [loadingBranch, setLoadingBranch] = useState<boolean>(false);
    const [selectBranch, setSelectBranch] = useState<string>('');

    //room
    const [rooms, settRooms] = useState<Room[]>([]);
    const [loadingRoom, setLoadingRoom] = useState<boolean>(false);
    const [selectRoom, setSelecteRoom] = useState<string>('');

    //filmshow
    const [filmShows, setFilmShows] = useState<FilmShow[]>([]);
    const [roomed, setRoomed] = useState<string>('');
    const [loadingShowtime, setLoadingShowTime] = useState<boolean>(false);
    const [event, setEvent] = useState<Event[]>([])

    //time line
    const [currentDate, setCurrentDate] = useState(new Date());
    const [currentView, setCurrentView] = useState(Views.DAY);

    // subfilm
    const [subFilms, setSubFilm] = useState<SubFilm[]>([]);
    const [loadingSubFilm, setLoadingSubFilm] = useState<boolean>(false);

    //room
    const [roomModal, setRoomModal] = useState<Room[]>([]);
    const [loadingRoomModal, setLoadingRoomModal] = useState<boolean>(false);

    //post
    const [dataFilmShow, setDataFilmShow] = useState<DataPost>(initFilmShow);
    const [loadingPost, setLoadingPost] = useState<boolean>(false);
    const [isOpenCreate, setIsOpenCreate] = useState<boolean>(false);
    const roomRef = useRef(null);
    const subFilmRef = useRef(null);
    const [roomReload, setRoomReload] = useState<string>("");

    //modal accept
    const [isOpenAccept, setIsOpenAccept] = useState<boolean>(false);
    const [loadingAccept, setLoadingAccept] = useState<boolean>(false);
    const [selected, setSelected] = useState<number>(0);

    const navigate = useNavigate();

    // ttime start -end
    dayjs.extend(customParseFormat);


    // display show time
    const handleSelectSlot = (slotInfo: { start: Date }) => {
        if (currentView === Views.MONTH) {
            setCurrentView(Views.DAY);
            setCurrentDate(slotInfo.start);
        }
    };

    useEffect(() => {
        handleFetchBranch();
    }, []);
    useEffect(() => {
        handleFetchSubFilm();
        handleFetchRoomModal();
    }, []);
    useEffect(() => {
        if( selectBranch == '' || selectBranch == null) return;
        handleFetchRoomByBranch();
    }, [selectBranch]);

    useEffect(() => {
        if( roomed == '' || roomed == null ) return;
        if( roomed != roomReload && roomReload != '') return;
        handleFetchShowTimeByRoomAndTime();
    }, [roomed, currentDate, roomReload]);
    useEffect(() => {
        setEvent(filmShows.map((film) => ({
            id: film.id,
            title: film.filmName + " " + film.subName,
            start: moment(`${film.timestamp} ${film.timeStart}`).toDate(),
            end: moment(`${film.timestamp} ${film.timeEnd}`).toDate(),
        })));
    }, [filmShows]);

    const handleFetchBranch = async () => {
        setLoadingBranch(true);
        const response = await fetchTheater();
        setLoadingBranch(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setBranchs(response.data.data);
        setSelectBranch(response.data.data[0].id);
    }

    const handleFetchRoomByBranch = async () => {
        setLoadingRoom(true);
        const response = await getRoomByBranchId(selectBranch);
        setLoadingRoom(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        settRooms(response.data.data);
        setSelecteRoom(response.data.data[0].id);
    }

    const handleFetchShowTimeByRoomAndTime = async () => {
        setLoadingShowTime(true);
        const response = await fetchFilmShowByRoomAndTime(roomed, moment(currentDate).format("YYYY-MM-DD"));
        setLoadingShowTime(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        console.log(response.data.data);
        setFilmShows(response.data.data);
    }

    const handleFetchSubFilm = async () => {
        setLoadingSubFilm(true);
        const response = await fetchSubFilmAll();
        setLoadingSubFilm(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setSubFilm(response.data.data);
    }

    const handleFetchRoomModal = async () => {
        setLoadingRoomModal(true);
        const response = await fetchRoomAll();
        setLoadingRoomModal(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setRoomModal(response.data.data);
    }

    const handleChangeTime = (time, timeString) => {
        setDataFilmShow({
            ...dataFilmShow,
            timeStart : timeString[0],
            timeEnd : timeString[1]
        })
    }
    const handleChangeDate = (day,dayString) => {
        setDataFilmShow({
            ...dataFilmShow,
            timestamp : dayString
        })
    }
    const handlePostFilmShow = async () => {
        const roomId : string = roomRef.current == null ? '' : roomRef.current.value.toString();
        const subFilmId : string = subFilmRef.current == null ? '' : subFilmRef.current.value.toString();
        if( dataFilmShow.timestamp == '' || dataFilmShow.timeStart == '' || dataFilmShow.timeEnd == '' || subFilmId == '' || roomId == ''){
            toast.warning(<p className={'w-full'}>Vui lòng điền đầy đủ các trường</p>)
            return;
        }

        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingPost(true);
        const response = await addShowTime(dataFilmShow.timeStart, dataFilmShow.timeEnd, dataFilmShow.timestamp, subFilmId, roomId, token);
        setLoadingPost(false);
        // console.log(response)
        if( response.status != 201){
            toast.warning(<p className={'w-full'}>Không thể tạo dữ liệu</p>)
            return;
        }
        toast.success(<p>Tạo thành công</p>)
        setIsOpenCreate(false);
        setRoomReload(roomId);
    }
    const handleCancel = () => {
        setIsOpenCreate(false);
    }
    const handleCancelAccept = () => {
        setIsOpenAccept(false);
    }
    const handleDelete = async (filmShowId) => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingAccept(true);
        const response = await deleteShowTime(filmShowId, token);
        setLoadingAccept(false);
        if( response.status != 200){
            toast.warning(<p className={'w-full'}>Xuất chiếu đã được đặt</p>)
            return;
        }
        toast.success(<p>Xóa thành công</p>)
        setFilmShows([
            ...filmShows.filter(item=>item.id!=filmShowId)
        ])
        setIsOpenAccept(false);
    }

    return (
        <div className={'bg-white p-[20px] rounded-xl'}>
            <h2 className={'text-2xl font-bold uppercase text-center mb-[20px]'}>Lịch chiếu phim</h2>
            <div className={'flex justify-between items-end gap-4 mb-[20px]'}>
                <div className={'flex gap-2 flex-col flex-1'}>
                    <label className={'text-main uppercase'}>Danh sách rạp :</label>
                    <Spin tip={'Đang tải...'} spinning={loadingBranch}>
                        {
                            branchs.length > 0 ?
                                <select className={'outline-0 px-4 py-2 bg-textAdmin text-white uppercase'}
                                        value={selectBranch} onChange={e => setSelectBranch(e.target.value)}>
                                    {
                                        branchs.map(branch => {
                                            return <option value={branch.id} key={branch.id}>
                                                {branch.nameBranch}
                                            </option>
                                        })
                                    }
                                </select>
                                :
                                <select className={'outline-0 px-4 py-2 bg-textAdmin text-white uppercase'}
                                        disabled={true}>
                                    <option>
                                        Không có rạp
                                    </option>
                                </select>
                        }
                    </Spin>
                </div>
                <div className={'flex gap-2 flex-col flex-1'}>
                    <label className={'text-main uppercase'}>Danh sách rạp :</label>
                    <Spin tip={'Đang tải...'} spinning={loadingBranch}>
                        {
                            rooms.length > 0 ?
                                <Spin tip={'Đang tải...'} spinning={loadingRoom}>
                                    <select className={'outline-0 px-4 py-2 bg-textAdmin text-white uppercase'}
                                            value={selectRoom} onChange={e => setSelecteRoom(e.target.value)}>
                                        {
                                            rooms.map(room => {
                                                return <option value={room.id} key={room.id}>
                                                    {room.name}
                                                </option>
                                            })
                                        }
                                    </select>
                                </Spin>
                                :
                                <select className={'outline-0 px-4 py-2 bg-textAdmin text-white uppercase'}
                                        disabled={true}>
                                    <option>
                                        Không có phòng
                                    </option>
                                </select>
                        }
                    </Spin>
                </div>
                <div className={'w-[150px]'}>
                    <button
                        onClick={() => setRoomed(selectRoom)}
                        className={'bg-main px-4 py-2 text-white'}>Tìm kiếm</button>
                </div>
            </div>
            <div className={'flex justify-end my-[20px]'}>
                <button className={'px-4 py-2 bg-main text-white rounded-xl'} onClick={()=>setIsOpenCreate(true)}>Tạo Lịch Chiếu</button>
            </div>
            <Modal
                open={isOpenAccept}
                loading={loadingAccept}
                onCancel={() => handleCancelAccept()}
                closeIcon={[]}
                footer={[]}
            >
                <div>
                    <div><p className={'text-main text-xl font-bold text-center'}>Bạn có chắc chắn muốn xóa ?</p></div>
                    <div className={'justify-center gap-4 flex items-center mt-[20px]'}>
                        <button
                            onClick={() => handleCancelAccept()}
                            className={'px-4 py-2 border-textAdmin border-2 w-[100px] text-textAdmin'}>Hủy
                        </button>
                        <button
                            onClick={() => handleDelete(selected)}
                            className={'px-4 py-2 border-2 border-red-500 bg-red-500 text-white w-[100px]'}>Xác nhận
                        </button>
                    </div>
                </div>
            </Modal>
            <Modal
                open={isOpenCreate}
                loading={loadingPost}
                onCancel={()=>handleCancel()}
                footer={[
                    <button onClick={()=>handlePostFilmShow()} key={"update"} className={'bg-main px-4 py-2 text-white w-[100px]'}>Tạo</button>
                ]}
                width={600}
                title={<p className={'text-main font-bold uppercase text-xl'}>Tạo Thời Gian Chiếu</p>}
            >
                <div className={'flex flex-col gap-4'}>
                    <div className={'flex items-start gap-4'}>
                        <DatePicker size={'large'} onChange={(day,dayString)=>handleChangeDate(day,dayString)}/>
                        <TimePicker.RangePicker prefix={<FieldTimeOutlined />} size={'large'} onChange={(time, timeString)=>handleChangeTime(time, timeString)}/>
                    </div>
                    <div className={'flex flex-col gap-4'}>
                        <div className={'flex gap-2'}>
                            <div>
                                <p className={'px-2 py-1 text-main font-bold w-[100px]'}>Phòng :</p>
                            </div>
                            <Spin tip={'Đang tải...'} spinning={loadingRoomModal}>
                                {
                                    roomModal.length > 0 ?
                                        <select
                                            ref={roomRef}
                                            className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}>
                                            {
                                                roomModal.map(room => {
                                                    return <option value={room.id} key={room.id}>{room.name}</option>
                                                })
                                            }
                                        </select>
                                        :
                                        <select disabled={true}>
                                            <option>Không có phòng khả dụng</option>
                                        </select>
                                }
                            </Spin>
                        </div>
                        <div className={'flex gap-2'}>
                            <div>
                                <p className={'px-2 py-1 text-main font-bold w-[100px]'}>Xuất chiếu :</p>
                            </div>
                            <Spin tip={'Đang tải...'} spinning={loadingSubFilm}>
                                {
                                    subFilms.length > 0 ?
                                        <select
                                            ref={subFilmRef}
                                            className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}>
                                            {
                                                subFilms.map(sf => {
                                                    return <option value={sf.id} key={sf.id}>{sf.filmDto.name} - {sf.subDto.name}</option>
                                                })
                                            }
                                        </select>
                                        :
                                        <select disabled={true}>
                                            <option>Không có xuất khả dụng</option>
                                        </select>
                                }
                            </Spin>
                        </div>
                    </div>
                </div>
            </Modal>
            {
                roomed != '' &&
                <Spin tip={'Đang tải...'} spinning={loadingShowtime}>
                    <Calendar
                        localizer={localizer}
                        events={event}
                        startAccessor="start"
                        endAccessor="end"
                        views={["month", "day"]}
                        date={currentDate}
                        view={currentView}
                        onView={(view) => setCurrentView(view)}
                        onNavigate={(date) => setCurrentDate(date)}
                        onSelectSlot={handleSelectSlot} // Xử lý khi chọn ngày
                        selectable // Cho phép chọn ngày
                        style={{height: 500}}
                        eventPropGetter={() => {
                            // Hàm tạo màu ngẫu nhiên
                            const getRandomColor = () => {
                                const letters = "0123456789ABCDEF";
                                let color = "#";
                                for (let i = 0; i < 6; i++) {
                                    color += letters[Math.floor(Math.random() * 16)];
                                }
                                return color;
                            };

                            return {
                                style: {
                                    backgroundColor: getRandomColor(),
                                    color: "white",
                                    borderRadius: "5px",
                                    padding: "5px"
                                }
                            };
                        }}
                        onSelectEvent={(event) => {
                            setSelected(event.id);
                            setIsOpenAccept(true);
                        }}
                        components={{
                            event: ({ event }) => (
                                <div style={{ display: "flex", flexDirection: "column", textAlign: "center" }}>
                                    <span style={{ fontWeight: "bold" }}>{event.title}</span>
                                </div>
                            )
                        }}
                    />
                </Spin>
            }
        </div>
    );
};

export default ShowTime;
