import {createContext, ReactNode, useContext, useEffect, useState} from "react";
import { getToken, parseJwt} from "../Helper/Helper.ts";

interface CommonContextType {
    isLogin ?: boolean
    setLogin ?: (arg :boolean) => void,
    info ?: User,
    setInfo ?: (arg : User) => void,
    bill ?: BillDto,
    setBill ?: (arg:BillDto) => void,
    initBill ?: BillDto
}

interface User {
    email : string,
    id : string,
    name : string,
    roles : []
    sub : string,
    phone : string,
    cccd : string
}
interface BillDto {
    id : string,
    totalPrice : number,
    transactionCode : string,
    paymentMethodId : string,
    paymentMethod : string,
    chairs : BillChairDto[],
    dishes : BillDishDto[],
    timestamp : string,  // 1
    status : string,
    filmShowTimeId : number,
    timeEnd : string,
    timeStart : string,
    timeStampSee : string,
    roomId : string,
    nameRoom : string,
    filmId : string,
    nameFilm : string,
    userName : string,
    email : string,
    numberPhone : string,
    nameBranch : string,
    address : string
}
interface BillChairDto{
    id : string,
    chairCode : string,
    price : string,
    ticket : TicketDto,
}
interface TicketDto {
    id : string,
    conditionUse : string,
    name : string,
    price : string,
    typeTicket : string
}
interface  BillDishDto {
    id : string,
    price : number,
    amount : number,
    dishDto : DishDto
}
interface DishDto{
    id : string,
    price : number,
    name : string,
    image : string,
    typeDish : TypeDishDto
}
interface TypeDishDto{
    id : string,
    name : string
}

const initUser : User = {
    email : "",
    id : "",
    name : "",
    roles : [],
    sub : "",
    phone : "",
    cccd : ''
}
const initBill : BillDto = {
    id : "",
    totalPrice : 0,
    transactionCode : "",
    paymentMethodId : "",
    paymentMethod : "",
    chairs : [],
    dishes : [],
    timestamp : "",
    status : "ACTIVE",
    filmShowTimeId : 0,
    timeEnd : "",
    timeStart : "",
    timeStampSee : "",
    roomId : "",
    nameRoom : "",
    filmId : "",
    nameFilm : "",
    userName : "",
    email : "",
    numberPhone : "",
    nameBranch : "",
    address : ""
}

const defaultValue: CommonContextType = {};

export const CommonContext = createContext<CommonContextType>(defaultValue);

const CommonProvider: React.FC<{ children: ReactNode }> = ({ children }) => {

    const [isLogin, setIsLogin] = useState<boolean>(false);
    const [info, setInfo ] = useState<User>(initUser);
    const [bill, setBill] = useState<BillDto>(initBill);

    useEffect(() => {
        const token : string = getToken();
        if( token != undefined ){
            const user : User = parseJwt(token);
            setInfo(user);
            setIsLogin(true);
        }
    }, []);

    const contextValue : CommonContextType = {
        isLogin : isLogin,
        setLogin : setIsLogin,
        info : info,
        setInfo : setInfo,
        bill : bill,
        setBill : setBill,
        initBill : initBill
    }

    return (
        <CommonContext.Provider value={contextValue}>
            {children}
        </CommonContext.Provider>
    );
};

export default CommonProvider;

export const useCommonContext = () => useContext(CommonContext);
