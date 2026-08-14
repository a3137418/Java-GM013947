// 保護會員頁面與管理者頁面
// 未登入者不可以進入我的管理頁面
// 一般會員不能進入管理者頁面

import { Navigate } from "react-router-dom";
import { useAuth } from "../state/AuthContext";

function ProtectedRoute({ children, adminOnly = false}) {

    const {isLogin, isAdmin} = useAuth();

    // 如果沒有登入就導回登入頁
    if(!isLogin) {
        return <Navigate to="/login" replace />
    }

    // 如果此頁面需要管理者角色, 但是目前的登入角色不是管理者, 就導回首頁
    if(adminOnly && !isAdmin) {
        return <Navigate to="/login" replace />
    }

    return children;
}

export default ProtectedRoute;