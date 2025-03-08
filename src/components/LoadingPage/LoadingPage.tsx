import React from "react";
import { motion } from "framer-motion";

const LoadingPage : React.FC = () => {
    const text = "Loading...".split("");

    return <>
        <div className={'flex w-full h-dvh items-center justify-center'}>
            <div className={'flex flex-col items-center'}>
                <img src={'/public/loading.png'} className={'w-[70px] h-[70px] animate-iconAni'}/>
                <p className="text-main text-xl flex space-x-1">
                    {text.map((char, index) => (
                        <motion.span
                            key={index}
                            initial={{opacity: 0}}
                            animate={{opacity: 1}}
                            transition={{delay: index * 0.2, repeat: Infinity}}
                        >
                            {char}
                        </motion.span>
                    ))}
                </p>
            </div>
        </div>
    </>
}
export default LoadingPage;