import React, {useEffect, useState} from "react";
import {createAccountEmployee, expireToken, getEmployee, getToken, resetEmployee} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {Modal, Spin, Table} from "antd";
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
interface CreateAccount {
    name : string,
    email : string,
    userName : string,
    password : string,
    cccd : string
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
const initCreateAccount = {
    name : '',
    email : '',
    userName : '',
    password : '',
    cccd : ''
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
    //modal xem account
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    const [loadingSee, setLoadingSee] = useState<boolean>(false);

    const [dataModal, setDataModal] = useState<DataType>();
    const [page, setPage] = useState<Page>(initPage);
    const [q, setQ] = useState<string>('');

    const [createAccount, setCreateAccount] = useState<CreateAccount>(initCreateAccount);
    const [loadingCreateAccout, setLoadingCreateAccount] = useState<boolean>(false);
    const [isOpenCreateAccount, setIsOpenCreateAccount] = useState<boolean>(false);

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

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token : string | undefined = getToken();
        if( token == undefined || expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingCreateAccount(true);
        const response = await createAccountEmployee(createAccount.name,createAccount.email,createAccount.cccd,createAccount.userName,token);
        setLoadingCreateAccount(false);
        console.log(response);
        if( response.status != 201){
            toast.warning(<p className={'w-full'}>Không thể tạo tài khoản</p>);
            return;
        }
        toast.success(<p>Tạo tài khoản thành công</p>)
        setIsOpenCreateAccount(false);
        const account : Employee = response.data.data;
        setData([
            account,
            ...data
        ])
        setCreateAccount(initCreateAccount);
    }

    const handleResetAccount = async (id : string) => {
        const token : string | undefined = getToken();
        if( token == undefined || expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingSee(true);
        const response = await resetEmployee(id, token);
        setLoadingSee(false);
        console.log(response);
        if( response.status != 200){
            toast.warning(<p className={'w-full'}>Không thể thiết lập lại</p>);
            return;
        }
        toast.success(<p>Thiết lập thành công</p>)
        setIsModalOpen(false);
    }

    return (
        <>
            <div className={'bg-white p-[20px]'}>
                <div className={'flex justify-end mb-[20px] gap-4'}>
                    <div onClick={()=>setIsOpenCreateAccount(true)} className={'flex gap-2 items-center border-[1px] border-textAdmin text-white cursor-pointer px-2 py-1 bg-textAdmin'}>
                        Tạo tài khoản
                    </div>
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
                <Table<DataType> columns={columns}
                                rowKey={r=>r.id}
                                 dataSource={
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
                loading={loadingSee}
                onCancel={() => handleCancelModal()}
                footer={[
                    <button onClick={()=>handleResetAccount(dataModal?.id)} className={'bg-red-500 text-white px-4 py-2'}>Thiết lập lại</button>
                ]}
            >
                <div className={'flex gap-4 flex-col mt-[20px]'}>
                <div className={'flex flex-col'}>
                        <label className={'text-main'}>Mã nhân viên <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal?.id} readOnly={true} className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex flex-col'}>
                        <label className={'text-main'}>Họ và tên <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal?.name} readOnly={true} className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex flex-col'}>
                        <label className={'text-main'}>Địa chỉ email <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal?.email} readOnly={true}
                               className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex gap-4 items-center justify-between'}>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>CCCD <span className={'text-red-500'}>*</span></label>
                            <input value={dataModal?.cccd} readOnly={true}
                                   minLength={10}
                                   maxLength={12}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>Tài khoản <span
                                className={'text-red-500'}>*</span></label>
                            <input value={dataModal?.username} readOnly={true}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                    </div>
                    <div className={'flex flex-col flex-1 w-1/2'}>
                        <label className={'text-main'}>Tài khoản <span
                            className={'text-red-500'}>*</span></label>
                        <select className={'outline-0 border-textAdmin border-[1px] px-2 py-1'} value={dataModal?.active} disabled={true}>
                            <option className={'text-green-600'}>ACTIVE</option>
                            <option className={'text-red-500'}>DELETE</option>
                        </select>
                    </div>
                </div>
            </Modal>
            <Modal
                open={isOpenCreateAccount}
                title={<p className={'font-bold text-main uppercase'}>Tạo Tài Khoản</p>}
                footer={[
                ]}
                onCancel={()=>setIsOpenCreateAccount(false)}
            >
                <Spin tip={"Đang xử lý..."} spinning={loadingCreateAccout}>
                    <form onSubmit={(e) => handleSubmit(e)}>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Họ và tên <span className={'text-red-500'}>*</span></label>
                            <input value={createAccount.name}
                                   required={true}
                                   minLength={10}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}
                                   onChange={(e) => setCreateAccount({...createAccount, name: e.target.value})}/>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Địa chỉ email <span
                                className={'text-red-500'}>*</span></label>
                            <input value={createAccount.email}
                                   type={"email"}
                                   required={true}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}
                                   onChange={(e) => setCreateAccount({...createAccount, email: e.target.value})}/>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Căn cước công dân <span
                                className={'text-red-500'}>*</span></label>
                            <input value={createAccount.cccd}
                                   minLength={10}
                                   maxLength={12}
                                   required={true}
                                   onChange={(e) => setCreateAccount({...createAccount, cccd: e.target.value})}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex flex-col'}>
                            <label className={'text-main'}>Tài khoản <span className={'text-red-500'}>*</span></label>
                            <input value={createAccount.userName}
                                   minLength={8}
                                   required={true}
                                   onChange={(e) => setCreateAccount({...createAccount, userName: e.target.value})}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex justify-center mt-[20px]'}>
                            <button className={'px-4 py-1 bg-main text-white font-bold uppercase min-w-[150px]'}>Tạo
                            </button>
                        </div>
                    </form>
                </Spin>
            </Modal>
        </>
    )
};
export default Staff;