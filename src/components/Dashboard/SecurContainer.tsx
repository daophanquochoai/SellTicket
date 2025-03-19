import {Outlet, useNavigate} from "react-router-dom";
import React, {lazy, useEffect, useState} from "react";
import {getToken, parseJwt} from "../../Helper/Helper.ts";

const LoginAdmin = lazy(() => import('../LoginAdmin/LoginAdmin.tsx'));

interface User {
    email : string,
    id : string,
    name : string,
    roles : []
    sub : string,
    phone : string
}


const SecurContainer: React.FC = () => {
    const [user, setUser] = useState<User | null>(null);
    const navigate = useNavigate();
    const data: string | undefined = getToken();

    useEffect(() => {
        if (data !== undefined) {
            const parsedUser = parseJwt(data);
            setUser(parsedUser);
            console.log(parsedUser);
        }
    }, [data]);

    if (!data) {
        return <LoginAdmin />;
    }

    if (!user) {
        return <p className="text-white">Đang kiểm tra quyền truy cập...</p>;
    }

    if (user.roles[0] !== "ROLE_ADMIN") {
        return <p className="text-white">Không cho phép truy cập</p>;
    }

    return <Outlet />;
};

export default SecurContainer;

