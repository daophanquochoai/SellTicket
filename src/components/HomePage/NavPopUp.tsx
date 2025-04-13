import React, {lazy, useEffect, useState} from "react";
import {CloseOutlined} from "@ant-design/icons";
import {getBillsByCustomer, removeToken} from "../../Helper/Helper.ts";
import {useNavigate} from "react-router-dom";
import {useCommonContext} from "../../context/CommonContext.tsx";
import { motion } from "framer-motion";
import Info from "../Info/Info.tsx";
import {Modal, Table} from "antd";
import {
    useQuery,
} from '@tanstack/react-query'
import Bill from "../Dashboard/Bill/Bill.tsx";
import {FaSearch} from "react-icons/fa";

const ModalBill = lazy(()=> import("./../Dashboard/Bill/ModalBill.tsx"));


interface NavPropUps {
    propUp : boolean;
    setPropUp : (arg : boolean) => void
}
const initBill : Bill = {
    id : '',
    totalPrice : 0,
    transactionCode : "",
    paymentMethodId : "",
    paymentMethod : "",
    active : "",
    chairs : [],
    dishes: [],
    timestamp : "",
    status : "",
    filmShowTimeId : 0,
    timeEnd : "",
    timeStart : "",
    timeStampSee : "",
    roomId : "",
    nameRoom : "",
    nameBranch : "",
    address : "",
    filmId : "",
    nameFilm : "",
    userName : "",
    email : "",
    numberPhone : "",
    qrCode : ""
}
const NavPopUp : React.FC<NavPropUps> = ({propUp, setPropUp}) => {

    //modal
    const [openModal, setOpenModal] = useState<boolean>(false);
    const [billSelected, setBillSeleted] = useState<Bill>(initBill);
    const [search, setSearch] = useState<string>("");

    const colums = [
        {
            title: 'Xuất chiếu',
            key: 'showTime',
            render : (text)=>(
                <p className={'text-purple-600 font-bold'}>{text.nameFilm}</p>
            )
        },
        {
            title: 'Phương thức thanh toán',
            dataIndex: 'paymentMethod',
            key: 'paymentMethod',
            render : (text)=>(
                <p className={'text-blue-400 font-bold'}>{text}</p>
            )
        },
        {
            title: 'Người dùng',
            dataIndex: 'userName',
            key: 'userName',
        },
        {
            title: 'Ngày giao dịch',
            dataIndex: 'timestamp',
            key: 'timestamp',
        },
        {
            title: 'QRcode',
            dataIndex: 'qrCode',
            key: 'qrCode',
            render : (text : string) => (
                <img  alt={'qrcode'} src={text} className={'w-[50px] h-[50px]'}/>
            )
        },
        {
            title: 'Trạng thái',
            dataIndex: 'status',
            key: 'status',
            render : (text) => (
                text == 'SUCCESS' ?
                    <p className={'text-green-600 font-bold'}>Thành công</p>
                    :
                    <p className={'text-red-700 font-bold'}>Thất bại</p>
            )
        },
    ]

    const navigation = useNavigate();
    const {isLogin,setLogin, info} = useCommonContext();
    const [open, setOpen] = useState<boolean>(true);
    const [openBill, setOpenBill] = useState<boolean>(false);


    const queryBillById = useQuery({queryKey : ['billById'], queryFn: ()=> getBillsByCustomer(info?.id)})

    const handleLogout = () => {
        removeToken();
        if (setLogin) {
            setLogin(false);
        }
        navigation('/login');
    }

    return (
        <>
            <div
                className={`${propUp ? 'w-[300px]' : 'w-0'} h-[100vh] bg-main absolute top-0 right-0 z-10 transition-all duration-500`}>
                <div
                    className={`${propUp ? 'block' : 'hidden'} transition-all duration-700`}>
                    <div className={'flex justify-start px-4 py-2'}>
                        <div className={'cursor-pointer text-[22px] text-white hover:text-black'}
                             onClick={() => setPropUp(false)}><CloseOutlined/></div>
                    </div>
                    {
                        isLogin &&
                            <>
                                <motion.div
                                    initial={{height: 120}}
                                    animate={{height: open  ? 170 : 0}}
                                    transition={{duration: 0.2, ease: "easeInOut"}}
                                    className={'overflow-hidden'}
                                >
                                    <ul className={'mt-4'}>
                                        <li
                                            onClick={() => setOpen(false)}
                                            className={'flex text-white px-4 py-2 justify-center hover:text-black hover:bg-white border-2 border-main cursor-pointer duration-150'}>
                                            <p className={'text-[18px]'}>Thông tin người dùng</p>
                                        </li>
                                        <li
                                            onClick={() => {
                                                setOpenBill(true);
                                                setSearch("");
                                            }}
                                            className={'flex text-white px-4 py-2 justify-center hover:text-black hover:bg-white border-2 border-main cursor-pointer duration-150'}>
                                            <p className={'text-[18px]'}>Lịch sử mua vé</p>
                                        </li>
                                        <li className={'flex text-white px-4 py-2 justify-center hover:text-black hover:bg-white border-2 border-main cursor-pointer duration-150'}
                                            onClick={() => handleLogout()}>
                                            <p className={'text-[18px]'}>Đăng xuất</p>
                                        </li>
                                    </ul>
                                </motion.div>
                            </>
                    }
                </div>
                {
                    !open &&
                    <Info setOpen={setOpen}/>
                }
                <Modal
                    open={openBill}
                    onCancel={()=>{
                        setOpenBill(false);
                    }}
                    title={<p className={'text-main text-2xl uppercase font-bold'}>Danh sách vé đặt</p>}
                    footer={[]}
                    width={1000}
                >
                    <div className={'flex justify-end'}>
                        <div className={'bg-white px-2 py-1 border-textAdmin border-[1px] flex items-center'}>
                            <input className={'px-2 outline-0'} placeholder={'Nhập tên phim...'}
                                   value={search}
                                   onChange={(e) => setSearch(e.target.value)}
                            />
                            <button className={'text-xl'}><FaSearch/></button>
                        </div>
                    </div>
                    <Table<Bill>
                        pagination={false}
                        columns={colums}
                        loading={queryBillById.isLoading}
                        rowKey={col => col.id}
                        scroll={{x: 'max-content', y: 400}}
                        dataSource={!queryBillById ? [] : queryBillById.data?.data?.data.filter(i=>i.nameFilm.normalize("NFD").replace(/[\u0300-\u036f]/g, "") .toLowerCase().includes(search.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "") ))}
                        onRow={(record) => ({
                            onClick: () => {
                                setBillSeleted(record);
                                setOpenModal(true);
                            }
                        })}
                    />
                </Modal>
            </div>
            <ModalBill
                isOpen={openModal}
                setIsOpen={setOpenModal}
                data={billSelected}
            />
        </>
    )
}
export default NavPopUp;