import React, {lazy, useEffect, useState} from "react";
import {createBranch, expireToken, getBranch, getRoomByBranchId, getToken, updateBranch} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {Modal, Pagination, Table} from "antd";
import {FaSearch} from "react-icons/fa";
import {useNavigate} from "react-router-dom";

const ModalRoom = lazy(()=>import('./ModalRoom.tsx'));

interface Branch {
    id : string,
    nameBranch : string,
    address : string,
    status : string
}
interface Room{
    id : string,
    name : string,
    positionChair : number[][],
    branch : Branch,
    status : string
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
const initPage : Page = {
    pageCurrent : 1,
    pageTotal : 1
}
const initBranch  = {
    id : '',
    nameBranch : '',
    address : '',
    status : 'ACTIVE'
}
const initCustom = {
    page : 0,
    limit : 10,
    asc : 'asc',
    status : 'none',
    orderBy : 'nameBranch',
    q : ''
}
const columns = [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Tên chi nhánh',
        dataIndex: 'nameBranch',
        key: 'nameBranch',
    },
    {
        title: 'Địa chỉ',
        dataIndex: 'address',
        key: 'address',
    },
    {
        title: 'Trạng thái',
        dataIndex: 'status',
        key: 'status',
        render: (text)=>{
            if( text == 'ACTIVE'){
                return <p className={'text-green-600'}>{text}</p>
            }else{
                return <p className={'text-red-600'}>{text}</p>
            }
        }
    },
]

const columnRoom = [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Tên phòng',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Trạng thái',
        dataIndex: 'status',
        key: 'status',
    },
]

