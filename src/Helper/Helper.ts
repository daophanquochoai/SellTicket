import axios from "axios";
import {env} from "./Contanst.ts";
import Cookies from "js-cookie";
import {toast} from "react-toastify";

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

export const fetchFilmById :
    ( filmId : string) => Promise<any> = async ( filmId ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/film/` + filmId);
        return response;
    }catch ( e ){
        return e;
    }
}

export const fetchComment :
    ( filmId : string ) => Promise<any> = async ( filmId ) => {
    try {
        const response = await axios.get(`${env.url.API_BASE_URL}/rate-service/api/rate/film/` + filmId + `?page=0&limit=3&asc=asc&orderBy=timeStamp&status=ACTIVE`)
        return response
    }catch (e){
        return e;
    }
}
export const fetchCommentByFull
    : ( filmId : string,page : number, limit : number, asc : string, orderBy : string, q : string) => Promise<any>
    = async ( filmId, page, limit, asc, orderBy, q ) => {
    try {
        const response = await axios.get(`${env.url.API_BASE_URL}/rate-service/api/rate/film/` + filmId + `?page=${page}&limit=${limit}&asc=${asc}&status=ACTIVE&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`)
        return response
    }catch (e){
        return e;
    }
}

export const fetchTheater :
    () => Promise<any> = async () => {
    try {
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/branch/all`);
        return response;
    }catch ( e ){
        return e;
    }
}

export const fetchFilmShow :
    (branchId : string, subId : string, filmId : string, time : string) => Promise<any> = async ( branchId, subId, filmId, time) => {
    try{
        const response = await axios.get(`${env.url.API_BASE_URL}/filmshowtime-service/api/filmshowtime/get/` + branchId + `/` + time + `/` + filmId + `/` + subId);
        return response;
    }catch ( e ){
        return e;
    }
}
export const fetchTicket :
    () => Promise<any> = async () => {
    try{
        const response = await axios.get(`${env.url.API_BASE_URL}/payment-service/api/ticket/all?limit=100&page=0&active=none&orderBy=price&asc=asc`)
        return response;
    }catch ( e){
        return e;
    }
}

export const fetchRoomById :
    (id : string) => Promise<any> = async (id) =>
{
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/room/` + id);
        return response;
    }catch (e){
        return e;
    }
}

export const fetchRoomBooked :
    (id : number) => Promise<any> = async (id) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/payment-service/api/billchair/` + id);
        return response;
    }catch (e){
        return e;
    }
}

export const fetchDish : () => Promise<any> = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/dish-service/api/typedish/all`);
        return response;
    }catch (e){
        return e;
    }
}

export const fetchBranch : () => Promise<any> = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/branch/all`);
        return response;
    }catch (e){
        return e;
    }
}

export const fetchFilmAll : () => Promise<any> = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/film/all`);
        return response;
    }catch (e){
        return e;
    }
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

export const paymentBill :
    (bill : BillDto) => Promise<any>
    = async ( bill ) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/payment-service/api/bill/add`,{
            ...bill
        });
        return response;
    }catch (e){
        return e;
    }
}

