import {createFilm, expireToken, getFilm, getSub, getToken, getTypeFilm, updateFilm} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import React, {useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Modal, Pagination, Select, Spin, Table} from "antd";
import {FaSearch} from "react-icons/fa";
import { UploadOutlined } from '@ant-design/icons';
import type { UploadProps } from 'antd';
import { Button, Upload } from 'antd';
import './style.css';
import { Editor } from '@tinymce/tinymce-react';


interface Film {
    id: string,
    name : string,
    age : number,
    image : string,
    sub: Sub[],
    nation : string,
    duration : string,
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

const initPage : Page = {
    pageCurrent : 1,
    pageTotal : 1
}
const initCustom = {
    page : 0,
    limit : 10,
    asc : 'asc',
    status : 'none',
    orderBy : 'name',
    q : ''
}
const columns = [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Tên phim',
        dataIndex: 'name',
        key: 'name',
        render: (text) => {
            return <p className={'font-bold'}>{text}</p>
        }
    },
    {
        title: 'Dành cho',
        dataIndex: 'age',
        key: 'age',
        render : (item) => {
            if( item == 99){
                return <p>Dành cho tất cả lứa tuổi</p>
            }else{
                return <p>{item} tuổi</p>
            }
        }
    },
    {
        title: 'Ảnh',
        dataIndex: 'image',
        key: 'image',
        render : (text) => {
            return (
                <div className={'w-[50px] h-[80px]'}>
                    <img src={text} className={'w-full h-full'}/>
                </div>
            )
        }
    },
    {
        title: 'Loại',
        dataIndex: 'sub',
        key: 'sub',
        render: (item) => {
            return item.map( (s : Sub) => {
                return <div>{s.name}</div>
            })
        }
    },
    {
        title: 'Quốc gia',
        dataIndex: 'nation',
        key: 'nation',
    },
    {
        title: 'Thời lượng',
        dataIndex: 'duration',
        key: 'duration',
    },
    {
        title: 'Miêu tả',
        dataIndex: 'description',
        key: 'description',
        render: (text?: string) => {
            if (!text) return <p className="text-gray-400">Không có miêu tả</p>; // ✅ Xử lý khi text bị null hoặc undefined

            return (
                <p title={text}>
                    {text.length > 50 ? text.slice(0, 50) + '...' : text}
                </p>
            );
        }
    },
    {
        title: 'Nội dung',
        dataIndex: 'content',
        key: 'content',
        render: (text?: string) => {
            if (!text) return <p className="text-gray-400">Không có miêu tả</p>; // ✅ Xử lý khi text bị null hoặc undefined

            return (
                <p title={text}>
                    {text.length > 50 ? text.slice(0, 50) + '...' : text}
                </p>
            );
        }
    },
    {
        title: 'Thể loại',
        dataIndex: 'typeFilms',
        key: 'typeFilms',
        render: (item) => {
            return item.map( (s:TypeFilm) => {
                return <p>{s.name}</p>
            })
        }
    },
    {
        title: 'Trạng thái',
        dataIndex: 'status',
        key: 'status',
        render : (text) => {
            return <p className={`font-bold ${text == "ACTIVE" ? 'text-textAdmin' : `${text == 'COMMING_SOON' ? 'text-green-600' : 'text-red-500'}`}`}>{text}</p>
        }
    },
]
const initFilm = {
    id: '',
    name : '',
    age : 99,
    image : '',
    sub: [],
    nation : '',
    duration : '',
    description : '',
    content : '',
    trailer : '',
    typeFilms : [],
    status : 'COMMING_SOON'
}

