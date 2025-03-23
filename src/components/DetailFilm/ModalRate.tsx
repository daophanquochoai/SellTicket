import React, {useEffect, useState} from "react";
import {Modal, Pagination} from "antd";
import {FaSearch} from "react-icons/fa";
import {expireToken, fetchCommentByFull, getToken, uploadComment} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {Rate} from "antd";
import {useCommonContext} from "../../context/CommonContext.tsx";
import {useNavigate} from "react-router-dom";

interface RateCommon {
    comments : Rate[],
    rate : number
}
interface Rate {
    id : string,
    star : number,
    content : string,
    timeStamp : string,
    customer : Customer,
}
interface Customer {
    name : string,
    phoneNumber : string,
    email : string,
    userName : string,
    password : string,
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
    orderBy : 'timeStamp',
    q : ''
}
interface Props {
    isOpen : boolean,
    setIsOpen : (arg:boolean)=>void,
    filmId : string
}
interface Content {
    star : number,
    content : string,
    customerId : string,
    filmId : string,
}
const initContent : Content = {
    star : 5,
    content : '',
    customerId : '',
    filmId : ''
}
const initRateCommon = {
    comments : [],
    rate : 0,
}

const ModalRate : React.FC<Props> = (props) => {

    //context
    const {isLogin, info} = useCommonContext();
    const navigate = useNavigate();
    //modal
    const {isOpen, setIsOpen, filmId} = props;
    const [param, setParam] = useState<Custom>(initCustom);
    const [page, setPage] = useState<Page>(initPage);
    const [loading, setLoading] = useState<boolean>(false);
    const [search, setSearch] = useState<string>('');
    const [data, setData] = useState<RateCommon>(initRateCommon);

    //upload
    const [content, setContent] = useState<Content>(initContent);
    const [reload, setReload] = useState<boolean>(false);

    useEffect(() => {
        setContent({
            ...content,
            customerId : info?.id,
            filmId : filmId
        })
    }, [filmId,info]);

    const handleCancel = () => {
        setIsOpen(false);
    }

    useEffect(() => {
        handleFetchRate()
    }, [param, page, reload]);

    const handleFetchRate = async () => {
        setLoading(true);
        const response = await fetchCommentByFull(filmId,page.pageCurrent-1,param.limit,param.asc,param.orderBy,param.q);
        setLoading(false);
        console.log(response);
        if( response.status != 200) {
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu</p>)
            return;
        }
        setData(response.data.data.data);
        if( page.pageCurrent != response.data.data.pageCurrent || page.pageTotal != response.data.data.totalPage){
            setPage({
                pageCurrent : response.data.data.pageCurrent + 1,
                pageTotal : response.data.data.totalPage
            })
        }

    }

    const handleUploadComment = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await uploadComment(content.star,content.content,content.customerId,content.filmId,token);
        console.log(response)
        setLoading(false);
        if( response.status != 201){
            toast.error(<p className={'w-full'}>Không thể đăng bình luận</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Đăng tải thành công</p>)
        setContent(initContent);
        setReload(!reload);
    }
    return (
        <>
            <Modal
                loading={loading}
                title={<p className={'text-main font-bold text-xl uppercase'}>Đánh giá người xem</p>}
                open={isOpen}
                onCancel={() => handleCancel()}
                width={1000}
                footer={[]}
            >
                <div>
                    <div>
                        <div className={'flex gap-2 justify-end'}>
                            <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                                <p>Sắp xếp theo :</p>
                                <select className={'bg-transparent outline-0'} defaultValue={'name'}
                                        onChange={(e) => setParam({...param, orderBy: e.target.value})}>
                                    <option value={'timeStamp'}>Ngày đánh giá</option>
                                    <option value={'star'}>Đánh giá</option>
                                </select>
                            </div>
                            <div className={'flex gap-2 items-center border-[1px] border-textAdmin bg-white px-2 py-1'}>
                                <p>Thứ tự xếp :</p>
                                <select className={'bg-transparent outline-0'} defaultValue={'asc'}
                                        onChange={(e) => setParam({...param, asc: e.target.value})}>
                                    <option value={'asc'}>Tăng dần</option>
                                    <option value={'des'}>Giảm dần</option>
                                </select>
                            </div>
                            <div className={'bg-white px-2 py-1 border-textAdmin border-[1px]'}>
                                <input className={'px-2 outline-0'} value={search} placeholder={'Nhập tìm kiếm...'}
                                       onChange={(e) => setSearch(e.target.value)}/>
                                <button className={'text-xl'} onClick={() => setParam({...param, q: search})}>
                                    <FaSearch/></button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={'mt-[20px]'}>
                    {
                        data && data.comments.map( item => {
                            return (
                                <div
                                    key={item.id}
                                    className={'flex justify-between items-center px-[40px] border-[1px] border-dashed border-textAdmin py-[10px]'}>
                                    <div>
                                        <div>
                                            <Rate className={'text-xs'} value={5} disabled/>
                                        </div>
                                        <div>
                                            <p className={'text-main text-[16px]'}>{item.content}</p>
                                        </div>
                                        <div>
                                            <p className={'text-xs text-white'}>{item.timeStamp}</p>
                                        </div>
                                    </div>
                                    <div>
                                        <p className={'text-border'}>{item.customer.name}</p>
                                    </div>
                                </div>
                            )
                        })
                    }
                    {
                        data.comments.length === 0 &&
                        <div className={'flex justify-center'}>
                            <p>Chưa có bình luận nào?</p>
                        </div>
                    }
                </div>
                <div className={'mt-[20px]'}>
                    <Pagination pageSize={10} align={"center"} total={page.pageTotal * 10}
                                defaultCurrent={page.pageCurrent}
                                onChange={(e) => setPage({...page, pageCurrent: e})}/>
                </div>
                {
                    isLogin && info?.roles[0] == 'ROLE_USER' &&
                    <div className={'mt-[20px] border-[1px] p-4 border-textAdmin'}>
                        <div className={'flex items-end gap-2'}>
                            <p className={'uppercase font-bold'}>Đánh giá :</p>
                            <Rate value={content.star} onChange={(e)=> setContent({...content, star : e})}/>
                        </div>
                        <div className={'flex items-end gap-2'}>
                            <textarea className={'flex-1 border-textAdmin border-[1px] outline-0 px-4 py-2'}
                                      placeholder={"Nhập binh luận..."}
                                      value={content.content}
                                      onChange={(e)=>setContent({...content,content : e.target.value})}
                            />
                            <div>
                                <button onClick={() => handleUploadComment()} className={'bg-main px-4 py-2 text-white'}>Đăng tải</button>
                            </div>
                        </div>
                    </div>
                }
            </Modal>

        </>
    )
}
export default ModalRate;