export const updateAccountCustomer
    : (name : string,email : string, phone : string, id : string, token : string) => Promise<any>
    = async ( name, email, phone, id, token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/user-service/api/customer/update/` + id,{
            name : name,
            phoneNumber : phone,
            email : email
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const updatePassword :
    (passwordNew : string, id : string, token : string) => Promise<any>
    = async ( passwordNew, id, token ) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/user-service/api/customer/update/password/` + id,{
            passwordNew : passwordNew
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const getReport
    : ( year : string, token : string) => Promise<any>
    = async ( year, token ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/payment-service/api/report/year/` + year,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const getYearForBill
    : ( token : string) => Promise<any>
    = async ( token ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/payment-service/api/report/year`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const getNumCustomerAndEmployee
    : ( token : string) => Promise<any>
    = async ( token ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/user-service/api/account/num`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

// get employee
export  const getEmployee
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<any>
= async ( page, limit, asc, status, orderBy, q, token  ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/user-service/api/employment/get/employee?page=${page}&limit=${limit}&asc=${asc}&status=${status}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const getFilm
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<any>
    = async ( page, limit, asc, status, orderBy, q, token  ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/film/get/custom?page=${page}&limit=${limit}&asc=${asc}&status=${status}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const getSub
    : () => Promise<any>
    = async ( ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/sub/all`);
        return response;
    }catch (e){
        return e;
    }
}

export const getTypeFilm
    : () => Promise<any>
    = async ( ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/typefilm/get/all`);
        return response;
    }catch (e){
        return e;
    }
}

//create film
interface Film {
    id: string,
    name : string,
    age : number,
    image : string,
    sub: Sub[],
    nation : string,
    duration : string,
    description : string,
    content : string,
    trailer : string,
    typeFilms : TypeFilm[],
    status : string
}
interface Sub {
    id : string,
    name : string
}
interface TypeFilm {
    id : string,
    name : string,
    active : string
}

export const createFilm
    : (film : Film, token : string) => Promise<any>
    = async ( film , token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/film-service/api/film/add`,{
            ...film
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const updateFilm
    : (film : Film, token : string) => Promise<any>
    = async ( film , token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/film-service/api/film/update/` + film.id,{
            ...film
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
// Branch
export const getBranch
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<any>
    = async ( page, limit, asc, status, orderBy, q, token  ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/branch/get/branch?page=${page}&limit=${limit}&asc=${asc}&status=${status}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
export const getRoomByBranchId
    : ( branchId : string) => Promise<any>
    = async ( branchId ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/room/get/branch/${branchId}`);
        return response;
    }catch (e){
        return e;
    }
}
export const updateBranch
    : ( branch : Branch, token : string) => Promise<any>
    = async ( branch, token ) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/room-service/api/branch/update/${branch.id}`,{
            ...branch
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
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
export const updateRoom
    : ( room : Room, token : string) => Promise<any>
    = async ( room, token ) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/room-service/api/room/update/${room.id}`,{
            branchId : room.branch.id,
            name : room.name,
            positionChair :room.positionChair,
            status : room.status
        },{
            headers : {
                "Content-Type": "application/json",
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
interface Branch {
    id : string,
    nameBranch : string,
    address : string,
    status : string
}
export const createBranch
    : ( branch : Branch, token : string) => Promise<any>
    = async ( branch , token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/room-service/api/branch/add`,{
            ...branch
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

//-------------------------- fetch dish ------------------------------
export const getTypeFilmByCustom
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<any>
    = async ( page, limit, asc, status, orderBy, q, token  ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/dish-service/api/typedish/get?page=${page}&limit=${limit}&asc=${asc}&status=${status}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
export const updateDish
    : (id : string,price : number, active : string, name : string, image : string, typeDishId : string, token : string) => Promise<any>
    = async (id, price, active, name, image, typeDishId, token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/dish-service/api/dish/update/${id}`,{
            id : id,
            price : price,
            active : active,
            name : name,
            image : image,
            typeDishId : typeDishId
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
export const createDish
    : (id : string,price : number, active : string, name : string, image : string, typeDishId : string, token : string) => Promise<any>
    = async (id, price, active, name, image, typeDishId, token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/dish-service/api/dish/add`,{
            id : id,
            price : price,
            active : active,
            name : name,
            image : image,
            typeDishId : typeDishId
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
export const updateTypeDish
    : (id : string, active : string, name : string, token : string) => Promise<any>
    = async (id, active, name, token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/dish-service/api/typedish/update/${id}`,{
            id : id,
            active : active,
            name : name,
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

// ------------------- ticket ---------------------------------------------\
export const getTicketByCustom
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<any>
    = async ( page, limit, asc, status, orderBy, q, token  ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/payment-service/api/ticket/all?page=${page}&limit=${limit}&asc=${asc}&status=${status}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const updateTicket
    : (id : string, active : string, conditionUse : string, name : string, price : string, typeTicket : string, slot : string , token : string) => Promise<any>
    = async (id, active, conditionUse, name, price,typeTicket,slot,token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/payment-service/api/ticket/update/${id}`,{
            id : id,
            active : active,
            name : name,
            conditionUse : conditionUse,
            price : price,
            typeTicket : typeTicket,
            slot : slot
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const createTicket
    : (id : string, active : string, conditionUse : string, name : string, price : string, typeTicket : string, slot : string , token : string) => Promise<any>
    = async (id, active, conditionUse, name, price,typeTicket,slot,token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/payment-service/api/ticket/add`,{
            active : active,
            name : name,
            conditionUse : conditionUse,
            price : price,
            typeTicket : typeTicket,
            slot : slot
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

// --------------- bill ------------------
export const getBillByCustom
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<any>
    = async ( page, limit, asc, status, orderBy, q, token  ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/payment-service/api/bill/custom?page=${page}&limit=${limit}&asc=${asc}&status=${status}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}


// -------------- employee --------
export const updateEmploye
    : ( name : string , email : string , pass : string, cccd : string, id : string , token : string) => Promise<any>
    = async (name, email, pass, cccd, id , token ) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/user-service/api/employment/update/${id}`,{
            name : name,
            email : email,
            password : pass,
            cccd : cccd
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

// --------------- rate ------------------
export const getRateCustom
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<any>
    = async (page, limit,asc,status,orderBy,q,token) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/rate-service/api/rate/get/rate?page=${page}&limit=${limit}&asc=${asc}&status=${status}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
export const deleteRate
    : (id : string, token : string) => Promise<any>
    = async (id, token) => {
    try{
        console.log(token);
        const response = await  axios.put(`${env.url.API_BASE_URL}/rate-service/api/rate/delete/${id}`,{},{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
export const activeRate
    : (id : string, token : string) => Promise<any>
    = async (id, token) => {
    try{
        console.log(token);
        const response = await  axios.put(`${env.url.API_BASE_URL}/rate-service/api/rate/active/${id}`,{},{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
export const uploadComment
    :(star : number, content : string, customerId : string, filmId : string, token : string) => Promise<any>
    = async (star, content,customerId,filmId, token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/rate-service/api/rate/add/${customerId}/${filmId}`,{
            star : star,
            content : content,
            customerId : customerId,
            filmId : filmId,
            active : "ACTIVE"
        },{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}


// ---------------- sub -----------------------------
export const getSubCustom
    : (page : number, limit : number, asc : string, orderBy : string, q : string) => Promise<any>
    = async (page, limit,asc,orderBy,q) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/sub/custom?page=${page}&limit=${limit}&asc=${asc}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`);
        return response;
    }catch (e){
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
interface User {
    email : string,
    id : string,
    name : string,
    roles : []
    sub : string,
    phone : string,
    exp : number
}
export const expireToken = ( token : string ) =>{
    const info : User = parseJwt(token);
    const now = Math.floor(Date.now() / 1000); // Lấy thời gian hiện tại (giây)

    if (now > info.exp) {
        return true;
    } else {
        return false;
    }
}