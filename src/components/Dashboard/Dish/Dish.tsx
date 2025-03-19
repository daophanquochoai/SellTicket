import React, {lazy, useEffect, useState} from "react";
import {expireToken, getTicketByCustom, getToken, getTypeFilmByCustom} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {Pagination, Table} from "antd";
import {FaSearch} from "react-icons/fa";

const ModalDish = lazy(()=>import('./ModalDish.tsx'));
const ModalTicket = lazy(() => import('./ModalTicket.tsx'));

interface TypeDish {
    id : string,
    active : string,
    name : string,
    dishes : Dish[]
}
interface Dish {
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
    price : number,
    typeTicket : string,
    slot : number
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
    orderBy : 'name',
    q : ''
}
const initPage : Page= {
    pageCurrent : 1,
    pageTotal : 1
}
const columnsTicket = [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Tên vé',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Điều kiện',
        dataIndex: 'conditionUse',
        key: 'conditionUse',
    },
    {
        title: 'Giá',
        dataIndex: 'price',
        key: 'price',
        render: (text : number) => {
            return <p>{text.toLocaleString()}Đ</p>
        }
    },
    {
        title: 'Số lượng áp dụng',
        dataIndex: 'slot',
        key: 'slot'
    },
    {
        title: 'Loại vé',
        dataIndex: 'typeTicket',
        key: 'typeTicket',
    },
    {
        title: 'Trạng thái',
        dataIndex: 'active',
        key: 'active',
        render: (text) => {
            if( text == 'ACTIVE')
                return <p className={'text-green-600'}>{text}</p>
            return <p className={'text-red-700'}>{text}</p>
        }
    }
]
const columnsTypeDish = [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Tên loại',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Trạng thái',
        dataIndex: 'active',
        key: 'active',
        render: (text) => {
            if( text == 'ACTIVE')
            return <p className={'text-green-600'}>{text}</p>
            return <p className={'text-red-700'}>{text}</p>
        }
    }
]
const initTypeFilm : TypeDish = {
    id : '',
    active : '',
    name : '',
    dishes : []
}
const initTicket = {
    id : '',
    active : 'ACTIVE',
    conditionUse : '',
    name : '',
    price : 1000,
    typeTicket : 'ĐƠN',
    slot : 1
}

