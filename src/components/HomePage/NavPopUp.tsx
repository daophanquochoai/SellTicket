import React, {useEffect, useState} from "react";
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


interface NavPropUps {
    propUp : boolean;
    setPropUp : (arg : boolean) => void
}

const NavPopUp : React.FC<NavPropUps> = ({propUp, setPropUp}) => {

    const colums = [
        {
            title: 'Mã giao dịch',
            dataIndex: 'transactionCode',
            key: 'transactionCode',
            render : (text)=>(
                <p className={'text-purple-600 font-bold'}>{text}</p>
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

    useEffect(() => {
        console.log(queryBillById.data?.data?.data);
    }, [queryBillById.data]);

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
                                            onClick={() => setOpenBill(true)}
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
                    footer={[]}
                    width={1000}
                >
                    <Table<Bill>
                        pagination={false}
                        columns={colums}
                        loading={queryBillById.isLoading}
                        rowKey={col => col.id}
                        scroll={{ x: 'max-content', y: 400 }}
                        dataSource={!queryBillById ? [] : queryBillById.data?.data?.data}
                        onRow={(record) => ({
                            onClick: () => {
                            }
                        })}
                    />
                </Modal>
            </div>
        </>
    )
}
export default NavPopUp;