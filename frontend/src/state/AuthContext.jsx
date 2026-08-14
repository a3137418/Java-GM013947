/**
 * 建立全系統共用的登入狀態管理
 * createContext: 建立 Context
 * useContext: 使用 Context
 * useEffect: 當 token 改變時同步更新到 localStorage
 * useMemo: 避免 value 每次都重新建立
 * useState: 儲存登入狀態
 */

import { createContext , useContext , useEffect , useMemo , useState } from "react";

// 匯入 API 工具
import { apiFetch } from "../api/client";

// 匯入 JWT 解碼工具
import { decodeToken } from "../api/jwt";

// 建立 AuthContext
const AuthContext = createContext(null);

export function AuthProvider({children}){
    // 從 localStorage 讀取 token
    const [token , setToken] = useState(() => localStorage.getItem('token'));

    // user 不是後端另外回傳的，是從 token 解碼出來的
    // （這裡跟 frontend-rental 不一樣：LoginResponse 只有 token，沒有 user 物件）
    const [user , setUser] = useState(() => token ? decodeToken(token) : null);

    // token 改變時：同步更新 localStorage，並重新解碼出對應的 user
    useEffect(() => {
        if(token){
            localStorage.setItem('token' , token);
            setUser(decodeToken(token));
        }else{
            localStorage.removeItem('token');
            setUser(null);
        }
    },[token]);

    
    // login 方法：負責呼叫後端登入 API
    const login = async(username , password) => {
        const result = await apiFetch('/auth/login' , {
            method : 'POST',
            body: JSON.stringify({username , password})
        });

        // 後端只回傳 token，把它存起來
        setToken(result.data.token);

        // 把剛拿到的token解碼，回傳給 loginPage 判斷要導去哪一頁
        return decodeToken(result.data.token);
    };

    // logout 方法：清空 token（useEffect 會自動連 user、localStorage 一起清掉）
    const logout = () => setToken(null);

    const value = useMemo(() => ({
        user,
        token,
        // 有token 就代表已登入
        isLogin: !!token && !!user ,
        // JWT payload 裡的 role 是 ADMIN 就 代表管理者
        isAdmin: user?.role == 'ADMIN',
        login,
        logout,
    }) , [user , token]);

    return (
        <AuthContext.Provider value = {value}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth(){
        return useContext(AuthContext);
}