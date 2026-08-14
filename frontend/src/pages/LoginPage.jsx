// useState 用來儲存表單欄位資料(狀態)
import { useState } from "react";

// useNavigate 可以用程式導頁
import { useNavigate } from "react-router-dom";

// 匯入 useAuth 取得 login 方法
import { useAuth } from '../state/AuthContext'

function LoginPage() {
    // 帳號
    const [username, setUsername] = useState('test001');

    // 密碼
    const [password, setPassword] = useState('123456');

    // error 儲存登入失敗訊息
    const [error, setError] = useState('');

    // 從 AuthContext 取得 login 方法
    const { login } = useAuth();

    // navigate 用來導頁
    const navigate = useNavigate();

    // 送出表單
    const handleSubmit = async (e) => {
        // 停止表單預設行為
        e.preventDefault();

        // 印 log
        // console.log('帳號:', username);
        // console.log('密碼:', password);
        //alert(`帳號: ${username}, 密碼: ${password}`);

        // 每次送出前先清空錯誤訊息
        setError('');

        try {
            // 呼叫 AuthContext 裡面的 login 方法
            const user = await login(username, password);

            // 如果是管理者登入後進入首頁
            // 如果是一般會員登入後進入首頁
            navigate('/');
        } catch (error) {
            // 登入失敗顯示錯誤訊息
            //setError(error.message);
            setError('登入失敗 ' + error.message);
        }
        
    }

    return (
        <section className="panel narrow">
            <h1>登入</h1>
            
            {/* <p className="hint">
                管理者: admin / admin123 ; 會員: user / user123
            </p> */}

            {/* 如果有 error 內容就顯示錯誤 */}
            {error && <div className="alert error">{ error }</div>}

            <form onSubmit={handleSubmit} className="form">
                <label>帳號</label>
                <input value={username} 
                    onChange={(e) => setUsername(e.target.value)} />
                <label>密碼</label>
                <input value={password} 
                    type="password"
                    onChange={(e) => setPassword(e.target.value)} />
                
                <button type="submit">登入</button>
            </form>

        </section>
    )
}

export default LoginPage;