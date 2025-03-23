import React, {lazy, useState} from 'react';
import {
    MenuFoldOutlined,
    MenuUnfoldOutlined,
} from '@ant-design/icons';
import { Button, Layout, Menu, theme } from 'antd';
const { Header, Sider, Content } = Layout;
import './style.css';
import {FaCalendarAlt, FaChartLine} from "react-icons/fa";
import {BsPersonVideo2} from "react-icons/bs";
import {PiFilmSlateDuotone} from "react-icons/pi";
import {GiFilmSpool, GiTheater} from "react-icons/gi";
import {MdConnectWithoutContact, MdOutlineRateReview} from "react-icons/md";
import {BiSolidDish} from "react-icons/bi";
import {FaMoneyBillTrendUp} from "react-icons/fa6";
import {IoSettingsSharp} from "react-icons/io5";
import {useCommonContext} from "../../context/CommonContext.tsx";

const Setting = lazy(()=> import('./Setting/Setting.tsx'));
const ShowTime = lazy(()=>import('./ShowTime/ShowTime.tsx'));
const Bill = lazy(()=>import('./Bill/Bill.tsx'));
const Dish = lazy(()=>import('./Dish/Dish.tsx'));
const Film = lazy(()=>import('./Film.tsx'));
const OverView = lazy(() => import('./OverView.tsx'));
const Staff = lazy(() => import('./Staff.tsx'));
const Theater = lazy(() => import('./Theater.tsx'));
const Rate = lazy(()=>import('./Rate/Rate.tsx'));
const Sub = lazy(()=> import("./Sub/Sub.tsx"));
const NavAdmin = lazy(()=>import("./NavAdmin.tsx"));
const Contact = lazy(()=> import("./Contact/Contact.tsx"));

const CommonDash : React.FC = () => {
    const [collapsed, setCollapsed] = useState(false);
    const {info} = useCommonContext();
    const [select, setSelect] = useState<string[]>(['1'])
    const [navOpen, setNavOpen] = useState<boolean>(false);

    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();
    return (
        <>
            <Layout className={'w-full h-dvh'}>
                <Sider trigger={null} collapsible collapsed={collapsed}>
                    <div className="demo-logo-vertical]"/>
                    <div className={'flex justify-center items-center mt-[20px]'}>
                        {
                            !collapsed
                                ?
                                <p className={'text-[35px] font-bold text-main'}>Movie<span
                                    className={'text-[30px] text-textCol'}>Ticket</span></p>
                                :
                                <p className={'text-[25px] font-bold text-main'}>M<span
                                    className={'text-[20px] text-textCol'}>T</span></p>

                        }
                    </div>
                    <Menu
                        theme="dark"
                        mode="inline"
                        className={'mt-[30px]'}
                        selectedKeys={select}
                        onClick={(e) => setSelect(e.keyPath)}
                        items={[
                            {
                                key: '1',
                                icon: <FaChartLine />,
                                label: 'Tổng quan',
                            },
                            {
                                key: '2',
                                icon: <BsPersonVideo2 />,
                                label: 'Nhân viên',
                            },
                            {
                                key: '3',
                                icon: <PiFilmSlateDuotone />,
                                label: 'Phim',
                            },
                            {
                                key: '4',
                                icon: <GiFilmSpool />,
                                label: 'Xuất chiếu',
                            },
                            {
                                key: '5',
                                icon: <FaCalendarAlt />,
                                label: 'Lịch chiếu',
                            },
                            {
                                key: '6',
                                icon: <GiTheater />,
                                label: 'Rạp chiếu',
                            },
                            {
                                key: '7',
                                icon: <BiSolidDish />,
                                label: 'Đồ ăn / vé',
                            },
                            {
                                key: '8',
                                icon: <FaMoneyBillTrendUp />,
                                label: 'Hóa đơn',
                            },
                            {
                                key: '9',
                                icon: <MdOutlineRateReview />,
                                label: 'Đánh giá',
                            },
                            {
                                key: '10',
                                icon: <MdConnectWithoutContact />,
                                label: 'Hợp tác',
                            },
                            {
                                key: '11',
                                icon: <IoSettingsSharp />,
                                label: 'Cài đặt',
                            },
                        ]}
                    />
                </Sider>
                <Layout>
                    <Header style={{padding: 0, background: colorBgContainer }}>
                        <div class={'flex justify-between'}>
                            <Button
                                type="text"
                                icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                                onClick={() => setCollapsed(!collapsed)}
                                style={{
                                    fontSize: '16px',
                                    width: 64,
                                    height: 64,
                                }}
                            />
                            <div
                                onClick={()=>setNavOpen(true)}
                                className="flex items-center justify-center gap-2 px-[20px]"
                                 >
                                <img
                                    src="/loading.png"
                                    className="w-[40px] bg-red-500 p-2 rounded-full cursor-pointer"
                                />
                                <p className="text-[16px] hover:text-main cursor-pointer">
                                    {info?.name}
                                </p>
                            </div>
                        </div>
                        <NavAdmin propUp={navOpen} setPropUp={setNavOpen}/>
                    </Header>
                    <Content
                        style={{
                            margin: '24px 16px',
                            padding: 24,
                            minHeight: 280,
                            // background: colorBgContainer,
                            borderRadius: borderRadiusLG
                        }}
                        className={'overflow-x-scroll'}
                    >
                        {
                            select[0] == '1' &&
                            <OverView/>
                        }
                        {
                        select[0] == '2' &&
                            <Staff />
                        }
                        {
                            select[0] == '3' &&
                            <Film />
                        }
                        {
                            select[0] == '4' &&
                            <Sub />
                        }
                        {
                            select[0] == '5' &&
                            <ShowTime />
                        }
                        {
                            select[0] == '6' &&
                            <Theater />
                        }
                        {
                            select[0] == '7' &&
                            <Dish />
                        }
                        {
                            select[0] == '8' &&
                            <Bill />
                        }
                        {
                            select[0] == '9' &&
                            <Rate />
                        }
                        {
                            select[0] == '10' &&
                            <Contact />
                        }
                        {
                            select[0] == '11' &&
                            <Setting />
                        }
                    </Content>
                </Layout>
            </Layout>
        </>
    )
}
export default CommonDash;