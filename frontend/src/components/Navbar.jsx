import { Layout, Menu, Button, Space, Typography } from "antd";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { useAuth } from "../state/AuthContext";

const { Header } = Layout;
const { Text } = Typography;

function Navbar() {
  const { user, isLogin, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const menuItems = [
    { key: '/', label: <Link to="/">股票清單</Link> },
    ...(isLogin ? [{ key: '/watchlist', label: <Link to="/watchlist">自選股</Link> }] : []),
    ...(isLogin ? [{ key: '/positions', label: <Link to="/positions">持倉</Link> }] : []),
    ...(isLogin ? [{ key: '/orders'   , label: <Link to="/orders">歷史訂單</Link>}] : []),
  ];

  return (
    <Header
      style={{
        display: 'flex',
        alignItems: 'center',
        background: '#5b7b8c',
        gap: 24,
      }}
    >
      {/* 品牌名稱固定寬度，不參與伸縮 */}
      <Link
        to="/"
        style={{ color: 'white', fontSize: 18, fontWeight: 'bold', flex: '0 0 auto', whiteSpace: 'nowrap' }}
      >
        股票模擬交易系統
      </Link>

      {/*
        Menu 要能真正依視窗寬度自動收合，容器一定要 flex: 1（跟著可用空間伸縮）
        且 minWidth: 0（覆蓋 flex item 預設的 min-width: auto，不然瀏覽器不會讓它縮小到比內容還窄，
        導致收合永遠不會被觸發，或觸發時機跟視窗寬度對不上）
      */}
      <Menu
        theme="dark"
        mode="horizontal"
        selectedKeys={[location.pathname]}
        items={menuItems}
        style={{ flex: '1 1 auto', minWidth: 0, background: 'transparent' }}
      />

      {/* 使用者資訊區固定寬度，不參與伸縮 */}
      <Space style={{ flex: '0 0 auto' }}>
        {isLogin ? (
          <>
            <Text style={{ color: 'white', whiteSpace: 'nowrap' }}>{user.sub}（{user.role}）</Text>
            <Button onClick={handleLogout}>登出</Button>
          </>
        ) : (
          <Link to="/login">
            <Button type="primary">登入</Button>
          </Link>
        )}
      </Space>
    </Header>
  );
}

export default Navbar;
