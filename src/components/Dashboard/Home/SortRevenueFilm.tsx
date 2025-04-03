import React, {useEffect, useState} from "react";
import {getRevenueFilm} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {Spin} from "antd";

interface Data {
    id: string,
    name : string,
    total_revenue : number
}
interface Props{
    month : string | year,
    year : string | year
}

const SortRevenueFilm : React.FC<Props> = (props) => {

    const [data, setData] = useState<Data[]>([]);
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        handleFetchRevenueFilm();
    }, [props.month, props.year]);

    const handleFetchRevenueFilm = async () => {
        setLoading(true);
        const response = await getRevenueFilm(props.month, props.year);
        setLoading(false);
        console.log(response)
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải biểu đồ</p>)
            return;
        }
        console.log(response);
        setData(response.data?.data);
    }

    const formatCurrency = (value : number) => {
        if (value >= 1_000_000_000) {
            return `${(value / 1_000_000_000).toFixed(2)}B`; // Tỷ
        } else if (value >= 1_000_000) {
            return `${(value / 1_000_000).toFixed(2)}M`; // Triệu
        } else if (value >= 1_000) {
            return `${(value / 1_000).toFixed(2)}K`; // Nghìn
        }
        return value.toString(); // Giữ nguyên nếu nhỏ hơn 1K
    };


    return (
        <>
            <div>
                <p className={'font-bold text-green-400 text-xl'}>XẾP HẠNG PHIM</p>
            </div>
            <Spin tip={'Đang tải...'} spinning={loading}>
                <div className={'bg-gray-100 mt-[20px] p-[20px] rounded-xl flex flex-col gap-4'}>
                    {
                        data && data.map( (item, index) => {
                            return <div key={index} className={`hover:scale-105 transition-all duration-700 ${index == 0 ? 'bg-orange-400' : (index == 1 ? 'bg-yellow-300' : (index == 2 ? 'bg-red-400' : 'bg-white'))} p-4 rounded-xl cursor-pointer flex justify-between items-center`}>
                                <p className={'text-white font-bold uppercase'}>{item.name}</p>
                                <p className={'text-border font-bold'}>{formatCurrency(item.total_revenue)}</p>
                            </div>
                        })
                    }
                </div>
            </Spin>
        </>
    )
}
export default SortRevenueFilm;