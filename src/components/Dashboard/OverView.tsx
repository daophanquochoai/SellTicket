import { Column } from '@ant-design/plots';
import React, {lazy, useEffect, useState} from 'react';
import {Select, Spin} from "antd";
import {expireToken, getNumCustomerAndEmployee, getReport, getToken, getYearForBill} from "../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {AiOutlineRise} from "react-icons/ai";
import {IoIosTrendingDown} from "react-icons/io";
import { IoPersonCircle } from "react-icons/io5";
import { FaPersonWalkingLuggage } from 'react-icons/fa6';
import { GiFamilyHouse } from 'react-icons/gi';

const RevenueFilm = lazy(()=> import("./Home/RevenueFilm.tsx"));
const SortRevenueFilm = lazy(() => import("./Home/SortRevenueFilm.tsx"));

interface Report{
    month : number,
    totalPrice : number
}
interface Year{
    value: string,
    label: string
}
interface NumberAccount {
    numCustomer : number,
    numEmployee : number,
    numBranch : number
}
interface Month{
    revenue : number,
    increment : boolean
}

interface RevenueParam {
    month : string | number ,
    year : string | number
}
const initMonth : Month = {
    revenue : 0,
    increment : false
}
const initNumber = {
    numCustomer : 0,
    numEmployee : 0,
    numBranch : 0
}
const initRevenueParam : RevenueParam = {
    month : new Date(Date.now()).getMonth() + 1,
    year : new Date(Date.now()).getFullYear()
}
const OverView : React.FC = () => {

    const [loading, setLoading] = useState<boolean>(false);
    const [loadingYear, setLoadingYear] = useState<boolean>(false);
    const [dataYear, setDataYear] = useState<Year[]>([]);
    const [selectYear, setSelectYear] = useState<string>('2025');
    const navigate = useNavigate();
    const [data ,setData] = useState<Report[]>([]);
    const [dataNum, setDataNum] = useState<NumberAccount>(initNumber);

    //revenue detail
    const [revenueParam, setRevenueParam] = useState<RevenueParam>(initRevenueParam);

    //detail
    const [month, setMonth] = useState<Month>(initMonth);

    useEffect(() => {
        handleFetchYearForBill();
        handleFetchReport();
        handleFetchNumCustomerAndEmployee();
    }, [selectYear]);
    useEffect(() => {
        if( data?.length == 0 ) return;
        const month = new Date(Date.now()).getMonth() + 1;
        let pastMonth : Report = null;
        let recentMonth : Report = null;
        data && data.forEach( item => {
            if( item.month == month){
                recentMonth = item;
            }
            if( item.month == month - 1){
                pastMonth = item;
            }
        })
        if( pastMonth != null && recentMonth != null){
            if( pastMonth.totalPrice < recentMonth.totalPrice){
                setMonth({revenue : recentMonth.totalPrice, increment : true});
            }else{
                setMonth({revenue : recentMonth.totalPrice, increment : false});
            }
        }
    }, [data]);

    const handleFetchReport = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await getReport(selectYear, token);
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu biểu đồ</p>)
            return;
        }
        const data : Report[] = response.data.data;
        setData(data);
    }

    const handleFetchYearForBill = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingYear(true);
        const response = await getYearForBill( token);
        setLoadingYear(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu biểu đồ</p>)
            return;
        }
        const data : string[] = response.data.data;
        const temp : Year[] = [];
        data && data.forEach( item => {
            temp.push({
                value : item,
                label : item
            })
        })
        setDataYear(temp);
    }
    const handleFetchNumCustomerAndEmployee = async () => {
        const token : string = getToken();
        if( expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        const response = await getNumCustomerAndEmployee(token);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải dữ liệu biểu đồ</p>)
            return;
        }
        setDataNum(response.data.data);
    }


    const config = {
        data,
        xField: 'month',
        yField: 'totalPrice',
        colorField: 'month',
        onReady: ( {chart }) => {
            chart.on('element:click', (event) => {
                setRevenueParam({
                    ...revenueParam,
                    month : event.data.data.month
                })
            });
        },
        axis: {
            x: {
                size: 40,
                labelFormatter: (datum) => `Tháng ${datum}`,
            },
            y : {
                labelFormatter: (datum) => `${datum.toLocaleString()}`,
            }
        },
        legend: false,
        theme: "academy",
        tooltip: (item ) => ({name : `Tháng ${item.month}`, value : `${item.totalPrice.toLocaleString()}VND`})
    };

    const handleChangeYear = (e) => {
        setSelectYear(e);
        setRevenueParam({
            ...revenueParam,
            year : e
        })
    }
    return (
        <>
            <div>
                <div className={'flex items-center gap-[40px] bg-white rounded-xl p-[20px]'}>
                    <div className={'flex-1 flex h-[100px] shadow'}>
                        <div className={'bg-green-800 h-[100%] w-[20px]'}></div>
                        <div
                            className={'flex flex-col flex-1 items-center justify-center'}>
                            <div className={'flex gap-2'}>
                                <p className={'text-3xl '}>{month.revenue.toLocaleString()}Đ</p>
                                <div className={'text-4xl'}>
                                    {
                                        month.increment ?
                                            <AiOutlineRise className={'text-green-800'}/>
                                            :
                                            <IoIosTrendingDown className={'text-red-500'}/>
                                    }
                                </div>
                            </div>
                            <p className={'text-xl text-green-800'}>Doanh thu</p>
                        </div>
                    </div>
                    <div className={'flex-1 flex h-[100px] shadow'}>
                        <div className={'bg-red-800 h-[100%] w-[20px]'}></div>
                        <div
                            className={'flex flex-col flex-1 items-center justify-center'}>
                            <div className={'flex gap-2'}>
                                <p className={'text-3xl '}>{dataNum?.numCustomer.toLocaleString()}</p>
                                <div className={'text-4xl text-red-800'}>
                                    <IoPersonCircle/>
                                </div>
                            </div>
                            <p className={'text-xl text-red-800'}>Khách hàng</p>
                        </div>
                    </div>
                    <div className={'flex-1 flex h-[100px] shadow'}>
                        <div className={'bg-yellow-600 h-[100%] w-[20px]'}></div>
                        <div
                            className={'flex flex-col flex-1 items-center justify-center'}>
                            <div className={'flex gap-2'}>
                                <p className={'text-3xl '}>{dataNum?.numEmployee.toLocaleString()}</p>
                                <div className={'text-4xl text-yellow-600'}>
                                    <FaPersonWalkingLuggage />
                                </div>
                            </div>
                            <p className={'text-xl text-yellow-600'}>Nhân viên</p>
                        </div>
                    </div>
                    <div className={'flex-1 flex h-[100px] shadow'}>
                        <div className={'bg-pink-400 h-[100%] w-[20px]'}></div>
                        <div
                            className={'flex flex-col flex-1 items-center justify-center'}>
                            <div className={'flex gap-2'}>
                                <p className={'text-3xl '}>{dataNum?.numBranch.toLocaleString()}</p>
                                <div className={'text-4xl text-pink-400'}>
                                    <GiFamilyHouse/>
                                </div>
                            </div>
                            <p className={'text-xl text-pink-400'}>Chi nhánh</p>
                        </div>
                    </div>
                </div>
                <div className={'bg-white rounded-xl mt-[40px] p-[20px]'}>
                    <div className={'flex justify-between'}>
                        <p className={'text-xl font-bold text-green-400'}>DOANH THU</p>
                        <Spin tip={'Đang tải...'} spinning={loadingYear}>
                            <Select
                                value={selectYear}
                                style={{width: 120}}
                                options={dataYear}
                                onChange={e=>handleChangeYear(e)}
                            />
                        </Spin>
                    </div>
                    <Spin tip={"Đang tải..."} spinning={loading}>
                        <Column {...config} />
                    </Spin>
                </div>
                <div className={'flex justify-center p-[20px] bg-white my-[20px] rounded-xl text-green-600 font-bold text-xl'}>
                    <p>Tháng {revenueParam.month}</p>
                </div>
                <div className={'flex mt-[20px] gap-[20px]'}>
                    <div className={'flex-1 bg-white rounded-xl p-[20px] h-full max-h-[400px] overflow-x-scroll'}>
                        <SortRevenueFilm month={revenueParam.month} year={revenueParam.year}/>
                    </div>
                    <div className={'flex-1 bg-white rounded-xl p-[20px]'}>
                        <RevenueFilm month={revenueParam.month} year={revenueParam.year}/>
                    </div>
                </div>
            </div>
        </>
    )
}
export default OverView;