import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import { ConfigProvider } from 'antd'
import 'antd/dist/reset.css'
import './index.css'
import App from './App.jsx'
import { AuthProvider } from './state/AuthContext.jsx'

// 日系風格主題：低飽和度、米白背景、灰藍/抹茶綠主色
const theme = {
  token: {
    colorPrimary: '#5b7b8c',       // 灰藍（藍染色系）
    colorBgLayout: '#f7f4ef',      // 米白背景
    colorBgContainer: '#ffffff',
    colorText: '#4a4640',          // 暖灰黑，不用純黑
    colorBorder: '#e0dbd1',
    borderRadius: 6,
    fontFamily: "system-ui, 'Segoe UI', 'Noto Sans TC', sans-serif",
  },
}

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <ConfigProvider theme={theme}>
          <App />
        </ConfigProvider>
      </AuthProvider>
    </BrowserRouter>
  </StrictMode>,
)