const initRoom = {
    id : '',
    name : '',
    branch : {
        id : '',
        nameBranch : '',
        address : '',
        status : 'ACTIVE'
    },
    status : 'ACTIVE',
    positionChair : [
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
    ]
}
const Theater: React.FC = () => {

    const [loading, setLoading] = useState<boolean>(false);
    const [dataBranch, setDataBranch] = useState<Branch[]>([]);
    const [dataRoom, setDataRoom] = useState<Room[]>([]);
    const [q, setQ] = useState<string>('');
    const navigate = useNavigate();
    const [params, setParams] = useState<Custom>(initCustom);
    const [pageBranch, setPageBranch] = useState(initPage);

    //modal branch
    const [dataModal, setDataModal] = useState<Branch>(initBranch);
    const [loadingModal, setLoadingModal] = useState<boolean>(false);
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    const [selectBranch, setSelectBranch] = useState<string>('');
    const [branchSelect, setBranchSelect] = useState<Branch>(initBranch);

    //modal room
    const [loadingRoom, setLoadingRoom] = useState<boolean>(false);
    const [isModalOpenRoom, setIsModalOpenRoom] = useState<boolean>(false);
    const [dataRoomSelect, setDataRoomSelect] = useState<Room>(initRoom);
    const [activeRoom, setActiveRoom] = useState<string>('ACTIVE');

    useEffect(() => {
        handleFetchBranch();
    }, [params, pageBranch]);

    useEffect(() => {
        if( selectBranch.length == 0) return;
        const temp : Branch = dataBranch.find(i=>i.id==selectBranch);
        if( temp == undefined){
            toast.warning(<p>Không thể tải chi tiết</p>)
            return;
        }
        setBranchSelect(temp);
        handleFetchRoomByBranch();
    }, [selectBranch]);


    const handleFetchBranch = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await getBranch(pageBranch.pageCurrent-1, params.limit, params.asc, params.status, params.orderBy, params.q, token);
        console.log(response);
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setDataBranch(response.data.data.content);
        if(pageBranch.pageCurrent != response.data.data.pageable.pageNumber + 1 || pageBranch.pageTotal !=  response.data.data.totalPages){
            setPageBranch({
                pageCurrent : response.data.data.pageable.pageNumber + 1,
                pageTotal : response.data.data.totalPages
            });
        }
    }

    const handleFetchRoomByBranch = async () => {
        if(selectBranch.length == 0) return;
        setLoadingRoom(true);
        const response = await getRoomByBranchId(selectBranch);
        setLoadingRoom(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setDataRoom(response.data.data);
    }

    const handleCancel = () => {
        setIsModalOpen(false);
        setDataModal(initBranch);
    }

    const handleClickCreate = () => {
        setIsModalOpen(true);
    }

    const handleNameBranch = (e) => {
        setDataModal({
            ...dataModal,
            nameBranch : e.target.value
        })
    }

    const handleAddress = (e) => {
        setDataModal({
            ...dataModal,
            address : e.target.value
        })
    }

    const handleStatus = (e) => {
        setDataModal({
            ...dataModal,
            status : e.target.value
        })
    }

    const handleUpdateBranch = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingRoom(true);
        const response = await updateBranch(branchSelect, token);
        setLoadingRoom(false);
        if( response.status == 400 ){
            toast.warning(<p className={'w-full'}>{response.response?.data?.message}</p>)
            return;
        }
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể cập nhật dữ liệu</p>)
            return;
        }
        console.log(response.data.data)
        setDataBranch(
            dataBranch.map(i => {
                if( i.id == response.data.data.id){
                    return response.data.data;
                }
                return i;
            })
        )
        setSelectBranch('')
        toast.success(<p className={'w-full'}>Cập nhật dữ liệu thành công</p>)
    }

    const handleCreateBranch = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingModal(true);
        console.log(dataModal);
        const response = await createBranch(dataModal, token);
        setLoadingModal(false);
        console.log(response);
        if( response.status != 201 ){
            toast.warning(<p className={'w-full'}>Tạo chi nhánh không thành công</p>)
            return;
        }
        setDataBranch([
            response.data.data,
            ...dataBranch
        ])
        setIsModalOpen(false);
        setDataModal(initBranch);
        toast.warning(<p className={'w-full'}>Tạo chi nhánh thành công</p>)
    }

    const handleSearch = () => {
        setParams({
            ...params,
            q : q
        })
    }

    const handleCreateRoom = () => {
        setDataRoomSelect(initRoom);
        setActiveRoom('CREATE');
        setIsModalOpenRoom(true);
    }

    return (
        <>
            <div className={'bg-white p-[20px] rounded-xl'}>
                <div className={'flex justify-end mb-[20px] gap-2'}>
                    <button onClick={() => handleClickCreate()}
                            className={'flex gap-2 items-center border-[1px] border-textAdmin bg-textAdmin text-white px-2 py-1'}>
                        Tạo Chi Nhánh
                    </button>
                    <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                        <p>Trạng thái :</p>
                        <select className={'bg-transparent outline-0'} defaultValue={'none'}
                                value={params.status}
                                onChange={(e) => setParams({...params, status: e.target.value})}>
                            <option value={'none'}>Tất cả</option>
                            <option value={'ACTIVE'}>Còn hoạt động</option>
                            <option value={'DELETE'}>Ngưng hoạt động</option>
                        </select>
                    </div>
                    <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                        <p>Sắp xếp theo :</p>
                        <select className={'bg-transparent outline-0'} defaultValue={'none'}
                                value={params.orderBy}
                                onChange={(e) => setParams({...params, orderBy: e.target.value})}>
                            <option value={'nameBranch'}>Tên chi nhánh</option>
                            <option value={'address'}>Địa chỉ</option>
                        </select>
                    </div>
                    <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                        <p>Thứ tự :</p>
                        <select className={'bg-transparent outline-0'} defaultValue={'none'}
                                value={params.orderBy}
                                onChange={(e) => setParams({...params, asc: e.target.value})}>
                            <option value={'asc'}>Tăng dần</option>
                            <option value={'des'}>Giảm dần</option>
                        </select>
                    </div>
                    <div className={'bg-white px-2 py-1 border-textAdmin border-[1px]'}>
                        <input className={'px-2 outline-0'} value={q} placeholder={'Nhập tìm kiếm...'}
                               onChange={(e) => setQ(e.target.value)}/>
                        <button onClick={() => handleSearch()} className={'text-xl'}><FaSearch/></button>
                    </div>
                </div>
                <Table<Branch> columns={columns}
                               dataSource={
                                   dataBranch
                               }
                               pagination={false}
                               loading={loading}
                               onRow={(record) => ({
                                   onClick: () => {
                                       setSelectBranch(record.id);
                                   }
                               })}
                               scroll={{x: 'max-content'}}
                />
                <div className={'mt-[20px]'}>
                    <Pagination pageSize={10} align={"center"} total={pageBranch.pageTotal * 10}
                                defaultCurrent={pageBranch.pageCurrent}
                                onChange={(e) => setPageBranch({...pageBranch, pageCurrent: e})}/>
                </div>
            </div>
            {
                selectBranch != '' &&
                <div className={'flex items-start bg-white mt-[40px] p-[20px] rounded-xl gap-4'}>
                    <div className={'flex-1 flex flex-col gap-4'}>
                        <div className={'flex flex-col flex-1'}>
                        <label className={'text-main'}>ID <span className={'text-red-500'}>*</span></label>
                            <input value={branchSelect.id} required disabled={true}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>Tên chi nhánh <span
                                className={'text-red-500'}>*</span></label>
                            <input value={branchSelect.nameBranch} required
                                   onChange={(e) => setBranchSelect({...branchSelect, nameBranch: e.target.value})}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>Địa chỉ <span className={'text-red-500'}>*</span></label>
                            <input value={branchSelect.address} required
                                   onChange={(e) => setBranchSelect({...branchSelect, address: e.target.value})}
                                   className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex items-center gap-2'}>
                            <p className={'text-main'}>Trạng thái :</p>
                            <select className={'outline-0'} value={branchSelect.status}
                                    onChange={(e) => setBranchSelect({...branchSelect, status: e.target.value})}>
                                <option value={'ACTIVE'}>Còn hoạt động</option>
                                <option value={'DELETE'}>Đã ngừng hoạt động</option>
                            </select>
                        </div>
                        <div className={'flex justify-end'}>
                            <button className={'text-white bg-main px-3 py-2'} onClick={() => handleUpdateBranch()}>Cập
                                nhật
                            </button>
                        </div>
                    </div>
                    <div className={'flex-[2] '}>
                        <div className={'flex justify-between items-end mb-[10px]'}>
                            <p className={'text-[16px] font-bold uppercase mb-[10px]'}>Danh sách phòng</p>
                            <button onClick={() => handleCreateRoom()} className={'px-4 py-2 bg-textAdmin text-white'}>
                                Tạo phòng
                            </button>
                        </div>
                        <Table<Room> columns={columnRoom}
                                     dataSource={dataRoom}
                                     pagination={false}
                                     loading={loadingRoom}
                                     onRow={(record) => ({
                                         onClick: () => {
                                             setDataRoomSelect(JSON.parse(JSON.stringify(record))); // dêp copy
                                             setActiveRoom('UPDATE');
                                             setIsModalOpenRoom(true);
                                         }
                                     })}
                                     scroll={{x: 'max-content'}}
                        />
                    </div>
                </div>
            }
            <Modal
                open={isModalOpen}
                loading={loadingModal}
                footer={
                    <button onClick={() => handleCreateBranch()} className={'text-white bg-main px-2 py-1'}>Tạo chi
                        nhánh</button>
                }
                title={<p className={'text-main text-xl uppercase font-bold'}>Phim</p>}
                onCancel={() => handleCancel()}
            >
                <div className={'flex gap-4 flex-col'}>
                    <div className={'flex flex-col flex-1'}>
                        <label className={'text-main'}>Tên chi nhánh <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal.nameBranch} required onChange={(e) => handleNameBranch(e)}
                               className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex flex-col flex-1'}>
                        <label className={'text-main'}>Địa chỉ <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal.address} required onChange={(e) => handleAddress(e)}
                               className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex items-center gap-2'}>
                        <p className={'text-main'}>Trạng thái :</p>
                        <select className={'outline-0'} defaultValue={dataModal.status}
                                onChange={(e) => handleStatus(e)}>
                            <option value={'ACTIVE'}>Còn hoạt động</option>
                            <option value={'DELETE'}>Đã ngừng hoạt động</option>
                        </select>
                    </div>
                </div>
            </Modal>
            <ModalRoom
                loading={loadingRoom}
                setLoading={setLoadingRoom}
                setDataRoom={setDataRoomSelect}
                dataRoom={dataRoomSelect}
                setIsModalOpen={setIsModalOpenRoom}
                isModalOpen={isModalOpenRoom}
                active={activeRoom}
                dataRoomAll={dataRoom}
                setDataRoomAll={setDataRoom}
                branchSelect={branchSelect}
            />
        </>
    )
};
export default Theater;