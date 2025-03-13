import React, {lazy, useState} from "react";
import { motion } from "framer-motion";

const Stripe = lazy(() => import("./Stripe.tsx"));

interface Props{
    setStep : (arg: number) => void
}

const Step_2 : React.FC<Props> = ( props ) => {
    const [select, setSelect] = useState<number>(0);

    return (
        <>
            <div>
                <motion.div
                    animate={{height: select !== 1  ? 80 : 180}}
                    className={' border-2 border-border px-4 py-2 rounded-[10px] transition-all duration-700 ease-in-out overflow-hidden'}
                >
                    <div className={`flex items-center group cursor-pointer mb-[10px] ${select === 1 ? 'bg-border text-textCol' : ''}`} onClick={() => setSelect(1)}>
                        <img src={'https://cinestar.com.vn/assets/images/img-card.png'} alt={'icon visa'} className={'w-[60px] h-[60px]'}/>
                        <p className={`text-xl uppercase transition-all duration-300 ${select === 1 ? '' : 'text-white group-hover:text-main'}`}>Thanh toán thẻ visa</p>
                    </div>
                    <div>
                        <Stripe />
                    </div>
                </motion.div>
                <div className={'mt-[20px] flex justify-between'}>
                    <button className={'text-white bg-main w-[150px] px-4 py-2'} onClick={()=>props.setStep(1)}>Quay lại</button>
                </div>
            </div>
        </>
    )
}
export default Step_2;