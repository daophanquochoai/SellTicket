import React, {useEffect, useState} from "react";
import {expireToken, getAllContact, getToken} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {Modal, Pagination, Spin, Table} from "antd";
import {FaSearch} from "react-icons/fa";

interface Custom {
    limit : string,
    q : string,
    asc : string,
    status : string ,
    orderBy : string
}
interface Page {
    currentPage : number,
    totalPage : number
}

interface Contact {
    id : number,
    name : string,
    numberPhone : string,
    content : string,
    status : string,
    timestamp : string
}

const initCustom : Custom = {
    limit : '10',
    q : '',
    asc : 'asc',
    status : 'none',
    orderBy : 'name'
}
const initPage : Page = {
    currentPage : 1,
    totalPage : 1
}

const columns = [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id'
    },
    {
        title: 'Tên người gửi',
        dataIndex: 'name',
        key: 'name'
    },
    {
        title: 'Số điện thoại',
        dataIndex: 'numberPhone',
        key: 'numberPhone'
    },
    {
        title: 'Nội dung',
        dataIndex: 'content',
        key: 'content'
    },
    {
        title: 'Thời gian',
        dataIndex: 'timestamp',
        key: 'timestamp'
    },
    {
        title: 'Trạng thái',
        dataIndex: 'status',
        key: 'status',
        render : (text)=> (
            text == 'ACTIVE' ?
                <p className={'text-green-200 uppercase'}>Chưa đọc</p>
                :
                <p className={'text-red-600 uppercase'}>Đã đọc</p>
        )
    },
    {
        title: 'Hành động',
        key: 'action',
        render : () => (
            <button className={'px-4 py-2 text-white bg-main'}>Đánh dấu đọc</button>
        )
    },
]

const initContact: Contact = {
    id : 0,
    name : '',
    numberPhone : '',
    content : '',
    status : 'ACTIVE',
    timestamp : ''
}

const Contact: React.FC = () => {

    const [data, setData] = useState<Contact[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();

    // param
    const [param, setParam] = useState<Custom>(initCustom);
    const [page, setPage] = useState<Page>(initPage);
    const [search, setSearch] = useState<string>('');

    //modal
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [selected, setSelected] = useState<Contact>(initContact);


    useEffect(() => {
        handleGetAllContact()
    }, [param, page]);

    const handleGetAllContact = async () => {
        const token : string | undefined = getToken();
        if(  token == undefined || expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await getAllContact(page.currentPage-1, param.limit, param.q,param.asc,param.orderBy,param.status,token);
        setLoading(false);
        console.log(response)
        if( response.status != 200){
            toast.warning(<p>Không thể tải được dữ liệu</p>)
        }
        setData(response.data.data.content);
        if(page.currentPage != response.data.data.pageable.pageNumber + 1 || page.totalPage !=  response.data.data.totalPages){
            setPage({
                currentPage : response.data.data.pageable.pageNumber + 1,
                totalPage : response.data.data.totalPages
            })
        }
    }

    const handleStatusTypeFilm = (e) => {
        setParam({
            ...param,
            status : e.target.value
        })
    }
    const handleSortTypeFilm = (e) => {
        setParam({
           ...param,
           asc : e.target.value
        });
    }

    const handleOrderByTypeFilm = (e) => {
        setParam({
            ...param,
            orderBy : e.target.value
        })
    }

    const handleSearchTypeFilm = () => {
        setParam({
           ...param,
           q : search
        });
    }

    const handleClose = () => {
        setIsOpen(false);
        setSelected(initContact);
    }
    return (
        <div className={'bg-white p-[20px] rounded-xl'}>
            <div className={'flex gap-2 justify-end'}>
                <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                    <p>Trạng thái :</p>
                    <select className={'bg-transparent outline-0'} defaultValue={'none'}
                            onChange={(e) => handleStatusTypeFilm(e)}
                    >
                        <option value={'none'}>Tất cả</option>
                        <option value={'ACTIVE'}>Chưa đọc</option>
                        <option value={'DELETE'}>Đã đọc</option>
                    </select>
                </div>
                <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                    <p>Sắp xếp theo :</p>
                    <select className={'bg-transparent outline-0'} defaultValue={'name'}
                            onChange={(e) => handleOrderByTypeFilm(e)}
                    >
                        <option value={'name'}>Tên</option>
                        <option value={'content'}>Nội dung</option>
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
                           value={search}
                           onChange={(e) => setSearch(e.target.value)}
                    />
                    <button className={'text-xl'}
                            onClick={() => handleSearchTypeFilm()}
                    ><FaSearch/></button>
                </div>
            </div>
            <Spin tip={"Đang tải..."} spinning={loading}>
                <Table<Contact>
                    columns={columns}
                    dataSource={data}
                    pagination={false}
                    rowClassName={(record) => {
                        switch (record.status) {
                            case 'ACTIVE' :
                                return 'bg-textAdmin text-white';
                            case 'DELETE' :
                                return 'bg-white';
                            default:
                                return '';
                        }
                    }}
                    rowHoverable={false}
                    onRow={(record)=> ({
                        onClick : () => {
                            setSelected(record);
                            setIsOpen(true);
                    }
                    })}
                />
                <div className={'mt-[20px]'}>
                    <Pagination pageSize={10} align={"center"} total={page.totalPage * 10}
                                defaultCurrent={page.currentPage}
                                onChange={(e) => setPage({...page, currentPage: e})}/>
                </div>
            </Spin>
            <Modal
                onCancel={()=>handleClose()}
                open={isOpen}
                footer={[]}
                title={<p className={'text-main uppercase font-bold'}>Contact {selected.id}</p>}
            >
                <div className={'flex gap-2'}>
                    <label className={'text-blue-400 font-bold uppercase'}>Tên người gửi : </label>
                    <p>{selected.name}</p>
                </div>
                <div className={'flex flex-col'}>
                    <label  className={'text-blue-400 font-bold'}>Nội dung : </label>
                    <p>{selected.content}</p>
                </div>
                <div className={'flex gap-2'}>
                    <label  className={'text-blue-400 font-bold'}>Thời gian gửi : </label>
                    <p>{selected.timestamp}</p>
                </div>
                <div className={'flex gap-2'}>
                    <label  className={'text-blue-400 font-bold'}>Số điện thoại : </label>
                    <p>{selected.numberPhone}</p>
                </div>
                <div className={'flex gap-2'}>
                    <label  className={'text-blue-400 font-bold'}>Trạng thái : </label>
                    <p className={selected.status == 'ACTIVE' ? 'text-green-600' : 'text-red-700'}>{selected.status == 'ACTICE' ? 'Chưa đọc' : 'Đã đọc'}</p>
                </div>
            </Modal>
        </div>
    )
}
export default Contact;