import {parseJwt, saveToken} from "../../Helper/Helper.ts";
import {useEffect} from "react";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {useCommonContext} from "../../context/CommonContext.tsx";
import LoadingPage from "../LoadingPage/LoadingPage.tsx";

interface User {
    email : string,
    id : string,
    name : string,
    roles : []
    sub : string,
    phone : string
}

const OAuth2RedirectHandler : React.FC = () => {

    const {setLogin, setInfo} = useCommonContext();
    const navigate = useNavigate();

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const token: string|null = urlParams.get('token');
        if( token == null ){
            toast.error("Đã xảy ra lỗi!!")
            navigate("/login");
            return;
        }
        const valueToken : User = parseJwt(token);
        saveToken(token);
        if (setInfo) {
            setInfo(valueToken);
        }
        if (setLogin) {
            setLogin(true);
        }
        navigate("/")
    }, []);
    return (
        <>
            <LoadingPage/>
        </>
    )
}
export default OAuth2RedirectHandler;