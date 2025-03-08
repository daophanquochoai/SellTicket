import {createContext, ReactNode, useContext, useState} from "react";

interface CommonContextType {
    isLogin ?: boolean
    setLogin ?: (arg :boolean) => void,
    info ?: User,
    setInfo ?: (arg : User) => void
}

interface User {
    email : string,
    id : string,
    name : string,
    roles : []
    sub : string
}

const initUser : User = {
    email : "",
    id : "",
    name : "",
    roles : [],
    sub : ""
}

const defaultValue: CommonContextType = {};

export const CommonContext = createContext<CommonContextType>(defaultValue);

const CommonProvider: React.FC<{ children: ReactNode }> = ({ children }) => {

    const [isLogin, setIsLogin] = useState<boolean>(false);
    const [info, setInfo ] = useState<User>(initUser);
    const contextValue : CommonContextType = {
        isLogin : isLogin,
        setLogin : setIsLogin,
        info : info,
        setInfo : setInfo
    }

    return (
        <CommonContext.Provider value={contextValue}>
            {children}
        </CommonContext.Provider>
    );
};

export default CommonProvider;

export const useCommonContext = () => useContext(CommonContext);
