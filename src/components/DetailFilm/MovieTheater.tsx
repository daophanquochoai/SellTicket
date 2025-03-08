import React, {useState} from "react";
import {FaChevronDown, FaChevronUp} from "react-icons/fa";
import { motion } from "framer-motion";

const MovieTheater : React.FC = () => {
    const [isExpand, setIsExpand] = useState<boolean>(false);
    return (
        <>
            <div className={'flex justify-center items-center mt-[60px]'}>
                <div className={'container'}>
                    <div>
                        <div className={'flex justify-center'}>
                            <p className={'text-3xl uppercase font-bold text-white'}>Danh Sách Rạp</p>
                        </div>
                        <div className={'flex flex-col mt-[40px] gap-6'}>
                            <motion.div
                                className={`border-2 border-main p-4 px-[40px] rounded-[10px] bg-main transition-all duration-700 ease-in-out overflow-hidden`}
                                initial={{height: 0}}
                                animate={{height: isExpand ? 80 : 235}}
                                transition={{duration: 0.3, ease: "easeInOut"}}
                            >
                                <div className={'flex justify-between items-center'}
                                     onClick={() => setIsExpand(!isExpand)}>
                                    <p className={`text-white text-2xl`}>Cinestar Quốc Thanh (TP.HCM)</p>
                                    <div className={`text-2xl text-white p-2 cursor-pointer`}>
                                        {
                                            isExpand ?
                                                <FaChevronDown/>
                                                :
                                                <FaChevronUp/>
                                        }
                                    </div>
                                </div>
                                <div className={`bg-main overflow-hidden mt-[30px] flex flex-col gap-4`}>
                                    <span className={'text-white text-xl'}>271 Nguyễn Trãi, Phường Nguyễn Cư Trinh, Quận 1, Thành Phố Hồ Chí Minh</span>
                                    <p className={'text-white'}>Xuất chiếu</p>
                                    <div className={'flex bg-red gap-4'}>
                                        <div className={'px-4 py-2 bg-white text-main rounded-[10px]'}>
                                            <p>8:30</p>
                                        </div>
                                        <div className={'px-4 py-2 bg-white text-main rounded-[10px]'}>
                                            <p>8:30</p>
                                        </div>
                                        <div className={'px-4 py-2 bg-black text-main rounded-[10px]'}>
                                            <p>8:30</p>
                                        </div>
                                    </div>
                                </div>
                            </motion.div>
                            <motion.div
                                className={`border-2 border-main p-4 px-[40px] rounded-[10px] bg-main transition-all duration-700 ease-in-out overflow-hidden`}
                                initial={{height: 0}}
                                animate={{height: isExpand ? 80 : 235}}
                                transition={{duration: 0.3, ease: "easeInOut"}}
                            >
                                <div className={'flex justify-between items-center'}
                                     onClick={() => setIsExpand(!isExpand)}>
                                    <p className={`text-white text-2xl`}>Cinestar Quốc Thanh (TP.HCM)</p>
                                    <div className={`text-2xl text-white p-2 cursor-pointer`}>
                                        {
                                            isExpand ?
                                                <FaChevronDown/>
                                                :
                                                <FaChevronUp/>
                                        }
                                    </div>
                                </div>
                                <div className={`bg-main overflow-hidden mt-[30px] flex flex-col gap-4`}>
                                    <span className={'text-white text-xl'}>271 Nguyễn Trãi, Phường Nguyễn Cư Trinh, Quận 1, Thành Phố Hồ Chí Minh</span>
                                    <p className={'text-white'}>Xuất chiếu</p>
                                    <div className={'flex bg-red gap-4'}>
                                        <div className={'px-4 py-2 bg-white text-main rounded-[10px]'}>
                                            <p>8:30</p>
                                        </div>
                                        <div className={'px-4 py-2 bg-white text-main rounded-[10px]'}>
                                            <p>8:30</p>
                                        </div>
                                        <div className={'px-4 py-2 bg-black text-main rounded-[10px]'}>
                                            <p>8:30</p>
                                        </div>
                                    </div>
                                </div>
                            </motion.div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default MovieTheater;