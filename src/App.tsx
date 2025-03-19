import * as React from "react";
import {lazy, Suspense} from "react";
import {useRoutes} from "react-router-dom";
import { ToastContainer } from 'react-toastify';
import DetailFilm from "./components/DetailFilm/DetailFilm.tsx";

const LoginPage = lazy(() => import("./components/LoginPage/LoginPage.tsx"));
const SignUpPage = lazy(() => import("./components/SignUpPage/SignUp.tsx"));
const ForgetPasswordPage = lazy(() => import("./components/ForgetPasswordPage/ForgetPassword.tsx"));
const LoadingPage = lazy(() => import("./components/LoadingPage/LoadingPage.tsx"));
const HomePage = lazy(() => import("./components/HomePage/HomePage.tsx"));
const ContainerPage  = lazy(()  => import("./components/CommonPage/ContainerPage.tsx"));
const PaymentPage = lazy(() => import("./components/PaymentPage/PaymentPage.tsx"));
const TheaterMovie = lazy(() => import("./components/TheaterMovie/TheaterMovie.tsx"));
const Intro = lazy(() => import('./components/Intro/Intro.tsx'));
const CommonDash = lazy(() => import('./components/Dashboard/CommonDash.tsx'));
const SecurContainer = lazy(() => import('./components/Dashboard/SecurContainer.tsx'));
const LoginAdmin = lazy(() => import('./components/LoginAdmin/LoginAdmin.tsx'));

const routes  = [
    {
        path : '/login',
        element : (
            <Suspense fallback={<LoadingPage/>}>
                <LoginPage />
            </Suspense>
        )
    },
    {
        path: "/signup",
        element: (
            <Suspense fallback={<LoadingPage/>}>
                <SignUpPage />
            </Suspense>
        )
    },
    {
        path: "/forgetpassword",
        element: (
            <Suspense fallback={<LoadingPage />}>
                <ForgetPasswordPage />
            </Suspense>
        )
    },
    {
        path: "/dashboard",
        element: (
            <Suspense fallback={<LoadingPage />}>
                <SecurContainer />
            </Suspense>
        ),
        children: [
            {
                index : true,
                element: (
                    <Suspense fallback={<LoadingPage />}>
                        <CommonDash />
                    </Suspense>
                ),
            },
            {
                path : 'login',
                element: (
                    <Suspense fallback={<LoadingPage />}>
                        <LoginAdmin />
                    </Suspense>
                ),
            },
        ]
    },
    {
        path: "/",
        element: (
            <Suspense fallback={<LoadingPage />}>
                <ContainerPage />
            </Suspense>
        ),
        children : [
            {
                index : true,
                element: (
                    <Suspense fallback={<LoadingPage />}>
                        <HomePage />
                    </Suspense>
                ),
            },
            {
                path : '/film/:id',
                element: (
                    <Suspense fallback={<LoadingPage />}>
                        <DetailFilm />
                    </Suspense>
                ),
            },
            {
                path : '/payment',
                element: (
                    <Suspense fallback={<LoadingPage />}>
                        <PaymentPage />
                    </Suspense>
                ),
            },
            {
                path : '/theater',
                element: (
                    <Suspense fallback={<LoadingPage />}>
                        <TheaterMovie />
                    </Suspense>
                ),
            },
            {
                path : '/intro',
                element: (
                    <Suspense fallback={<LoadingPage />}>
                        <Intro />
                    </Suspense>
                ),
            }
        ]
    }
]

const App:React.FC = () => {
    const element = useRoutes(routes);

  return (
      <>
          <ToastContainer autoClose={1000}/>
          {element}
      </>
  )
}

export default App;
