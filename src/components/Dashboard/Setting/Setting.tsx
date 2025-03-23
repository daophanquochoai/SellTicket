import {toast} from "react-toastify";
import 'swiper/css';
import 'swiper/css/navigation';
import {Modal, Spin, Upload, UploadFile, UploadProps} from "antd";
import {InboxOutlined} from "@ant-design/icons";
import React, {useEffect, useState} from "react";
import {Autoplay, Navigation, Pagination} from "swiper/modules";
import {Swiper, SwiperSlide} from "swiper/react";
import {expireToken, getSlider, getToken, removeSlider, uploadSlider} from "../../../Helper/Helper.ts";
import {useNavigate} from "react-router-dom";
import './style.css';

const initImage : UploadFile = {
    uid : '',
    name : '',
    url : '',
    status : 'done'
}

const Setting : React.FC = () => {

    //var
    const [data,setData] = useState<UploadFile[]>([])
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();

    //modal
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [loadingModal, setLoadingModal] = useState<boolean>(false);
    const [imageSelected, setImageSelected] = useState<UploadFile>(initImage);

    useEffect(() => {
        handleFetchSlider();
    }, []);

    const { Dragger } = Upload;

    const props: UploadProps = {
        name: 'image',
        multiple: true,
        action: 'http://localhost:8080/upload',
        fileList: data,
        onRemove: (file) => {
            setImageSelected(file);
            setIsOpen(true);
        },
        onChange(info) {
            let newFileList = [...info.fileList]; // Lấy danh sách file mới nhất
            const { status } = info.file;

            if (status === 'done') {
                // Cập nhật URL từ response khi upload thành công
                newFileList = newFileList.map(file => {
                    if (file.uid === info.file.uid) {
                        return {
                            ...file,
                            url: info.file.response,
                            status: 'done',
                        };
                    }
                    return file;
                });
                handlePostImage(info.file.name,info.file.response);
            } else if (status === 'error') {
                toast.warning(<p>Không thể tải hình ảnh lên</p>);
                newFileList.filter(i=>i.status != 'done')
            }

            if( newFileList.find(i=>i.status != 'done')){
                setData(newFileList); // Cập nhật danh sách file
            }
        },
        onDrop(e) {
            console.log('Dropped files', e.dataTransfer.files);
        },
    };


    const handleFetchSlider = async () => {
        setLoading(true);
        const response = await getSlider();
        setLoading(false);
        console.log(response);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tải slide</p>)
            return;
        }
        setData([...response.data.data.map(item => {
            return {
                uid: item.id,
                name: item.name,
                status: 'done',
                url: item.image,
            }
        })]);
    }

    // upload image
    const handlePostImage = async (name : string,image : string) => {
        const token : string | undefined = getToken();
        if(token == undefined || expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoading(true);
        const response = await uploadSlider(name ,image, token);
        setLoading(false);
        console.log(response);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không thể tạo</p>)
            return;
        }
        console.log(data);
        toast.success(<p className={'w-full'}>Tải lên thành công</p>)
        setData([
            {
                uid: response.data.data.id,
                name: response.data.data.name,
                status: 'done',
                url: response.data.data.image
            },
            ...data.filter(i=>i.name!=name)
        ]);
    }

    //remove image
    const handleRemoveImage = async (uid : string) => {
        const token : string | undefined = getToken();
        if(token == undefined || expireToken(token)){
            toast.warning(<p className={'w-full'}>Phiên đăng nhập đã hết hạn</p>)
            navigate('/dashboard/login')
            return;
        }
        setLoadingModal(true);
        const response = await removeSlider( uid, token);
        setLoadingModal(false);
        if( response.status != 200 ){
            toast.warning(<p className={'w-full'}>Không xóa được hình ảnh</p>)
            return;
        }
        toast.success(<p className={'w-full'}>Xóa thành công</p>)
        setData([
            ...data.filter(i=>i.uid != uid)
        ]);
        setIsOpen(false);
    }

    const handleClose = () =>{
        setIsOpen(false);
    }
    return (
        <>
            <div>
                <div className={'bg-white p-[20px] rounded-xl'}>
                    <p className={'text-forget font-bold uppercase text-center mb-[20px]'}>Hiển thị Slider</p>
                    <Spin tip={"Đang xử lí..."} spinning={loading}>
                        <Dragger {...props}>
                            <p className="ant-upload-drag-icon">
                                <InboxOutlined/>
                            </p>
                            <p className="ant-upload-text">Chọn hoặc kéo thả hình ảnh vào đây</p>
                            <p className="ant-upload-hint">
                                Hỗ trợ tải lên một lần hoặc hàng loạt. Nghiêm cấm tải lên dữ liệu công ty hoặc các tệp
                                bị cấm khác.
                            </p>
                        </Dragger>
                        <div className={'mt-[20px]'}>
                            <Swiper spaceBetween={30}
                                    centeredSlides={true}
                                    autoplay={{
                                        delay: 2500,
                                        disableOnInteraction: false,
                                    }}
                                    pagination={{
                                        clickable: true,
                                    }}
                                    direction={"horizontal"}
                                    navigation={true}
                                    modules={[Autoplay, Pagination, Navigation]}
                                    className={`mySwiper ${data.length != 0 ? 'h-[250px]' : '[200px]'}`}
                            >
                                {
                                    data.length != 0 ? data.filter(i => i.status == 'done').map((item, index) => {
                                            return (
                                                <SwiperSlide key={index}
                                                             className="w-full h-full flex justify-center items-center">
                                                    <img src={item.url} alt="slide" className="w-full h-full object-cover"/>
                                                </SwiperSlide>
                                            )
                                        })
                                        :
                                        <div
                                            className={'w-full h-[200px] bg-textAdmin flex justify-center items-center'}>
                                            <p className={'text-center font-bold uppercase text-white'}>Chưa có slide
                                                nào</p>
                                        </div>
                                }
                            </Swiper>
                        </div>
                    </Spin>
                </div>
            </div>

            {/*modal*/}
            <Modal
                open={isOpen}
                closeIcon={false}
                onCancel={()=>handleClose()}
                loading={loadingModal}
                footer={[]}
            >
                <div>
                    <div><p className={'text-main text-xl font-bold text-center'}>Bạn có chắc chắn muốn xóa {imageSelected.name} ?</p></div>
                    <div className={'justify-center gap-4 flex items-center mt-[20px]'}>
                        <button
                            onClick={() => handleClose()}
                            className={'px-4 py-2 border-textAdmin border-2 w-[100px] text-textAdmin'}>Hủy
                        </button>
                        <button
                            onClick={() => handleRemoveImage(imageSelected.uid)}
                            className={'px-4 py-2 border-2 border-red-500 bg-red-500 text-white w-[100px]'}>Xác nhận
                        </button>
                    </div>
                </div>
            </Modal>
        </>
    )
}
export default Setting;