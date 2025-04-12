import axios from "axios";
import {env} from "./Contanst.ts";
import Cookies from "js-cookie";

interface Response {
    data : object,
    status : number
}

export const handleLoginByUsernameAndPassword : (username : string, password : string) => Promise<Response> = async (username, password) => {
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

export const handleForgetPassword : ( email : string) => Promise<Response> = async (email) => {
    try{
        const response = await axios.post(`${env.url.API_BASE_URL}/user-service/api/customer/forget/customer?email=${email}`,
            {
            },
            {
            }
        );

        return response;
    }catch( e){
        return e;
    }
}
export const handleAcceptOpt
    : (opt : string, password : string, email : string) => Promise<Response>
    = async (opt, password, email) => {
    try{
        console.log(password);
        const response = await axios.post(`${env.url.API_BASE_URL}/user-service/api/customer/change/customer/${opt}/${email}`,
             password ,
            { headers: { "Content-Type": "application/json" } }
        );
        return response;
    }catch( e){
        return e;
    }
}

export const createAccountEmployee
    : ( name : string, email : string, cccd : string, username : string, token : string) => Promise<Response>
    = async ( name, email, cccd, username, token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/user-service/api/employment/add`,{
            name : name,
            email : email,
            cccd : cccd,
            userName : username,
            password : '123456',
            status : 'ACTIVE',

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

export const handleSignUpByAccount :
    (name : string, phoneNumber : string, email : string, username : string, password : string) => Promise<Response> =  async ( name, phoneNumber, email, username,password) => {
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
    (status : string) => Promise<Response> = async ( status ) => {
    try{
        const reponse = await axios.get(`${env.url.API_BASE_URL}/film-service/api/film/get/status/` + status)
        return reponse;
    }catch ( e ){
        return e;
    }
}

export const fetchFilmById :
    ( filmId : string) => Promise<Response> = async ( filmId ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/film/` + filmId);
        return response;
    }catch ( e ){
        return e;
    }
}

export const fetchComment :
    ( filmId : string ) => Promise<Response> = async ( filmId ) => {
    try {
        const response = await axios.get(`${env.url.API_BASE_URL}/rate-service/api/rate/film/` + filmId + `?page=0&limit=3&asc=asc&orderBy=timeStamp&status=ACTIVE`)
        return response
    }catch (e){
        return e;
    }
}
export const fetchCommentByFull
    : ( filmId : string,page : number, limit : number, asc : string, orderBy : string, q : string) => Promise<Response>
    = async ( filmId, page, limit, asc, orderBy, q ) => {
    try {
        const response = await axios.get(`${env.url.API_BASE_URL}/rate-service/api/rate/film/` + filmId + `?page=${page}&limit=${limit}&asc=${asc}&status=ACTIVE&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`)
        return response
    }catch (e){
        return e;
    }
}
// ----------------- room ---------------
export const fetchTheater :
    () => Promise<Response> = async () => {
    try {
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/branch/all`);
        return response;
    }catch ( e ){
        return e;
    }
}
export const fetchRoomAll :
    () => Promise<Response> = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/room/all`);
        return response;
    }catch (e){
        return e;
    }
}
export const fetchFilmShow :
    (branchId : string, subId : string, filmId : string, time : string) => Promise<Response> = async ( branchId, subId, filmId, time) => {
    try{
        const response = await axios.get(`${env.url.API_BASE_URL}/filmshowtime-service/api/filmshowtime/get/` + branchId + `/` + time + `/` + filmId + `/` + subId);
        return response;
    }catch ( e ){
        return e;
    }
}

export const fetchFilmShowByRoomAndTime
    : (roomId : string , time : string)  => Promise<Response>
    = async (roomId, time) => {
    try{
        const response = await axios.get(`${env.url.API_BASE_URL}/filmshowtime-service/api/filmshowtime/${roomId}/all?date=${time}`);
        return response;
    }catch ( e ){
        return e;
    }
}
export const fetchTicketByActive :
    () => Promise<Response> = async () => {
    try{
        const response = await axios.get(`${env.url.API_BASE_URL}/payment-service/api/ticket/get/active`)
        return response;
    }catch ( e){
        return e;
    }
}
export const fetchTicket :
    () => Promise<Response> = async () => {
    try{
        const response = await axios.get(`${env.url.API_BASE_URL}/payment-service/api/ticket/all?limit=100&page=0&active=none&orderBy=price&asc=asc`)
        return response;
    }catch ( e){
        return e;
    }
}


export const fetchRoomById :
    (id : string) => Promise<Response> = async (id) =>
{
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/room/` + id);
        return response;
    }catch (e){
        return e;
    }
}

export const fetchRoomBooked :
    (id : number) => Promise<Response> = async (id) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/payment-service/api/billchair/` + id);
        return response;
    }catch (e){
        return e;
    }
}

// ------------------- subfilm -----------------
export const fetchSubFilmAll :
    () => Promise<Response> = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/subfilm/get/all`);
        return response;
    }catch (e){
        return e;
    }
}

export const fetchDish : () => Promise<Response> = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/dish-service/api/typedish/all`);
        return response;
    }catch (e){
        return e;
    }
}

