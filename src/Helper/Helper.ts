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
        const response = await axios.get(`${env.url.API_BASE_URL}/rate-service/api/rate/film/` + filmId + `?page=0&limit=2&asc=asc&orderBy=timeStamp&status=ACTIVE`)
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