const Dish : React.FC = () => {

    const navigate = useNavigate();
    //loading
    const [loadingTypeFilm, setLoadingTypeFilm] = useState<boolean>(false);
    //param and page
    const [paramTypeFilm, setParamTypeFilm] = useState<Custom>(initParam);
    const [pageTypeFilm, setPageTypeFilm] = useState<Page>(initPage);
    const [searchTypeFilm, setSearchTypeFilm] = useState<string>('');
    //data
    const [dataTypeFilm, setDataTypeFilm] = useState<TypeDish[]>([]);
    //modal
    const [isOpenTypeDish, setIsOpenTypeDish] = useState<boolean>(false);
    const [typeDishSelected, setTypeDishSelected] = useState<TypeDish>(initTypeFilm);

    //ticket
    const [loadingTicket, setLoadingTicket] = useState<boolean>(false);
    const [paramTicket, setParamTicket] = useState<Custom>(initParam);
    const [searchTicket, setSearchTicket] = useState<string>('');
    const [dataTicket, setDataTicket] = useState<Ticket[]>([]);
    const [pageTicket, setPageTicket] = useState<Page>(initPage);

    // modal ticket
    const [loadingModalTicket, setLoadingModalTicket] = useState<boolean>(false);
    const [isOpenModalTicket, setIsOpenModalTicket] = useState<boolean>(false);
    const [dataTicketSelected, setDataTicketSelected] = useState<Ticket>(initTicket);
    const [activeTicket, setActiveTicket] = useState<string>('CREATE');


    useEffect(() => {
        handleFetchTypeFilm();
    }, [paramTypeFilm, pageTypeFilm]);

    useEffect(() => {
        handleFetchTicket();
    }, [paramTicket, pageTicket]);

    const handleFetchTypeFilm = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingTypeFilm(true);
        const response = await getTypeFilmByCustom(pageTypeFilm.pageCurrent-1,paramTypeFilm.limit,paramTypeFilm.asc,paramTypeFilm.status,paramTypeFilm.orderBy,paramTypeFilm.q, token);
        setLoadingTypeFilm(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setDataTypeFilm(response.data.data.content);
        if(pageTypeFilm.pageCurrent != response.data.data.pageable.pageNumber + 1 || pageTypeFilm.pageTotal !=  response.data.data.totalPages){
            setPageTypeFilm({
                pageCurrent : response.data.data.pageable.pageNumber + 1,
                pageTotal : response.data.data.totalPages
            })
        }
    }
    const handleFetchTicket = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingTicket(true);
        const response = await getTicketByCustom(pageTicket.pageCurrent-1,paramTicket.limit,paramTicket.asc,paramTicket.status,paramTicket.orderBy,paramTicket.q, token);
        setLoadingTicket(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        console.log(response)
        setDataTicket(response.data.data.content);
        if(pageTicket.pageCurrent != response.data.data.pageable.pageNumber+1  || pageTicket.pageTotal !=  response.data.data.totalPages){
            setPageTicket({
                pageCurrent : response.data.data.pageable.pageNumber+1,
                pageTotal : response.data.data.totalPages
            })
        }
    }
    const handleSearchTypeFilm = () => {
        setParamTypeFilm({
            ...paramTypeFilm,
            q : searchTypeFilm
        })
    }
    const handleSortTypeFilm = (e) => {
        setParamTypeFilm({
            ...paramTypeFilm,
            asc : e.target.value
        })
    }
    const handleOrderByTypeFilm = (e) => {
        setParamTypeFilm({
            ...paramTypeFilm,
            asc : e.target.value
        })
    }
    const handleStatusTypeFilm = (e) => {
        setParamTypeFilm({
            ...paramTypeFilm,
            status : e.target.value
        })
    }
    const handleCreateTicket = ()=>{
        setDataTicketSelected(initTicket);
        setActiveTicket('CREATE');
        setIsOpenModalTicket(true);
    }

    useEffect(() => {
        if( isOpenTypeDish){
            setTypeDishSelected(dataTypeFilm.find(i=>i.id==typeDishSelected.id))
        }
    }, [dataTypeFilm]);
    return (
        <>
            <div className={'bg-white p-[20px] rounded-xl'}>
                <div>
                    <p className={'font-bold uppercase text-2xl mb-[10px]'}>Danh sách loại đồ ăn</p>
                </div>
                <div className={'mb-[20px]'}>
                    <div className={'flex gap-2 justify-end'}>

                        <button
                            className={'flex gap-2 items-center border-[1px] border-textAdmin bg-textAdmin text-white px-2 py-1'}>
                            Tạo Loại Đồ Ăn
                        </button>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Trạng thái :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'none'}
                                    onChange={(e) => handleStatusTypeFilm(e)}
                            >
                                <option value={'none'}>Tất cả</option>
                                <option value={'ACTIVE'}>Còn kinh doanh</option>
                                <option value={'DELETE'}>Ngưng kinh doanh</option>
                            </select>
                        </div>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Sắp xếp theo :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'name'}
                                    onChange={(e) => handleOrderByTypeFilm(e)}
                            >
                                <option value={'name'}>Tên</option>
                            </select>
                        </div>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Thứ tự xếp :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'asc'}
                                    onChange={(e) => handleSortTypeFilm(e)}
                            >
                                <option value={'asc'}>Tăng dần</option>
                                <option value={'des'}>Giảm dần</option>
                            </select>
                        </div>
                        <div className={'bg-white px-2 py-1 border-textAdmin border-[1px]'}>
                            <input className={'px-2 outline-0'} placeholder={'Nhập tìm kiếm...'}
                                   value={searchTypeFilm}
                                   onChange={(e) => setSearchTypeFilm(e.target.value)}
                            />
                            <button className={'text-xl'}
                                    onClick={() => handleSearchTypeFilm()}
                            ><FaSearch/></button>
                        </div>
                    </div>
                </div>
                <Table<TypeDish> columns={columnsTypeDish}
                                 dataSource={dataTypeFilm}
                                 pagination={false}
                                 loading={loadingTypeFilm}
                                 onRow={(record) => ({
                                     onClick: () => {
                                         setTypeDishSelected(record);
                                         setIsOpenTypeDish(true);
                                     }
                                 })}
                                 scroll={{x: 'max-content'}}
                />
                <div className={'mt-[20px]'}>
                    <Pagination pageSize={10} align={"center"} total={pageTypeFilm.pageTotal * 10} defaultCurrent={pageTypeFilm.pageCurrent}
                                onChange={(e) => setPageTypeFilm({...pageTypeFilm, pageCurrent: e})}/>
                </div>
            </div>
            <div className={'bg-white p-[20px] rounded-xl mt-[20px]'}>
                <div>
                    <p className={'font-bold uppercase text-2xl mb-[10px]'}>Danh sách loại vé</p>
                </div>
                <div className={'mb-[20px]'}>
                    <div className={'flex gap-2 justify-end'}>

                        <button
                            onClick={()=>handleCreateTicket()}
                            className={'flex gap-2 items-center border-[1px] border-textAdmin bg-textAdmin text-white px-2 py-1'}>
                            Tạo Vé
                        </button>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Trạng thái :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'none'}
                                    onChange={(e) => setParamTicket({...paramTicket, status : e.target.value})}
                            >
                                <option value={'none'}>Tất cả</option>
                                <option value={'ACTIVE'}>Còn kinh doanh</option>
                                <option value={'DELETE'}>Ngưng kinh doanh</option>
                            </select>
                        </div>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Sắp xếp theo :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'name'}
                                    onChange={(e) => setParamTicket({...paramTicket, orderBy : e.target.value})}
                            >
                                <option value={'name'}>Tên</option>
                                <option value={'conditionUse'}>Điều kiến áp dụng</option>
                                <option value={'price'}>Giá</option>
                                <option value={'typeTicket'}>Loại vé</option>
                                <option value={'slot'}>Số lượng áp dụng</option>
                            </select>
                        </div>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Thứ tự xếp :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'asc'}
                                    onChange={(e) => setParamTicket({...paramTicket,asc : e.target.value})}
                            >
                                <option value={'asc'}>Tăng dần</option>
                                <option value={'des'}>Giảm dần</option>
                            </select>
                        </div>
                        <div className={'bg-white px-2 py-1 border-textAdmin border-[1px]'}>
                            <input className={'px-2 outline-0'} placeholder={'Nhập tìm kiếm...'}
                                   value={searchTypeFilm}
                                   onChange={(e) => setSearchTicket(e.target.value)}
                            />
                            <button className={'text-xl'}
                                    onClick={() => setParamTicket({...paramTicket, q : searchTicket})}
                            ><FaSearch/></button>
                        </div>
                    </div>
                </div>
                <Table<Ticket> columns={columnsTicket}
                                 dataSource={dataTicket}
                                 pagination={false}
                                 loading={loadingTicket}
                                 onRow={(record) => ({
                                     onClick: () => {
                                         setActiveTicket('UPDATE');
                                         setIsOpenModalTicket(true);
                                         setDataTicketSelected(record);
                                     }
                                 })}
                                scroll={{x: 'max-content'}}
                />
                <div className={'mt-[20px]'}>
                    <Pagination pageSize={10} align={"center"} total={pageTicket.pageTotal * 10} defaultCurrent={pageTicket.pageCurrent}
                                onChange={(e) => setPageTicket({...pageTicket, pageCurrent: e})}/>
                </div>
            </div>
            {/*  Modal  */}
            <ModalDish
                isModal={isOpenTypeDish}
                setIsModal={setIsOpenTypeDish}
                typeDish={typeDishSelected}
                dataTypeFilm={dataTypeFilm}
                setDataTypeFilm={setDataTypeFilm}
                setTypeDishSelected={setTypeDishSelected}
            />
            <ModalTicket
                loading={loadingModalTicket}
                setLoading={setLoadingModalTicket}
                isOpen={isOpenModalTicket}
                setIsOpen={setIsOpenModalTicket}
                data={dataTicketSelected}
                setData={setDataTicketSelected}
                active={activeTicket}
                dataAll={dataTicket}
                setDataAll={setDataTicket}
            />
        </>
    )
}
export default Dish;