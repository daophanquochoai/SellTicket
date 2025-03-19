import React, {useEffect, useState} from "react";
import {expireToken, getEmployee, getToken} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {Modal, Table} from "antd";
import { Pagination } from 'antd';
import { FaSearch } from "react-icons/fa";

interface Custom {
    page : number,
    limit : number,
    asc : string,
    status : string,
    orderBy : string,
    q : string
}
interface Employee{
    account : Account,
    cccd : string,
    email : string,
    id : string,
    name : string,
    status : string
}
interface Account {
    active : string,
    password : string,
    role : Role,
    userName : string
}
interface Role {
    id: number,
    roleName: string,
    status: string
}
interface DataType {
    id : string,
    name : string,
    email : string,
    cccd : string,
    username : string,
    active : string
}
interface Page {
    pageCurrent : number,
    pageTotal : number
}
const initCustom = {
    page : 0,
    limit : 10,
    asc : 'asc',
    status : 'none',
    orderBy : 'name',
    q : ''
}
const initPage : Page = {
    pageCurrent : 1,
    pageTotal : 1
}

const columns = [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Họ và tên',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',
    },
    {
        title: 'CCCD',
        dataIndex: 'cccd',
        key: 'cccd',
    },
    {
        title: 'Tài khoản',
        dataIndex: 'username',
        key: 'username',
    },
    {
        title: 'Hoạt động',
        dataIndex: 'active',
        key: 'active',
        render : (text) => (
            <>
                {
                    text == 'ACTIVE' ?
                        <p className={'text-green-600'}>{text}</p>
                        :
                        <p className={'text-red-600'}>{text}</p>
                }
            </>
        )
    },
]

const Staff : React.FC = () => {

    const [loading, setLoading] = useState<boolean>(false);
    const [params, setParams] = useState<Custom>(initCustom);
    const navigate = useNavigate();
    const [data,setData] = useState<Employee[]>([]);
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    const [dataModal, setDataModal] = useState<DataType>();
    const [page, setPage] = useState<Page>(initPage);
    const [q, setQ] = useState<string>('');

    useEffect(() => {
        handleFetchEmployee();
    }, [params, page]);

    const handleFetchEmployee = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await getEmployee(page.pageCurrent-1, params.limit, params.asc, params.status, params.orderBy, params.q, token);
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setData(response.data.data.content);
        if(page.pageCurrent != response.data.data.pageable.pageNumber + 1 || page.pageTotal !=  response.data.data.totalPages){
            setPage({
                pageCurrent : response.data.data.pageable.pageNumber + 1,
                pageTotal : response.data.data.totalPages
            });
        }
    }

    const handleCancelModal = async () => {
        setIsModalOpen(false);
    }

    const handleSearch = () => {
        setParams({
           ...params,
           q : q
        });
    }

    const handleSort = (value) => {
        setParams({
            ...params,
            asc : value
        })
    }

    const handleOrder = (value) => {
        setParams({
            ...params,
            orderBy : value
        })
    }

    return (
        <>
            <div className={'bg-white p-[20px]'}>
                <div className={'flex justify-end mb-[20px] gap-4'}>
                    <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                        <p>Sắp xếp theo :</p>
                        <select className={'bg-transparent outline-0'} defaultValue={'name'}
                                onChange={(e) => handleOrder(e.target.value)}>
                            <option value={'name'}>Tên</option>
                            <option value={'email'}>Email</option>
                        </select>
                    </div>
                    <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                        <p>Thứ tự xếp :</p>
                        <select className={'bg-transparent outline-0'} defaultValue={'asc'}
                                onChange={(e) => handleSort(e.target.value)}>
                            <option value={'asc'}>Tăng dần</option>
                            <option value={'des'}>Giảm dần</option>
                        </select>
                    </div>
                    <div className={'flex gap-2 items-center border-textAdmin border-[1px] bg-white px-2 py-1'}>
                        <input value={q} onChange={(e) => setQ(e.target.value)} className={'outline-0'}/>
                        <button onClick={() => handleSearch()} className={'cursor-pointer'}><FaSearch
                            className={'text-2xl text-main'}/></button>
                    </div>
                </div>
                <Table<DataType> columns={columns} dataSource={
                    data.map(item => {
                        return {
                            id: item.id,
                            name: item.name,
                            email: item.email,
                            cccd: item.cccd,
                            username: item.account.userName,
                            active: item.account.active
                        }
                    })}
                                 pagination={false}
                                 loading={loading}
                                 onRow={(record) => ({
                                     onClick: () => {
                                         setDataModal(record);
                                         setIsModalOpen(true);
                                     }
                                 })}
                />
                <div className={'mt-[20px]'}>
                    <Pagination pageSize={10} align={"center"} total={page.pageTotal * 10}
                                defaultCurrent={page.pageCurrent}
                                onChange={(e) => setPage({...page, pageCurrent: e})}/>
                </div>
            </div>

            <Modal
                title={<p className={'text-main text-xl uppercase font-bold'}>Nhân viên</p>}
                open={isModalOpen}
                onCancel={() => handleCancelModal()}
                footer={[]}
            >
                <div className={'flex gap-4 flex-col mt-[20px]'}>
                    <div className={'flex flex-col'}>
                        <label className={'text-main'}>Mã nhân viên <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal?.id} className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex flex-col'}>
                        <label className={'text-main'}>Họ và tên <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal?.name} className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex flex-col'}>
                        <label className={'text-main'}>Địa chỉ email <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal?.email}
                               className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex gap-4 items-center justify-between'}>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>CCCD <span className={'text-red-500'}>*</span></label>
                            <input value={dataModal?.cccd}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>Tài khoản <span
                                className={'text-red-500'}>*</span></label>
                            <input value={dataModal?.username}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                    </div>
                    <div className={'flex flex-col flex-1 w-1/2'}>
                        <label className={'text-main'}>Tài khoản <span
                            className={'text-red-500'}>*</span></label>
                        <select className={'outline-0 border-textAdmin border-[1px] px-2 py-1'} value={dataModal?.active}>
                            <option className={'text-green-600'}>ACTIVE</option>
                            <option className={'text-red-500'}>DELETE</option>
                        </select>
                    </div>
                </div>
            </Modal>
        </>
    )
};
export default Staff;