const Film : React.FC = () => {

    const [loadingFilm, setLoadingFilm] = useState<boolean>(false);
    const [loadingSub, setLoadingSub] = useState<boolean>(false);
    const [loadingTypeFilm, setLoadingTypeFilm] = useState<boolean>(false);
    const [params, setParams] = useState<Custom>(initCustom);
    const [dataFilm, setDataFilm] = useState<Film[]>([]);
    const [dataSub, setDataSub] = useState<Sub[]>([]);
    const [dataTypeFilm, setDataTypeFilm] = useState<TypeFilm[]>([]);
    const [pageFilm, setPageFilm] = useState<Page>(initPage);
    const navigate = useNavigate();

    //search
    const [q, setQ] = useState<string>('');
    const [isModal, setIsModal] = useState<boolean>(false);
    const [dataModal, setDataModel] = useState<Film>(initFilm);
    const descriptionRef = useRef(null);
    const contentRef = useRef(null);
    const [active, setActive] = useState<string>("CREATE");

    //modal
    const [loadingModal, setLoadingModal] = useState<boolean>(false);

    //fetch data
    useEffect(() => {
        handleFetchFilm();
    }, [params, pageFilm]);

    useEffect(() => {
        handleFetchSub();
    }, []);

    useEffect(() => {
        handleFetchTypeFilm();
    }, []);

    const handleFetchFilm = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingFilm(true);
        const response = await getFilm(pageFilm.pageCurrent-1, params.limit, params.asc, params.status, params.orderBy, params.q, token);
        setLoadingFilm(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        console.log(123)
        setDataFilm(response.data.data.content);
        if(pageFilm.pageCurrent != response.data.data.pageable.pageNumber + 1 || pageFilm.pageTotal !=  response.data.data.totalPages){
            setPageFilm({
                pageCurrent : response.data.data.pageable.pageNumber + 1,
                pageTotal : response.data.data.totalPages
            });
        }
    }

    const handleFetchSub = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingSub(true);
        const response = await getSub();
        setLoadingSub(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setDataSub(response.data.data);
    }

    const handleFetchTypeFilm = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingTypeFilm(true);
        const response = await getTypeFilm();
        setLoadingTypeFilm(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setDataTypeFilm(response.data.data);
    }

    const handleSearch = () => {
        setParams({
            ...params,
            q : q
        })
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

    const handleName = (e) => {
        setDataModel({
            ...dataModal,
            name : e.target.value
        })
    }

    const handleAge = (e) => {
        setDataModel({
            ...dataModal,
            age : e.target.value
        })
    }

    const handleSub = (value) => {
        const subs : Sub[] = [];
        value.forEach( item => {
            const temp : Sub = dataSub.find(i => i.id == item);
            if( temp === undefined){
                toast.warning(<p className={'w-full'}>Đã xảy ra lỗi</p>)
                return
            }
            subs.push(temp);
        })
        setDataModel({
            ...dataModal,
            sub : subs
        })
    }

    const handleNation = (e) => {
        setDataModel({
            ...dataModal,
            nation : e.target.value
        })
    }

    const handleDuration = (e) => {
        setDataModel({
            ...dataModal,
            duration : e.target.value
        })
    }

    const handleTypeFilm = (value) => {
        const typeFilms : TypeFilm[] = [];
        value.forEach( item => {
            const temp : TypeFilm = dataTypeFilm.find(i => i.id == item);
            if( temp === undefined){
                toast.warning(<p className={'w-full'}>Đã xảy ra lỗi</p>)
                return
            }
            typeFilms.push(temp);
        })
        setDataModel({
            ...dataModal,
            typeFilms : typeFilms
        })
    }

    const handleTrailer = (e) => {
        setDataModel({
            ...dataModal,
            trailer : e.target.value
        })
    }

    const handleStatus = (e) => {
        setDataModel({
            ...dataModal,
            status : e.target.value
        })
    }

    const handleCreateFilm = async () => {
        const temp : Film = {
            ...dataModal,
            description : descriptionRef.current.getContent(),
            content : contentRef.current.getContent()
        };
        console.log(temp)
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingModal(true);
        const response = await createFilm(temp, token);
        setLoadingModal(false);
        console.log(response);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tạo được phim</p>)
            return;
        }else{
            setIsModal(false);
            setDataModel(initFilm);
            toast.success(<p className={'w-full'}>Tạo phim thành công</p>)
        }
    }

    const handleUpdateFilm = async () => {
        const temp : Film = {
            ...dataModal,
            description : descriptionRef.current.getContent(),
            content : contentRef.current.getContent()
        };

        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingModal(true);
        const response = await updateFilm(temp, token);
        setLoadingModal(false);
        console.log(response);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tạo được phim</p>)
            return;
        }else{
            setIsModal(false);
            setDataFilm(
                [dataModal,...dataFilm.filter(i=>i.id!=dataModal.id)]
            );
            setDataModel(initFilm);
            toast.success(<p className={'w-full'}>Cập nhật phim thành công</p>)
        }
    }

    const handleClickCreate = () =>{
        setIsModal(true);
        setActive("CREATE");
    }

    const handleClose = () => {
        setIsModal(false);
        setDataModel(initFilm);
    }

    const handleSearchStatus = (e) => {
        setParams({
            ...params,
            status : e.target.value
        })
    }

    // upload
    const props: UploadProps = {
        name: 'image',
        action: 'http://localhost:8080/upload',
        maxCount : 1,
        onChange(info) {
            if (info.file.status !== 'uploading') {
            }
            if (info.file.status === 'done') {
                setDataModel({
                    ...dataModal,
                    image : info.file.response
                })
            } else if (info.file.status === 'error') {
                toast.error(<p className={'w-full'}>Upload hình ảnh thất bại</p>)
            }
        },
        onRemove(){
            setDataModel({
                ...dataModal,
                image : ''
            })
        }
    };

    return (
        <>
            <div className={'bg-white p-[20px] rounded-xl'}>
                <div className={'mb-[20px]'}>
                    <div className={'flex gap-2 justify-end'}>

                        <button onClick={() => handleClickCreate()}
                                className={'flex gap-2 items-center border-[1px] border-textAdmin bg-textAdmin text-white px-2 py-1'}>
                            Tạo Phim
                        </button>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Trạng thái :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'none'}
                                    onChange={(e) => handleSearchStatus(e)}>
                                <option value={'none'}>Tất cả</option>
                                <option value={'COMMING_SOON'}>Sắp ra mắt</option>
                                <option value={'ACTIVE'}>Đã ra mắt</option>
                                <option value={'DELETE'}>Đã xoa</option>
                            </select>
                        </div>
                        <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                            <p>Sắp xếp theo :</p>
                            <select className={'bg-transparent outline-0'} defaultValue={'name'}
                                    onChange={(e) => handleOrder(e.target.value)}>
                                <option value={'Id'}>Id</option>
                                <option value={'name'}>Tên</option>
                                <option value={'age'}>Tuổi</option>
                                <option value={'nation'}>Quốc gia</option>
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
                        <div className={'bg-white px-2 py-1 border-textAdmin border-[1px]'}>
                            <input className={'px-2 outline-0'} value={q} placeholder={'Nhập tìm kiếm...'}
                                   onChange={(e) => setQ(e.target.value)}/>
                            <button className={'text-xl'} onClick={() => handleSearch()}><FaSearch/></button>
                        </div>
                    </div>
                </div>
                <Table<Film> columns={columns}
                             dataSource={dataFilm}
                             pagination={false}
                             loading={loadingFilm}
                             onRow={(record) => ({
                                 onClick: () => {
                                     setDataModel(record);
                                     setIsModal(true);
                                     setActive("UPDATE");
                                 }
                             })}
                             scroll={{x: 'max-content'}}
                />
                <div className={'mt-[20px]'}>
                    <Pagination pageSize={10} align={"center"} total={pageFilm.pageTotal * 10}
                                defaultCurrent={pageFilm.pageCurrent}
                                onChange={(e) => setPageFilm({...pageFilm, pageCurrent: e})}/>
                </div>
            </div>
            <Modal
                title={<p className={'text-main text-xl uppercase font-bold'}>Phim</p>}
                open={isModal}
                loading={loadingModal}
                onCancel={() => handleClose()}
                footer={
                    active == 'CREATE' ?
                        <>
                            <button onClick={() => handleCreateFilm()}
                                    className={'bg-main min-w-[150px] px-4 py-2 text-[14px] text-white font-bold uppercase'}>Tạo phim</button>
                        </>
                        :
                        <>
                            <button onClick={() => handleUpdateFilm()}
                                    className={'bg-main min-w-[150px] px-4 py-2 text-[14px] text-white font-bold uppercase'}>Cập nhật
                            </button>
                        </>
                    }
                width={1000}
            >
                <div className={'flex gap-4 flex-col mt-[20px]'}>
                    <div className={'flex flex-col'}>
                        <label className={'text-main'}>Tên phim <span className={'text-red-500'}>*</span></label>
                        <input value={dataModal.name} onChange={(e) => handleName(e)} className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                    <div className={'flex justify-between items-end'}>
                        <div className={'flex gap-2'}>
                            {
                                dataModal.image != '' && <img src={dataModal.image} className={'w-[50px] h-[80px]'}/>
                            }
                            <Upload {...props}>
                                <Button icon={<UploadOutlined/>}>Upload Image</Button>
                            </Upload>
                        </div>
                        <div className={'flex items-center gap-2'}>
                            <p className={'text-main'}>Dành cho :</p>
                            <select className={'outline-0'} defaultValue={dataModal.age} onChange={(e) => handleAge(e)}>
                                <option value={13}>trên 13 tuổi</option>
                                <option value={18}>trên 18 tuổi</option>
                                <option value={99}>Dành cho tất cả mọi người</option>
                            </select>
                        </div>
                    </div>
                    <div>
                        <p className={'text-main'}>Loại <span className={'text-red-500'}>*</span></p>
                        <Spin tip={'Đang tải...'} spinning={loadingSub}>
                            <Select
                                mode="multiple"
                                size="middle"
                                placeholder=""
                                value={dataModal?.sub?.map(item => item.id) || []}
                                onChange={(value) => handleSub(value)}
                                style={{ width: '100%' }}
                                options={dataSub.map(item => ({
                                    value: item.id,
                                    label: item.name
                                }))}
                            />

                        </Spin>
                    </div>
                    <div className={'flex justify-between gap-4'}>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>Quốc gia <span className={'text-red-500'}>*</span></label>
                            <input value={dataModal.nation} onChange={(e)=>handleNation(e)} className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>Thời lượng <span className={'text-red-500'}>*</span></label>
                            <input value={dataModal.duration} onChange={(e)=>handleDuration(e)} className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                    </div>
                    <div>
                        <label className={'text-main'}>Mô tả <span className={'text-red-500'}>*</span></label>
                        <Editor apiKey='qgdph0wy2uwgtvg05zj7e485k08kkai6tdvtwdyftkw2q6n1'
                                onInit={(_evt, editor) => descriptionRef.current = editor}
                                initialValue={dataModal.description}
                                init={{
                                    height: 300,
                                    menubar: false,
                                    plugins: [
                                        'advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview',
                                        'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
                                        'insertdatetime', 'media', 'table', 'code', 'help', 'wordcount'
                                    ],
                                    toolbar: 'undo redo | blocks | ' +
                                        'bold italic forecolor | alignleft aligncenter ' +
                                        'alignright alignjustify | bullist numlist outdent indent | ' +
                                        'removeformat | help',
                                    content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
                                }}
                        />
                    </div>
                    <div>
                        <label className={'text-main'}>Nội dung <span className={'text-red-500'}>*</span></label>
                        <Editor apiKey='qgdph0wy2uwgtvg05zj7e485k08kkai6tdvtwdyftkw2q6n1'
                                onInit={(_evt, editor) => contentRef.current = editor}
                                initialValue={dataModal.content}
                                init={{
                                    height: 300,
                                    menubar: false,
                                    plugins: [
                                        'advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview',
                                        'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
                                        'insertdatetime', 'media', 'table', 'code', 'help', 'wordcount'
                                    ],
                                    toolbar: 'undo redo | blocks | ' +
                                        'bold italic forecolor | alignleft aligncenter ' +
                                        'alignright alignjustify | bullist numlist outdent indent | ' +
                                        'removeformat | help',
                                    content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
                                }}
                        />
                    </div>
                    <div>
                        <p className={'text-main'}>Thể loại <span className={'text-red-500'}>*</span></p>
                        <Spin tip={'Đang tải...'} spinning={loadingTypeFilm}>
                            <Select
                                mode="multiple"
                                size={'middle'}
                                placeholder=""
                                value={dataModal?.typeFilms?.map(item => item.id) || []}
                                onChange={(value) => handleTypeFilm(value)}
                                style={{width: '100%'}}
                                options={dataTypeFilm.map(item => {
                                    return {
                                        value: item.id,
                                        label: item.name
                                    }
                                })}
                            />
                        </Spin>
                    </div>
                    <div className={'flex justify-between items-end gap-4'}>
                        <div className={'flex flex-col flex-1'}>
                            <label className={'text-main'}>Link trailer <span
                                className={'text-red-500'}>*</span></label>
                            <input value={dataModal.trailer} onChange={(e)=>handleTrailer(e)} className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                        </div>
                        <div className={'flex items-center gap-2'}>
                            <p className={'text-main'}>Trạng thái :</p>
                            <select className={'outline-0'} defaultValue={dataModal.status} onChange={(e)=>handleStatus(e)}>
                                <option value={'COMMING_SOON'}>Sắp ra mắt</option>
                                <option value={'ACTIVE'}>Đã ra mắt</option>
                                <option value={'DELETE'}>Đã xóa</option>
                            </select>
                        </div>
                    </div>
                </div>
            </Modal>
        </>
    )
}
export default Film;