import * as React from "react";
import {lazy, Suspense} from "react";
import {useRoutes} from "react-router-dom";
import { ToastContainer } from 'react-toastify';
import DetailFilm from "./components/DetailFilm/DetailFilm.tsx";
const LoginPage = lazy(() => import("./components/LoginPage/LoginPage.tsx"));
const SignUpPage = lazy(() => import("./components/SignUpPage/SignUp.tsx"));
const ForgetPasswordPage = lazy(() => import("./components/ForgetPasswordPage/ForgetPassword.tsx"))
const LoadingPage = lazy(() => import("./components/LoadingPage/LoadingPage.tsx"));
const HomePage = lazy(() => import("./components/HomePage/HomePage.tsx"))
const ContainerPage  = lazy(()  => import("./components/CommonPage/ContainerPage.tsx"))

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
