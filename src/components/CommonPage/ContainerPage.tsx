import Footer from "../HomePage/Footer.tsx";
import React from "react";
import Header from "../HomePage/Header.tsx";
import {Outlet} from "react-router-dom";

const ContainerPage : React.FC = () => {
    return(
        <>
            <Header/>
            <Outlet />
            <Footer/>
        </>
    )
}
export default ContainerPage;