import React, {useEffect, useRef, useState} from "react";
import {
    addFilmIntoSub,
    deleteSubFilm,
    expireToken,
    getFilmNotInSub,
    getSubFilmCustom,
    getToken
} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {Modal, Pagination, Spin, Table} from "antd";

interface SubFilm {
    id : string,
    filmDto : Film
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
    typeFilms : string,
    status : string
}
interface Props {
    subId : string
}
interface Custom {
    limit : string,
    asc : string,
    pageCurrent : number,
    totalPage : number
}

const initCustom : Custom = {
    limit : '10',
    asc : 'asc',
    pageCurrent : 1,
    totalPage : 1
}
const columns = [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id'
    },
    {
        title: 'Tên phim',
        key: 'name',
        render : (item)=>(
            <p>{item.filmDto.name}</p>
        )
    },
]
const SubFilm : React.FC<Props> = (props) => {

    const {subId} = props;
    const [param, setParam] = useState<Custom>(initCustom);
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();
    const [subFilms, setSubFilms] = useState<SubFilm[]>([]);

    const [loadingFilm, setLoadingFilm] = useState<boolean>(false);
    const [films, setFilms] = useState<Film[]>([]);
    const selectedRef = useRef(null);

    const [reload, setReload] = useState<boolean>(false);
    const [isModal, setIsModal] = useState<boolean>(false);
    const [loadingModal, setLoadingModal] = useState<boolean>(false);
    const [selectedSl, setSelectedSl] = useState<string>('');

    useEffect(() => {
        if( subId == null || subId == '') return;
        handleFetchSubFilm();
    }, [param, subId, reload]);
    useEffect(() => {
        if( subId == null || subId == '') return;
        handleFetchFilmNotINSub();
    }, [subId,reload]);

    const handleFetchSubFilm = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await getSubFilmCustom(subId,param.pageCurrent-1,param.limit,param.asc,token);
        setLoading(false);
        if( response.status != 200 ){
            toast.error(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setSubFilms(response.data.data.content);
        if(param.pageCurrent != response.data.data.pageable.pageNumber + 1 || param.totalPage !=  response.data.data.totalPages){
            setParam({
                ...param,
                pageCurrent : response.data.data.pageable.pageNumber + 1,
                totalPage : response.data.data.totalPages
            })
        }
    }

    const handleFetchFilmNotINSub = async () => {
        setLoadingFilm(true);
        const response = await getFilmNotInSub(subId);
        setLoadingFilm(false);
        if( response.status != 200 ){
            toast.error(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setFilms(response.data.data);
    }

    const handleAddFilmInSub = async () => {
        const token : string | undefined = getToken();
        if(  token == undefined || expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        setLoadingFilm(true);
        const response = await addFilmIntoSub(subId,selectedRef.current.value, token);
        setLoading(false);
        setLoadingFilm(false);
        if( response.status != 201){
            toast.error(<p className={'w-full'}>Thêm không thành công</p>)
            return;
        }
        setReload(!reload);
        toast.success(<p className={'w-full'}>Thêm thành công</p>)
    }

    const handleCancel = () => {
        setIsModal(false);
    }

    const handleDelete = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingModal(true);
        const response = await deleteSubFilm(selectedSl, subId, token);
        setLoadingModal(false);
        console.log(response);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Xuất phim đã được sử dụng</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Xóa thành công</p>)
        setIsModal(false);
        setReload(!reload);
    }
    return (
        <>
            {
                subId != '' &&
                <div>
                    <div className={'mb-[20px]'}>
                        <p className={'font-bold uppercase text-xl'}>Danh Sách Phim</p>
                    </div>
                    {
                        films.length > 0 &&
                        <Spin tip={'Đang tải...'} spinning={loadingFilm}>
                            <div className={'my-[20px] flex justify-end gap-2'}>
                                <select ref={selectedRef} className={'outline-0 border-[1px] border-textAdmin px-2 py-1'}>
                                    {
                                        films.map(f => {
                                            return <option value={f.id} key={f.id}>
                                                {f.name}
                                            </option>
                                        })
                                    }
                                </select>
                                <button
                                    onClick={() => handleAddFilmInSub()}
                                    className={'px-2 py-1 bg-main text-white border-2 border-main'}>Thêm</button>
                            </div>
                        </Spin>
                    }
                    <Table<SubFilm>
                        loading={loading}
                        pagination={false}
                        columns={columns}
                        rowKey={row => row.id}
                        dataSource={subFilms}
                        onRow={(record) => ({
                            onClick: () => {
                                setIsModal(true);
                                setSelectedSl(record.filmDto.id);
                            }
                        })}
                    />
                    <div className={'mt-[10px]'}>
                        <Pagination pageSize={10} align={"center"} total={param.totalPage * 10}
                                    defaultCurrent={param.pageCurrent}
                                    onChange={(e) => setParam({...param, pageCurrent: e})}/>
                    </div>
                    <Modal
                        open={isModal}
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
                                    onClick={() => handleDelete()}
                                    className={'px-4 py-2 border-2 border-red-500 bg-red-500 text-white w-[100px]'}>Xác nhận
                                </button>
                            </div>
                        </div>
                    </Modal>
                </div>
            }
        </>
    )
}
export default SubFilm;