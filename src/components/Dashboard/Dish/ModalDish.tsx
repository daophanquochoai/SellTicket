import React, {useEffect, useState} from "react";
import {Button, Modal, Table, Upload, type UploadProps} from "antd";
import {FaSearch} from "react-icons/fa";
import {UploadOutlined} from "@ant-design/icons";
import {toast} from "react-toastify";
import {createDish, expireToken, getToken, updateDish, updateTypeDish} from "../../../Helper/Helper.ts";
import {useNavigate} from "react-router-dom";

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
interface Props {
    isModal : boolean,
    setIsModal : (arg :boolean) => void,
    typeDish : TypeDish,
    dataTypeFilm : TypeDish[],
    setDataTypeFilm : (arg:TypeDish[]) =>void,
    setTypeDishSelected : (arg:TypeDish) => void
}

const initDish = {
    id : '',
    price : 0,
    active : 'ACTIVE',
    name : '',
    image : ''
}

const columns = [
    {
        title: 'Id',
            dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Tên món ăn',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Hình ảnh',
        dataIndex: 'image',
        key: 'image',
        render : (text)=>{
            if( text == '' || text == null ) {
                return (
                    <div className={'w-[60px] h-[80px] border-2 border-textAdmin'}></div>
                )
            }
            return(
                <div>
                    <img src={text} className={'w-[60px] h-[80px]'}/>
                </div>
            )
        }
    },
    {
        title: 'Giá',
        dataIndex: 'price',
        key: 'price',
    },
    {
        title: 'Trạng thái',
        dataIndex: 'active',
        key: 'active',
        render : (text)=> {
            if( text == 'ACTIVE'){
                return <p className={'text-green-600'}>{text}</p>
            }else{
                return <p className={'text-red-600'}>{text}</p>
            }
        }
    },
]
const ModalDish : React.FC<Props> = ( props ) => {

    const {isModal,setIsModal,typeDish, setDataTypeFilm, dataTypeFilm,setTypeDishSelected} = props;
    const navigate = useNavigate();
    //search
    const [q, setQ] = useState<string>('');
    const [data, setData] = useState<Dish[]>();
    //select
    const [dishSelected, setDishSelected] = useState<Dish>(initDish);
    const [isOpenDish, setIsOpenDish] = useState<boolean>(false);
    const [loadingDish, setLoadingDish] = useState<boolean>(false);
    const [active, setActive] = useState<string>('CREATE');

    useEffect(() => {
        setData(typeDish.dishes);
    }, [typeDish]);

    useEffect(() => {
        console.log(data);
    }, [data]);

    const handleSearch = () => {
        setData(typeDish.dishes.filter(i=> i.name.toLowerCase().includes(q.toLowerCase())));
    }

    const handleClode = () => {
        setIsModal(false)
    }

    const handleCancelDish = () => {
        setIsOpenDish(false);
    }

    const handleUpdateDish = async () => {
        if( dishSelected.price < 1000){
            toast.warning(<p className={'w-full'}>Giá nên lớn hơn 1.000đ</p>)
            return;
        }
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingDish(true);
        const response = await updateDish(dishSelected.id,dishSelected.price,dishSelected.active,dishSelected.name,dishSelected.image,typeDish.id,token);
        setLoadingDish(false);
        console.log(response);
        if( response.status != 200){
            toast.warning(<p className={'w-full'}>Không thể cập nhật dữ liệu</p>)
            return;
        }
        setIsOpenDish(false);
        setDataTypeFilm(dataTypeFilm.map(i => {
            if (i.id === typeDish.id) {
                return {
                    ...i,
                    dishes: i.dishes.map(d => d.id === dishSelected.id ? response.data.data : d)
                };
            }
            return i;
        }));
        setDishSelected(initDish);
        toast.success(<p className={'w-full'}>Cập nhật dữ liệu thành công</p>)
    }

    const handleClickCreate = () => {
        setActive("CREATE");
        setDishSelected(initDish);
        setIsOpenDish(true);
    }

    const handleCreateDish = async () => {
        if( dishSelected.price < 1000){
            toast.warning(<p className={'w-full'}>Giá nên lớn hơn 1.000đ</p>)
            return;
        }
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingDish(true);
        const response = await createDish(dishSelected.id,dishSelected.price,dishSelected.active,dishSelected.name,dishSelected.image,typeDish.id, token);
        setLoadingDish(false);
        if( response.status != 201){
            toast.warning(<p className={'w-full'}>Không thể tạo món ăn</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Tạo món ăn thành công</p>)
        setDataTypeFilm(dataTypeFilm.map(i => {
            if (i.id === typeDish.id) {
                return {
                    ...i,
                    dishes: [...i.dishes, response.data.data]
                };
            }
            return i;
        }));
        setIsOpenDish(false);
    }

    const handleUpdateTypeDish = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingDish(true);
        const response = await updateTypeDish(typeDish.id,typeDish.active,typeDish.name, token);
        setLoadingDish(false);
        console.log(response);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể cập nhật dữ liệu</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Cập nhật loại món ăn thành công</p>)
        setDataTypeFilm(dataTypeFilm.map(i => {
            if (i.id === typeDish.id) {
                return {
                    ...i,
                    id : typeDish.id,
                    active : typeDish.active,
                    name : typeDish.name
                };
            }
            return i;
        }));
        setIsOpenDish(false);
    }
    // upload
    const propsUpload: UploadProps = {
        name: 'image',
        action: 'http://localhost:8080/upload',
        maxCount : 1,
        onChange(info) {
            if (info.file.status !== 'uploading') {
            }
            if (info.file.status === 'done') {
                setDishSelected({
                    ...dishSelected,
                    image : info.file.response
                })
            } else if (info.file.status === 'error') {
                toast.error(<p className={'w-full'}>Upload hình ảnh thất bại</p>)
            }
        },
        onRemove(){
            setDishSelected({
                ...dishSelected,
                image : ''
            })
        }
    };

    return (
        <>
            <Modal
                title={<p className={'text-main text-xl uppercase font-bold'}>Đồ Ăn</p>}
                open={isModal}
                loading={loadingDish}
                onCancel={() => handleClode()}
                width={1000}
                footer={[]}
            >
                <div className={'flex items-start gap-4 mb-[10px]'}>
                    <div className={'flex-1 flex flex-col gap-4'}>
                        <div className={'flex flex-col gap-2'}>
                            <label>Id <span className={'text-red-700'}>*</span></label>
                            <input value={typeDish.id} className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                                   disabled={true}/>
                        </div>
                        <div className={'flex flex-col gap-2'}>
                            <label>Tên loại<span className={'text-red-700'}>*</span></label>
                            <input value={typeDish.name}
                                   onChange={(e)=>setTypeDishSelected({...typeDish, name : e.target.value})}
                                   className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                                   required={true}/>
                        </div>
                    </div>
                    <div className={'flex-1 flex-col justify-between gap-4 flex'}>
                        <div className={'flex flex-col gap-2'}>
                            <label>Trạng thái <span className={'text-red-700'}>*</span></label>
                            <select value={dishSelected.active}
                                    onChange={(e) => setTypeDishSelected({...typeDish, active: e.target.value})}>
                                <option value={'ACTIVE'}>Còn kinh doanh</option>
                                <option value={'DELETE'}>Ngừng kinh doanh</option>
                            </select>
                        </div>
                        <div className={'flex justify-end'}>
                            <button
                                onClick={()=> handleUpdateTypeDish()}
                                className={'bg-main px-2 py-1 text-white'}>Cập nhật</button>
                        </div>
                    </div>
                </div>
                <div className={'w-full h-[1px] border-2 border-textAdmin border-dashed my-[20px]'}></div>
                <div className={'flex justify-between items-center mb-[10px] gap-2'}>
                    <div>
                        <p className={'font-bold text-xl uppercase'}>Danh sách món ăn</p>
                    </div>
                    <div className={'flex gap-4'}>
                        <div>
                            <button
                                onClick={() => handleClickCreate()}
                                className={'px-2 py-1 bg-textAdmin border-textAdmin border-2 text-white'}>Tạo món ăn
                            </button>
                        </div>
                        <div>
                            <div className={'border-textAdmin border-2 px-2 py-1 flex items-center'}>
                                <input className={'outline-0'} placeholder={"Nhập để tìm kiếm..."} value={q}
                                       onChange={e => setQ(e.target.value)}/>
                                <button className={'text-xl'}
                                        onClick={() => handleSearch()}
                                ><FaSearch/></button>
                            </div>
                        </div>
                    </div>
                </div>
                <Table<Dish> columns={columns}
                             dataSource={data}
                             pagination={false}
                             loading={loadingDish}
                             onRow={(record) => ({
                                 onClick: () => {
                                     setIsOpenDish(true);
                                     setDishSelected(record);
                                     setActive('UPDATE')
                                 }
                             })}
                             scroll={{x: 'max-content'}}
                />
            </Modal>
            <Modal
                title={<p className={'text-main text-xl font-bold uppercase'}>Đồ ăn</p>}
                loading={loadingDish}
                open={isOpenDish}
                onCancel={() => handleCancelDish()}
                footer={
                    active == 'CREATE' ?
                        <div>
                            <button
                                onClick={() => handleCreateDish()}
                                className={'px-4 py-2 text-white bg-main'}
                            >Tạo
                            </button>
                        </div>
                        :
                        <div>
                            <button
                                onClick={() => handleUpdateDish()}
                                className={'px-4 py-2 text-white bg-main'}
                            >Cập nhật</button>
                        </div>
                }
            >
                <div className={'flex flex-col gap-4'}>
                    <div className={'flex flex-col gap-2'}>
                        <label>Id <span className={'text-red-700'}>*</span></label>
                        <input value={dishSelected.id} className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'} disabled={true}/>
                    </div>
                    <div className={'flex flex-col gap-2'}>
                        <label>Tên <span className={'text-red-700'}>*</span></label>
                        <input value={dishSelected.name}
                               required={true}
                               className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                               onChange={(e) => setDishSelected({...dishSelected, name: e.target.value})}/>
                    </div>
                    <div className={'flex flex-col gap-2'}>
                        <label>Giá <span className={'text-red-700'}>*</span></label>
                        <input value={dishSelected.price} type={"number"}
                               required={true}
                               className={'px-2 py-1 outline-0 border-[1px] border-textAdmin'}
                               onChange={(e) => setDishSelected({...dishSelected, price: parseInt(e.target.value)})}/>
                    </div>
                    <div className={'flex gap-2'}>
                        {
                            dishSelected.image != '' && <img src={dishSelected.image} className={'w-[50px] h-[80px]'}/>
                        }
                        <Upload {...propsUpload}>
                            <Button icon={<UploadOutlined/>}>Upload Image</Button>
                        </Upload>
                    </div>
                    <div>
                        <label>Trạng thái :</label>
                        <select value={dishSelected.active}
                                onChange={(e) => setDishSelected({...dishSelected, active: e.target.value})}>
                            <option value={'ACTIVE'}>Còn kinh doanh</option>
                            <option value={'DELETE'}>Ngừng kinh doanh</option>
                        </select>
                    </div>
                </div>
            </Modal>
        </>
    )
}
export default ModalDish;