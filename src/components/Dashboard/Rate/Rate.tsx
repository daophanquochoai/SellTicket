import React, {useEffect, useState} from "react";
import {expireToken, getRateCustom, getToken} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {FaSearch, FaStar} from "react-icons/fa";
import {Pagination, Table} from "antd";
import ModalRate from "./ModalRate.tsx";

interface Rate {
    id : string,
    star : number,
    content : string,
    timeStamp : string,
    customer : Customer,
    film : Film,
    active : string
}
interface Customer {
    id : string,
    name : string,
    phoneNumber : string,
    email : string,
}
interface Film{
    id : 1,
    name : string,
    age : number,
    image : string,
    nation : string,
    duration : string,
    sub : Sub[],
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

interface Custom {
    page : number,
    limit : number,
    asc : string,
    status : string,
    orderBy : string,
    q : string
}
interface Page {
    pageCurrent : number,
    pageTotal : number
}

const initParam : Custom= {
    page : 0,
    limit : 10,
    asc : 'asc',
    status : 'none',
    orderBy : 'timeStamp',
    q : ''
}
const initPage : Page= {
    pageCurrent : 1,
    pageTotal : 1
}
const columns = [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id'
    },
    {
        title: 'Đánh giá',
        dataIndex: 'star',
        key: 'star',
        render : (text)=>(
            <div className={'flex items-center gap-2'}>{text}<div className={'text-main'}><FaStar /></div></div>
        )
    },
    {
        title: 'Nội dung',
        dataIndex: 'content',
        key: 'content',
        render : (text)=>(
            <p>{text.length > 200 ? text.slice(0,100) + '...' : text}</p>
        )
    },
    {
        title: 'Thời gian',
        dataIndex: 'timeStamp',
        key: 'timeStamp'
    },
    {
        title: 'Người đánh giá',
        dataIndex: 'customer',
        key: 'customer',
        render : (text) => (
            <p>{text.name}</p>
        )
    },
    {
        title: 'Phim',
        dataIndex: 'film',
        key: 'film',
        render : (text)=> (
            <p>{text.name}</p>
        )
    },
    {
        title: 'Trạng thái',
        dataIndex: 'active',
        key: 'active',
        render : (text)=>(
            text == 'ACTIVE' ?
                <p className={'text-green-600 font-bold'}>{text}</p>
                :
                <p className={'text-red-700 font-bold'}>{text}</p>
        )
    },
]
const initCustomer : Customer= {
    id : '',
    name : '',
    phoneNumber : '',
    email : '',
}
const initFilm : Film= {
    id : 1,
    name : '',
    age : 0,
    image : '',
    nation : '',
    duration : '',
    sub : [],
    description : '',
    content : '',
    trailer :'',
    typeFilms : [],
    status : ''
}
const initRate : Rate = {
    id : '',
    star : 0,
    content : '',
    timeStamp : '',
    customer : initCustomer,
    film : initFilm,
    active : ''
}
const Rate : React.FC = () => {

    const [loading,setLoading] = useState<boolean>(false);
    const [param, setParam] = useState<Custom>(initParam);
    const [page, setPage] = useState<Page>(initPage);
    const [rates,setRates] = useState<Rate[]>([]);
    const navigate = useNavigate();
    const [search, setSearch] = useState<string>('');

    //modal
    const [rateSelected, setRateSelected ] = useState<Rate>(initRate);
    const [isOpen, setIsOpen] = useState<boolean>(false);

    useEffect(() => {
        handleGetRate()
    }, [param,page]);

    const handleGetRate = async () => {
        const token: string = getToken();
        if (expireToken(token)) {
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await getRateCustom(page.pageCurrent-1,  param.limit, param.asc, param.status, param.orderBy, param.q, token);
        setLoading(false);
        console.log(response)
        if (response.status != 200) {
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setRates(response.data.data.data);
        if (page.pageCurrent != response.data.data.pageCurrent || page.pageTotal != response.data.data.totalPage) {
            setPage({
                pageCurrent: response.data.data.pageCurrent,
                pageTotal: response.data.data.totalPage
            })
        }
    }

    return (
        <>
            <div>
                <div className={'mb-[10px]'}>
                    <div className={'flex gap-2 justify-end'}>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Trạng thái hóa đơn :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'none'}
                                    onChange={(e) => setParam({...param, status: e.target.value})}
                            >
                                <option value={'none'}>Tất cả</option>
                                <option value={'ACTIVE'}>Chưa xóa</option>
                                <option value={'DELETE'}>Đã xóa</option>
                            </select>
                        </div>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Sắp xếp theo :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'name'}
                                    onChange={(e) => setParam({...param, orderBy: e.target.value})}
                            >
                                <option value={'timeStamp'}>Thời gian</option>
                                <option value={'star'}>Số sao</option>
                            </select>
                        </div>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Thứ tự xếp :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'asc'}
                                    onChange={(e) => setParam({...param, asc: e.target.value})}
                            >
                                <option value={'asc'}>Tăng dần</option>
                                <option value={'des'}>Giảm dần</option>
                            </select>
                        </div>
                        <div className={'bg-white px-2 py-1 border-textAdmin border-[1px]'}>
                            <input className={'px-2 outline-0'} placeholder={'Nhập tìm kiếm...'}
                                   value={search}
                                   onChange={(e) => setSearch(e.target.value)}
                            />
                            <button className={'text-xl'}
                                    onClick={() => setParam({...param, q: search})}
                            ><FaSearch/></button>
                        </div>
                    </div>
                </div>
                <Table<Rate>
                    loading={loading}
                    columns={columns}
                    dataSource={rates}
                    rowKey={(record) => record.id}
                    pagination={false}
                    onRow={(record) => ({
                        onClick: () => {
                            setIsOpen(true);
                            setRateSelected(record);
                        }
                    })}
                />
                <div className={'mt-[10px]'}>
                    <Pagination pageSize={10} align={"center"} total={page.pageTotal * 10}
                                defaultCurrent={page.pageCurrent}
                                onChange={(e) => setPage({...page, pageCurrent: e})}/>
                </div>
            </div>
            <ModalRate data={rateSelected}
                       isOpen={isOpen}
                       rates={rates}
                       setRates={setRates}
                       setIsOpen={setIsOpen}
                       />
        </>
    )
}
export default Rate;