export const fetchBranch : () => Promise<Response> = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/branch/all`);
        return response;
    }catch (e){
        return e;
    }
}

export const fetchFilmAll : () => Promise<Response> = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/film/all`);
        return response;
    }catch (e){
        return e;
    }
}

// ---------------- show time ------------
export const addShowTime
    : (timeStart : string, timeEnd : string, timestamp : string, subFilmId : string, roomId : string, token : string) => Promise<Response>
    = async ( timeStart, timeEnd, timestamp, subFilmId, roomId, token) => {
    try{
    const response = await  axios.post(`${env.url.API_BASE_URL}/filmshowtime-service/api/filmshowtime/add`,{
        timeStart : timeStart,
        timeEnd : timeEnd,
        timestamp : timestamp,
        subFilmId : subFilmId,
        roomId : roomId,
        status : 'ACTIVE'
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
export const deleteShowTime
    : (filmShowId : string, token : string) => Promise<Response>
    = async ( filmShowId, token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/filmshowtime-service/api/filmshowtime/delete/${filmShowId}`,{},{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
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
// ---------- BILL -----------------------
export const paymentBill :
    (bill : BillDto) => Promise<Response>
    = async ( bill ) => {
    try{
        console.log(bill);
        const response = await  axios.post(`${env.url.API_BASE_URL}/payment-service/api/bill/add`,{
            ...bill
        });
        return response;
    }catch (e){
        return e;
    }
}
export const getBillsByCustomer = async (customerId : string | undefined) => {
    try{
        const response = await axios.get(`${env.url.API_BASE_URL}/payment-service/api/bill/get/${customerId}`);
        return response;
    }catch (e){
        return e;
    }
}

export const updateAccountCustomer
    : (name : string,email : string, phone : string, id : string, token : string) => Promise<Response>
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
    (passwordNew : string, id : string, token : string) => Promise<Response>
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
    : ( year : string, token : string) => Promise<Response>
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
    : ( token : string) => Promise<Response>
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
    : ( token : string) => Promise<Response>
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

//  ------------------------------- get employe ---------------------
export  const getEmployee
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<Response>
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

export const resetEmployee
    : (id : string, token : string ) => Promise<any>
    = async (id, token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/user-service/api/employment/reset/${id}`,{},{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

//---------------------------------- film -------------------------
export const getFilm
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<Response>
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
    : () => Promise<Response>
    = async ( ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/sub/all`);
        return response;
    }catch (e){
        return e;
    }
}

export const getTypeFilm
    : () => Promise<Response>
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
    : (film : Film, token : string) => Promise<Response>
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
    : (film : Film, token : string) => Promise<Response>
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
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<Response>
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
    : ( branchId : string) => Promise<Response>
    = async ( branchId ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/room-service/api/room/get/branch/${branchId}`);
        return response;
    }catch (e){
        return e;
    }
}
export const updateBranch
    : ( branch : Branch, token : string) => Promise<Response>
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
    : ( room : Room, token : string) => Promise<Response>
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
export const createRoom
    : (room : Room,branch : Branch, token : string) => Promise<Response>
    = async (room,branch, token) => {
    try{
        console.log({
            branchId : branch.id,
            name : room.name,
            positionChair :room.positionChair,
            status : room.status
        });
        const response = await  axios.post(`${env.url.API_BASE_URL}/room-service/api/room/add`,{
            branchId : branch.id,
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
    : ( branch : Branch, token : string) => Promise<Response>
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
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<Response>
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
    : (id : string,price : number, active : string, name : string, image : string, typeDishId : string, token : string) => Promise<Response>
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
    : (id : string,price : number, active : string, name : string, image : string, typeDishId : string, token : string) => Promise<Response>
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
    : (id : string, active : string, name : string, token : string) => Promise<Response>
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
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<Response>
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
    : (id : string, active : string, conditionUse : string, name : string, price : string, typeTicket : string, slot : string , token : string) => Promise<Response>
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
    : ( active : string, conditionUse : string, name : string, price : string, typeTicket : string, slot : string , token : string) => Promise<Response>
    = async ( active, conditionUse, name, price,typeTicket,slot,token) => {
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
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<Response>
    = async ( page, limit, asc, status, orderBy, q, token  ) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/payment-service/api/bill/custom?page=${page}&limit=${limit}&asc=${asc}&active=${status}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`,{
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
    : ( name : string , email : string , pass : string, cccd : string, id : string , token : string) => Promise<Response>
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
    : (page : number, limit : number, asc : string, status : string, orderBy : string, q : string, token : string) => Promise<Response>
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

export const checkCommented
    : (filmId : string, customerId : string, token : string ) => Promise<Response>
    = async (filmId, customerId, token) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/rate-service/api/rate/check/${filmId}/${customerId}`,{
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
    : (id : string, token : string) => Promise<Response>
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
    : (id : string, token : string) => Promise<Response>
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
    :(star : number, content : string, customerId : string, filmId : string, token : string) => Promise<Response>
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
    : (page : number, limit : number, asc : string, orderBy : string, q : string) => Promise<Response>
    = async (page, limit,asc,orderBy,q) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/sub/custom?page=${page}&limit=${limit}&asc=${asc}&orderBy=${orderBy}` + `${q == '' ? '' : '&q=' + q}`);
        return response;
    }catch (e){
        return e;
    }
}
export const deleteSubById
    : (id : string, token : string) => Promise<Response>
    = async (id, token) => {
    try{
        const response = await  axios.delete(`${env.url.API_BASE_URL}/film-service/api/sub/delete/${id}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}

export const createSub
    : (name : string, token : string) => Promise<Response>
    = async (name, token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/film-service/api/sub/add`, {
            id : '',
            name : name
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
export const updateSub
    : (id : string,name : string, token : string) => Promise<Response>
    = async (id,name, token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/film-service/api/sub/update/${id}`, {
            id : id,
            name : name
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
export const getSubFilmCustom
    : (subId : string, page : number, limit : string, asc : string,  token : string) => Promise<Response>
    = async (subId, page, limit, asc, token) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/subfilm/get/sub/${subId}?page=${page}&limit=${limit}&asc=${asc}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
        return response;
    }catch (e){
        return e;
    }
}
export const getFilmNotInSub
    : (subId : string) => Promise<Response>
    = async (subId) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/film/get/sub/${subId}`);
        return response;
    }catch (e){
        return e;
    }
}
export const addFilmIntoSub
    : (subId : string, filmId : string, token : string) => Promise<Response>
    = async (subId, filmId, token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/film-service/api/subfilm/add`,{
            subId : subId,
            filmId : filmId
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
export const deleteSubFilm
    : (filmId : string,subId : string, token : string ) => Promise<Response>
    = async (filmId, subId,token) => {
    try{
        const response = await  axios.delete(`${env.url.API_BASE_URL}/film-service/api/subfilm/delete/${filmId}/${subId}`,{
            headers : {
                Authorization : 'Bearer ' + token
            }
        });
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

// ----------------- slider ----------------------------
export const getSlider
    : () => Promise<any>
    = async () => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/slider/get`);
        return response;
    }catch (e){
        return e;
    }
}
export const uploadSlider
    : (name : string,image : string, token : string) => Promise<any>
    = async (name,image, token) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/film-service/api/slider/add/slider`,
            {
                name : name,
                image : image
            }, {
                headers : {
                    Authorization : 'Bearer ' + token
                }
            });
        return response;
    }catch (e){
        return e;
    }
}
export const removeSlider
    : (uid : string, token) => Promise<any>
    = async (uid, token) => {
    try{
        const response = await  axios.delete(`${env.url.API_BASE_URL}/film-service/api/slider/remove/${uid}`,
            {
                headers : {
                    Authorization : 'Bearer ' + token
                }
            });
        return response;
    }catch (e){
        return e;
    }
}

// -------------- contact -------------
export const addContact
    : (name : string, numberPhone : string, content : string) => Promise<any>
    = async (name, numberPhone, content) => {
    try{
        const response = await  axios.post(`${env.url.API_BASE_URL}/user-service/api/contact/add`
        ,{
            id: '',
            name : name,
            numberPhone : numberPhone,
            content : content,
            status : "ACTIVE",
            timestamp : new Date().toISOString()
        });
        return response;
    }catch (e){
        return e;
    }
}
export const getAllContact
    : (page : number, limit : string, q: string, asc : string , orderBy : string, status : string, token : string) => Promise<any>
    = async (page,limit,q,asc,orderBy,status,token) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/user-service/api/contact/get/contact?page=${page}&limit=${limit}&asc=${asc}&orderBy=${orderBy}&status=${status}` + `${q == '' ? '' : '&q=' + q}`
        ,  {
                headers : {
                    Authorization : 'Bearer ' + token
                }
            }
        );
        return response;
    }catch (e){
        return e;
    }
}
export const checkContact
    :( id : number, token : string) => Promise<Response>
    = async  (id,token) => {
    try{
        const response = await  axios.put(`${env.url.API_BASE_URL}/user-service/api/contact/check/${id}`,{}
            ,  {
                headers : {
                    Authorization : 'Bearer ' + token
                }
            }
        );
        return response;
    }catch (e){
        return e;
    }
}

// ------------------------ Revenue ------------------------
export const getRevenueFilm
    : (month: string | number, year : string | number) => Promise<Response>
    = async (month, year) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/revenue/film/all/${month}/${year}`);
        return response;
    }catch (e){
        return e;
    }
}

export const getRevenueFilmByFilmId
    : (filmId : string) => Promise<Response>
    = async (filmId) => {
    try{
        const response = await  axios.get(`${env.url.API_BASE_URL}/film-service/api/revenue/film/${filmId}`);
        return response;
    }catch (e){
        return e;
    }
}

// ---------------- login social -------------------
export const getSocialLogin = (name : string) => {
    return `${env.url.API_BASE_URL}/oauth2/authorization/${name}?redirect_uri=${env.url.OAUTH2_REDIRECT_URI}`
}