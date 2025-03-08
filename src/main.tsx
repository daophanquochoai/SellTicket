import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {BrowserRouter} from "react-router-dom";
import CommonProvider from "./context/CommonContext.tsx";

createRoot(document.getElementById('root')!).render(
    <BrowserRouter>
        <CommonProvider>
            <App/>
        </CommonProvider>
    </BrowserRouter>
)
