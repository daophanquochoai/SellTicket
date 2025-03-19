import React, {useEffect, useState} from "react";
import {getSubCustom} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {Pagination, Table} from "antd";
import {FaSearch} from "react-icons/fa";

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
]

const Sub : React.FC = () => {

    const [param, setParam] = useState<Custom>(initParam);
    const [page, setPage] = useState<Page>(initPage);
    const [subs, setSubs] = useState<Sub[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [search, setSearch] = useState<string>('');

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

    return (
        <>
            <div className={'flex gap-4 bg-white p-[20px] rounded-xl'}>
                <div className={'flex-1'}>
                    <div className={'mb-[10px]'}>
                        <div className={'flex gap-2 justify-end flex-col'}>
                            <div className={'flex gap-2'}>
                                <button
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
                        pagination={false}
                        onRow={(record) => ({
                            onClick: () => {
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

                </div>
            </div>
        </>
    )
}
export default Sub;