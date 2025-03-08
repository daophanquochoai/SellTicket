import axios from "axios";
import {env} from "./Contanst.ts";
import Cookies from "js-cookie";
import {aw} from "framer-motion/dist/types.d-6pKw1mTI";

export const handleLoginByUsernameAndPassword : (username : string, password : string) => Promise<any> = async (username, password) => {
    try{
        const response = await axios.post(`${env.url.API_BASE_URL}/api/authenticate`,
            {
                username : username,
                password : password
            }
            );
        return response;
    }catch( e){
        return e;
    }
}

export const handleSignUpByAccount :
    (name : string, phoneNumber : string, email : string, username : string, password : string) => Promise<any> =  async ( name, phoneNumber, email, username,password) => {
    try{
        const response = await axios.post(`${env.url.API_BASE_URL}/user-service/api/customer/add`, {
            name : name,
            phoneNumber : phoneNumber,
            email : email,
            userName : username,
            password : password,
            roleId : 1,
            status : 'ACTIVE'
        })
        return response;
    }catch ( e){
        return e;
    }
}

export const handleSlide :
    (status : string) => Promise<any> = async ( status ) => {
    try{
        const reponse = await axios.get(`${env.url.API_BASE_URL}/film-service/api/film/get/status/` + status)
        return reponse;
    }catch ( e ){
        return e;
    }
}

export const parseJwt = (token : string) => {
    if (!token) { return }
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace('-', '+').replace('_', '/')
    return JSON.parse(window.atob(base64))
}

export const saveToken = (token : string) =>{
    Cookies.set("token", token, {expires : 1, path : "/"});
}

export const getToken = () => {
    return Cookies.get("token");
}

export const removeToken = () => {
    Cookies.remove("token");
}