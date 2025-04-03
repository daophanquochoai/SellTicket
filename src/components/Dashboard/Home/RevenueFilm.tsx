import React, {memo, useEffect, useState} from 'react';
import { Pie } from '@ant-design/plots';
import { isEqual } from 'lodash-es';
import {getRevenueFilm} from "../../../Helper/Helper.ts";
import {toast} from "react-toastify";
import {Spin} from "antd";

const DemoPie = memo(
    ({ data, onReady }) => {
        var config = {
            data,
            angleField: 'value',
            colorField: 'type',
            label: {
                text: (item) => `${item.value.toLocaleString()} VNĐ`,
                position: 'outside',
            },
            tooltip: (item) => ({value : `${item.type} - ${item.value.toLocaleString()}VND`}),
            onReady,
        };
        return <Pie {...config} />;
    },
    (pre, next) => isEqual(pre?.data, next?.data)
);

interface Data {
    id: string,
    name : string,
    total_revenue : number
}

interface Props {
    month : string | number,
    year : string | number
}
const RevenueFilm : React.FC<Props> = ( props ) => {


    const [data, setData] = useState<Data[]>([]);
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        handleFetchRevenueFilm();
    }, [props.month, props.year]);

    const handleFetchRevenueFilm = async () => {
        setLoading(true);
        const response = await getRevenueFilm(props.month, props.year);
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải biểu đồ</p>)
            return;
        }
        setData(response.data?.data);
    }

    return (
        <>
            <div className={'flex justify-end'}>
                <p className={'font-bold text-green-400 text-xl'}>DOANH THU PHIM TRONG THÁNG</p>
            </div>
            <Spin tip={'Đang tải...'} spinning={loading}>
                <DemoPie data={data?.map(item =>{
                    return {
                        type : item.name,
                        value : item.total_revenue
                    }
                })} />
            </Spin>
        </>
    )
}
export default RevenueFilm;