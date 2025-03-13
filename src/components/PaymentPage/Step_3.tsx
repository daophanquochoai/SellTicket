const Step_3 : React.FC = () => {
    return (
        <>
            <div className={'w-full'}>
                <div className={'flex items-start justify-between bg-main'}>
                    <div className={'w-1/2 flex flex-col gap-4 border-r-2 border-dashed  px-8 py-4'}>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium font-bold text-border uppercase'}>Tên phim : </h4>
                            <p className={'text-white uppercase'}>Nụ Hôn Bạc Tỷ</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium font-bold text-border uppercase'}>Phòng chiếu : </h4>
                            <p className={'text-white uppercase'}>Phòng A2</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium font-bold text-border uppercase'}>Thời gian : </h4>
                            <p className={'text-white uppercase'}>13:30:00 - 15:30:00 <span className={'text-foreground'}>(Ngày:2025-02-17)</span></p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium text-border font-bold uppercase'}>Mã giao dịch : </h4>
                            <p className={'text-white uppercase'}>TXN987654321</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium text-border font-bold uppercase'}>Tổng tiền : </h4>
                            <p className={'text-white'}>250000.0 VND</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium text-border font-bold uppercase'}>Khách hàng : </h4>
                            <p className={'text-white'}>daophanquochoai</p>
                        </div>
                        <div className={'flex gap-4 items-center'}>
                            <h4 className={'text-medium font-bold text-border'}>Email : </h4>
                            <p className={'text-white'}>hoai23828@gmail.com</p>
                        </div>
                        <div className={'flex items-center gap-4'}>
                            <h4 className={'text-medium text-border font-bold'}>Số điện thoại : </h4>
                            <p className={'text-white'}>0779127667</p>
                        </div>
                    </div>
                    <div className={'flex-1 flex flex-col bg-main gap-4  px-8 py-4'}>
                        <div className={"flex gap-4"}>
                            <p className={'text-medium text-border font-bold'}>QRcode :</p>
                            <img
                                src={'https://ci3.googleusercontent.com/meips/ADKq_NZlp-n3qQpmidkobGnHi8kNLs58pYQ6oIzkyKCyiFIlDg8d9IGlolzyQkNrMPwEoAEIvc5vTS_VjcI6W2vbLDV033WiQZ285TfPrRX15Oh14IwDwd0TFZ5lcHS8j1ffyH7qsDSgvXIRHFYqSI-moZqAMsobkv_MoLIQQA=s0-d-e1-ft#http://res.cloudinary.com/dkbukqhmr/image/upload/v1740300004/04994dcd-37b4-40c5-a760-bb7282639edd.png'}
                                className={'w-[100px] h-[100px]'}
                            />
                        </div>
                        <div className={'flex flex-col gap-4'}>
                            <p className={'text-xl text-border font-bold'}>Danh sách ghế</p>
                            <table>
                                <tr className={'border-border border-2'}>
                                    <th>Ghế</th>
                                    <th>Loại vé</th>
                                    <th>Giá</th>
                                </tr>
                            </table>
                        </div>
                        <div className={'flex flex-col'}>
                            <p className={'text-xl text-border font-bold'}>Danh sách món ăn</p>
                            <table>
                                <tr className={'border-border border-2'}>
                                    <th>Món ăn</th>
                                    <th>Loại</th>
                                    <th>Số lượng</th>
                                    <th>Giá</th>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Step_3;