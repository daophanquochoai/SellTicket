import React, {useEffect, useState} from "react";
import {createSub, deleteSubById, expireToken, getSubCustom, getToken, updateSub} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {Modal, Pagination, Table} from "antd";
import {FaSearch} from "react-icons/fa";
import {useNavigate} from "react-router-dom";
import SubFilm from "./SubFilm.tsx";

interface Sub {
    id : string,
    name : string
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
    orderBy : 'sub',
    q : ''
}
const initPage : Page= {
    pageCurrent : 1,
    pageTotal : 1
}
const initSub : Sub = {
    id : '',
    name : ''
}

const Sub : React.FC = () => {

    const colums = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id'
        },
        {
            title: 'Tên loại phim',
            dataIndex: 'name',
            key: 'name'
        },
        {
            title: 'Hành động',
            key: 'action',
            render : (item) => (
                <div className={'flex gap-2'}>
                    <button
                        onClick={() => handleOpen(item.id)}
                        className={'bg-red-500 text-white px-3 py-1'}>Xóa
                    </button>
                    <button
                        onClick={() => handleUpdate(item)}
                        className={'bg-yellow-800 text-white px-3 py-1'}>Cập nhật
                    </button>
                </div>
            )
        },
    ]

    const [param, setParam] = useState<Custom>(initParam);
    const [page, setPage] = useState<Page>(initPage);
    const [subs, setSubs] = useState<Sub[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [search, setSearch] = useState<string>('');
    const navigate = useNavigate();

    //modal accept
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [selected, setSelected] = useState<string>('');
    const [loadingModal, setLoadingModal] = useState<boolean>(false);

    //modal create sub
    const [isOpenCreate, setIsOpenCreate] = useState<boolean>(false);
    const [data, setData] = useState<Sub>(initSub);
    const [loadingModalCreate, setLoadingModalCreate] = useState<boolean>(false);
    const [active, setActive] = useState<string>('CREATE');

    useEffect(() => {
        handleFetchSubs();
    }, [param,page]);

    const handleFetchSubs = async () => {
        setLoading(true);
        const response = await getSubCustom(page.pageCurrent-1,param.limit,param.asc,param.orderBy,param.q);
        setLoading(false);
        if( response.status != 200){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setSubs(response.data.data.content);
        if(page.pageCurrent != response.data.data.pageable.pageNumber + 1 || page.pageTotal !=  response.data.data.totalPages){
            setPage({
                pageCurrent : response.data.data.pageable.pageNumber + 1,
                pageTotal : response.data.data.totalPages
            })
        }
    }

    const handleOpen = (id : string) => {
        setIsOpen(true);
        setSelected(id);
    }

    const handleUpdate = (arg : Sub) => {
        setData(arg);
        setIsOpenCreate(true);
        setActive('UPDATE')
    }

    const handleCancel = () => {
        setIsOpen(false);
        setSelected('');
    }

    const handleDeleteSubById = async (id : string) => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingModal(true);
        const response = await deleteSubById(id, token);
        console.log(response)
        setLoadingModal(false);
        if( response.status != 200 ){
            toast.error(<p className={'w-full'}>Không thể xóa</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Xóa thành công</p>)
        setSubs([...subs.filter(i=>i.id!=id)]);
        setIsOpen(false);
    }

    const handleCreateSub = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingModalCreate(true);
        const response = await createSub(data.name, token);
        setLoadingModalCreate(false);
        if( response.status != 201){
            toast.error(<p className={'w-full'}>Không thể tạo</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Tạo thành công</p>)
        setSubs([response.data.data,...subs]);
        setIsOpenCreate(false);
        setData(initSub);
    }

    const handleUpdateSub = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingModalCreate(true);
        const response = await updateSub(data.id,data.name, token);
        setLoadingModalCreate(false);
        if( response.status != 200){
            toast.error(<p className={'w-full'}>Không thể cập nhật</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Cập nhật thành công</p>)
        setSubs([...subs.map( item => {
            if( item.id === data.id){
                return response.data.data;
            }
            return item;
        })]);
        setIsOpenCreate(false);
        setData(initSub);
    }

    return (
        <div className={'bg-white p-[20px] rounded-xl'}>
            <div className={'mb-[20px]'}>
                <p className={'font-bold uppercase text-xl'}>Danh Sách loại phim</p>
            </div>
            <div className={'flex gap-4'}>
                <div className={'flex-1'}>
                    <div className={'mb-[10px]'}>
                        <div className={'flex gap-2 justify-end flex-col'}>
                            <div className={'flex gap-2'}>
                                <button
                                    onClick={() => {
                                        setIsOpenCreate(true);
                                        setActive("CREATE")
                                    }}
                                    className={'flex gap-2 items-center border-[1px] border-textAdmin bg-textAdmin text-white px-2 py-1'}>
                                    Tạo loại
                                </button>
                                <div
                                    className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                                    <p>Sắp xếp theo :</p>
                                    <select className={'bg-transparent outline-0'} defaultValue={'name'}
                                            onChange={(e) => setParam({...param, orderBy: e.target.value})}
                                    >
                                        <option value={'sub'}>Tên</option>
                                        <option value={'ID'}>ID</option>
                                    </select>
                                </div>
                            </div>
                            <div className={'flex gap-2'}>
                                <div
                                    className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
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
                    </div>
                    <Table<Sub>
                        loading={loading}
                        columns={colums}
                        dataSource={subs}
                        rowKey={row => row.id}
                        pagination={false}
                        onRow={(record) => ({
                            onClick: () => {
                                setSelected(record.id)
                            }
                        })}
                    />
                    <div className={'mt-[10px]'}>
                        <Pagination pageSize={10} align={"center"} total={page.pageTotal * 10}
                                    defaultCurrent={page.pageCurrent}
                                    onChange={(e) => setPage({...page, pageCurrent: e})}/>
                    </div>
                </div>
                <div className={'flex-1'}>
                    <SubFilm subId={selected}/>
                </div>
            </div>
            <Modal
                open={isOpen}
                loading={loadingModal}
                onCancel={() => handleCancel()}
                closeIcon={[]}
                footer={[]}
            >
                <div>
                    <div><p className={'text-main text-xl font-bold text-center'}>Bạn có chắc chắn muốn xóa ?</p></div>
                    <div className={'justify-center gap-4 flex items-center mt-[20px]'}>
                        <button
                            onClick={() => handleCancel()}
                            className={'px-4 py-2 border-textAdmin border-2 w-[100px] text-textAdmin'}>Hủy
                        </button>
                        <button
                            onClick={() => handleDeleteSubById(selected)}
                            className={'px-4 py-2 border-2 border-red-500 bg-red-500 text-white w-[100px]'}>Xác nhận
                        </button>
                    </div>
                </div>
            </Modal>
            <Modal
                title={<p className={'text-main text-xl font-bold uppercase'}>Loại phim</p>}
                open={isOpenCreate}
                loading={loadingModalCreate}
                onCancel={() => setIsOpenCreate(false)}
                footer={[
                    active == 'CREATE' ?
                        <button
                            onClick={() => handleCreateSub()}
                            className={'px-4 py-2 bg-main text-white'}>Tạo</button>
                        :
                        <button
                            onClick={() => handleUpdateSub()}
                            className={'px-4 py-2 bg-main text-white'}>Cập nhật</button>
                ]}
            >
                <div className={'flex flex-col'}>
                    <label>Tên loại <span className={'text-red-500'}>*</span></label>
                    <input value={data.name}
                           onChange={(e) => setData({...data, name: e.target.value})}
                           className={'px-2 py-1 border-2 border-textAdmin outline-0'}/>
                </div>
            </Modal>
        </div>
    )
}
export default Sub;