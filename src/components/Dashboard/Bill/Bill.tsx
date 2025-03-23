import React, {lazy, useEffect, useState} from "react";
import {expireToken, getBillByCustom, getToken} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {Pagination, Table} from "antd";
import {FaSearch} from "react-icons/fa";

const ModalBill = lazy(() => import('./ModalBill.tsx'));

interface Bill {
    id : string,
    totalPrice : number,
    transactionCode : string,
    paymentMethodId : string,
    paymentMethod : string,
    active : string,
    chairs : Chair[],
    dishes: Dish[],
    timestamp : string,
    status : string,
    filmShowTimeId : number,
    timeEnd : string,
    timeStart : string,
    timeStampSee : string,
    roomId : string,
    nameRoom : string,
    nameBranch : string,
    address : string,
    filmId : string,
    nameFilm : string,
    userName : string,
    email : string,
    numberPhone : string
}
interface Chair {
    id: string,
    chairCode: string,
    price : number,
    ticket : Ticket,
    active : string
}
interface Dish {
    id : string,
    active : string,
    price : number,
    amount : number,
    dishDto : DishDetail
}
interface DishDetail {
    id : string,
    price : number,
    active : string,
    name : string,
    image : string,
}
interface Ticket {
    id : string,
    active : string,
    conditionUse : string,
    name : string,
    price : 1000
    typeTicket : string,
    slot : 1
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
    orderBy : 'timestamp',
    q : ''
}
const initPage : Page= {
    pageCurrent : 1,
    pageTotal : 1
}
const colums = [
    {
        title: 'Mã giao dịch',
        dataIndex: 'transactionCode',
        key: 'transactionCode',
        render : (text)=>(
            <p className={'text-purple-600 font-bold'}>{text}</p>
        )
    },
    {
        title: 'Phương thức thanh toán',
        dataIndex: 'paymentMethod',
        key: 'paymentMethod',
        render : (text)=>(
            <p className={'text-blue-400 font-bold'}>{text}</p>
        )
    },
    {
        title: 'Người dùng',
        dataIndex: 'userName',
        key: 'userName',
    },
    {
        title: 'Ngày giao dịch',
        dataIndex: 'timestamp',
        key: 'timestamp',
    },
    {
        title: 'Trạng thái',
        dataIndex: 'status',
        key: 'status',
        render : (text) => (
                text == 'SUCCESS' ?
                <p className={'text-green-600 font-bold'}>{text}</p>
                :
                <p className={'text-red-700 font-bold'}>{text}</p>
        )
    },
]
const initBill : Bill = {
    id : '',
    totalPrice : 0,
    transactionCode : "",
    paymentMethodId : "",
    paymentMethod : "",
    active : "",
    chairs : [],
    dishes: [],
    timestamp : "",
    status : "",
    filmShowTimeId : 0,
    timeEnd : "",
    timeStart : "",
    timeStampSee : "",
    roomId : "",
    nameRoom : "",
    nameBranch : "",
    address : "",
    filmId : "",
    nameFilm : "",
    userName : "",
    email : "",
    numberPhone : ""
}
const Bill : React.FC = () => {

    const [bills, setBills] = useState<Bill[]>([]);
    const [param, setParam] = useState<Custom>(initParam);
    const [page, setPage] = useState<Page>(initPage);
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();
    const [search, setSearch] = useState<string>('');

    //modal
    const [openModal, setOpenModal] = useState<boolean>(false);
    const [billSelected, setBillSeleted] = useState<Bill>(initBill);

    useEffect(() => {
        handleFetchBill();
    }, [param, page]);

    const handleFetchBill = async () => {
        const token: string = getToken();
        if (expireToken(token)) {
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await getBillByCustom(page.pageCurrent - 1, param.limit, param.asc, param.status, param.orderBy, param.q, token);
        console.log(response);
        setLoading(false);
        if (response.status != 200) {
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setBills(response.data.data.data);
        if (page.pageCurrent != response.data.data.pageCurrent || page.pageTotal != response.data.data.totalPages) {
            setPage({
                pageCurrent: response.data.data.pageCurrent,
                pageTotal: response.data.data.totalPages
            })
        }
    }

    useEffect(() => {
        console.log(param)
    }, [param]);

    return (
        <>
            <div className={'bg-white p-[20px] rounded-xl'}>
                <div>
                    <p className={'text-xl text-main font-bold uppercase'}>Danh sách hóa đơn</p>
                </div>
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
                                <option value={'timestamp'}>Ngày giao dịch</option>
                                <option value={'userName'}>Tên người dùng</option>
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
                <Table<Bill>
                    pagination={false}
                    columns={colums}
                    loading={loading}
                    dataSource={bills}
                    onRow={(record) => ({
                        onClick: () => {
                            setBillSeleted(record);
                            setOpenModal(true);
                        }
                    })}
                />
                <div className={'mt-[10px]'}>
                    <Pagination pageSize={10} align={"center"} total={page.pageTotal * 10} defaultCurrent={page.pageCurrent}
                                onChange={(e) => setPage({...page, pageCurrent: e})}/>
                </div>
            </div>
            <ModalBill
                isOpen={openModal}
                setIsOpen={setOpenModal}
                data={billSelected}
            />
        </>
    )
}
export default Bill;