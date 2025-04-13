import React, { useState} from "react";
import {Modal} from "antd";
import {toast} from "react-toastify";
import {createRoom, expireToken, getToken, updateRoom} from "../../Helper/Helper.ts";
import {useNavigate} from "react-router-dom";

interface Branch {
    id : string,
    nameBranch : string,
    address : string,
    status : string
}
interface Room{
    id : string,
    name : string,
    positionChair : number[][],
    branch : Branch,
    status : string
}
interface Props{
    loading : boolean,
    setLoading : (arg:boolean) => void,
    isModalOpen : boolean,
    setIsModalOpen : (arg:boolean) => void,
    dataRoom : Room,
    setDataRoom : (arg:Room) => void,
    active : string,
    dataRoomAll : Room[],
    setDataRoomAll : (arg :Room[]) => void,
    branchSelect : Branch
}

const initRoom = {
    id : '',
    name : '',
    branch : {
        id : '',
        nameBranch : '',
        address : '',
        status : 'ACTIVE'
    },
    status : 'ACTIVE',
    positionChair: [
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
    ]
}

const ModalRoom : React.FC<Props> = ( props ) => {

    const {loading, setLoading, isModalOpen, setIsModalOpen, dataRoom, setDataRoom, active, dataRoomAll, setDataRoomAll} = props;
    const navigate = useNavigate();
    const [selectTypeChair, setSelectTypeChair] = useState<number>(0);

    const handleCreateChair = (rowIndex, colIndex) => {
        const position : number[][] = dataRoom.positionChair;
        if( selectTypeChair == 2){
            if( colIndex + 1 > 19){
                toast.warning(<p className={'w-full'}>Không đủ khoảng trống</p>)
                return;
            }
            if( position[rowIndex][colIndex + 1] != 0){
                toast.warning(<p className={'w-full'}>Không đủ khoảng trống</p>)
                return;
            }
            if( position[rowIndex][colIndex] == 2){
                position[rowIndex][colIndex] = 0;
            }else if( position[rowIndex][colIndex] == 0){
                position[rowIndex][colIndex] = 2
            }
        }else if( selectTypeChair == 1 ){
            if( position[rowIndex][colIndex] == 1){
                position[rowIndex][colIndex] = 0;
            }else if( position[rowIndex][colIndex] == 0){
                position[rowIndex][colIndex] = 1
            }
        }else{
            position[rowIndex][colIndex] = 0;
        }
        setDataRoom({
            ...dataRoom,
            positionChair : position
        })
    }

    const handleCancel = () => {
        setDataRoom(initRoom);
        setIsModalOpen(false);
    }

    const handleUpdate = async () => {
        const token : string | undefined = getToken();
        if( token == undefined || expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await updateRoom(dataRoom, token);
        setLoading(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể cập nhật dữ liệu</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Cập nhật dữ liệu thành công</p>)
        setDataRoomAll(dataRoomAll.map(item => {
            if( item.id == dataRoom.id){
                return dataRoom;
            }
            return item;
        }))
    }

    const handleCreateRoom = async () => {
        if( dataRoom.name == ''){
            toast.success(<p className={'w-full'}>Vui lòng điển tên phòng</p>)
            return;
        }
        const token : string | undefined = getToken();
        if( token == undefined || expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await createRoom(dataRoom, props.branchSelect, token);
        setLoading(false);
        if( response.status != 201 ){
            toast.warning(<p className={'w-full'}>Tạo phòng không thành công</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Tạo phòng thành công</p>)
        setDataRoomAll([
            dataRoom,
            ...dataRoomAll
        ])
        setIsModalOpen(false);
        setDataRoom(initRoom);

    }
    return (
        <>
            <Modal
                open={isModalOpen}
                onCancel={() => handleCancel()}
                loading={loading}
                title={<p className={'text-main font-bold uppercase text-xl'}>Phòng</p>}
                footer={
                    active == 'CREATE' ?
                        <button onClick={()=> handleCreateRoom()} className={'text-white px-4 py-1 bg-main'}>Tạo phòng</button>
                        :
                        <button
                            onClick={() => handleUpdate()}
                            className={'text-white px-4 py-1 bg-main'}>Cập nhật</button>
                }
                width={1000}
            >
                {
                    active != 'CREATE' &&
                    <div className={'flex flex-col flex-1'}>
                        <label className={'text-main'}>ID <span className={'text-red-500'}>*</span></label>
                        <input value={dataRoom.id} required
                               disabled={active == 'UPDATE'}
                               onChange={(e) => setDataRoom({...dataRoom, id: e.target.value})}
                               className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                    </div>
                }
                <div className={'flex flex-col flex-1'}>
                    <label className={'text-main'}>Tên phòng <span className={'text-red-500'}>*</span></label>
                    <input value={dataRoom.name} required
                           onChange={(e) => setDataRoom({...dataRoom, name: e.target.value})}
                           className={'outline-0 border-textAdmin border-[1px] px-2 py-1'}/>
                </div>
                <div className={'flex items-center gap-2'}>
                    <p className={'text-main'}>Trạng thái :</p>
                    <select className={'outline-0'} value={dataRoom.status}
                            onChange={(e) => setDataRoom({...dataRoom, status: e.target.value})}>
                        <option value={'ACTIVE'}>Còn hoạt động</option>
                        <option value={'DELETE'}>Đã ngừng hoạt động</option>
                    </select>
                </div>
                <div className={'mt-[40px]'}>
                    <div>
                        <div className={'flex items-center justify-end gap-4 '}>
                            <div className={`flex cursor-pointer gap-2 border-textAdmin border-2 items-center justify-center px-2 py-1 ${selectTypeChair == 1 && 'bg-green-800 text-white'}`} onClick={() => setSelectTypeChair(1)}>
                                <div className={'w-[20px] h-[20px] bg-main'}></div>
                                <p>Ghế đơn</p>
                            </div>
                            <div className={`flex cursor-pointer gap-2 border-textAdmin border-2 items-center justify-center px-2 py-1 ${selectTypeChair == 2 && 'bg-green-800 text-white'}`} onClick={() => setSelectTypeChair(2)}>
                                <div className={'w-[20px] h-[20px] bg-red-500'}></div>
                                <p>Ghế đôi</p>
                            </div>
                            <div className={`flex cursor-pointer gap-2 border-textAdmin border-2 items-center justify-center px-2 py-1 ${selectTypeChair == 0 && 'bg-green-800 text-white'}`} onClick={() => setSelectTypeChair(0)}>
                                <div className={'w-[20px] h-[20px] border-textAdmin border-2'}></div>
                                <p>Khoảng trống</p>
                            </div>
                        </div>
                    </div>
                    <div className={'mt-[20px]'}>
                        <div>
                            <div className="h-[6px] text-xl w-full bg-border rounded-t-full text-center uppercase font-bold">Màn hình</div>
                        </div>
                        <table border={2} cellPadding={2} className={'mt-[20px] w-full'}>
                            <tbody>
                            {dataRoom.positionChair.map((row, rowIndex) => {
                                let temp: number = 0;
                                return <tr key={rowIndex}>
                                    {row.map((col, colIndex) => {

                                        if (col == 0) {
                                            if (temp == 0) {
                                                return (
                                                    <td key={colIndex} colSpan={1}>
                                                        <button
                                                            onClick={() => handleCreateChair(rowIndex, colIndex)}
                                                            className={'border-textAdmin border-2 px-2 py-1 w-[30px] h-[30px]'}></button>
                                                    </td>
                                                )
                                            }else{
                                                temp--;
                                            }
                                        } else if (col == 1) {
                                            return (
                                                <td key={colIndex} colSpan={1}>
                                                    <button
                                                        onClick={() => handleCreateChair(rowIndex, colIndex)}
                                                        className={'bg-main w-[30px] rounded-tl-[10px] rounded-tr-[10px] h-[30px]'}
                                                    >
                                                        {String.fromCharCode(65 + rowIndex) + (colIndex + 1)}
                                                    </button>
                                                </td>
                                            )
                                        } else {
                                            temp = 1;
                                            return (
                                                <td key={colIndex} colSpan={2}>
                                                    <button
                                                        className={'bg-red-500 w-full rounded-tl-[10px] rounded-tr-[10px] h-[30px]'}
                                                        onClick={() => handleCreateChair(rowIndex, colIndex)}
                                                    >
                                                        {String.fromCharCode(65 + rowIndex) + (colIndex + 1)}
                                                    </button>
                                                </td>
                                            )
                                        }
                                    })}
                                </tr>
                            })}
                            </tbody>
                        </table>
                    </div>
                </div>
            </Modal>
        </>
    )
}
export default ModalRoom;