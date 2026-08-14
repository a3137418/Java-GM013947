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
  ];

  return (
    <Header style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', background: '#5b7b8c' }}>
      <Space size="large">
        <Link to="/" style={{ color: 'white', fontSize: 18, fontWeight: 'bold' }}>
          股票模擬交易系統
        </Link>
        <Menu
          theme="dark"
          mode="horizontal"
          selectedKeys={[location.pathname]}
          items={menuItems}
          style={{ minWidth: 200, background: 'transparent' }}
        />
      </Space>

      <Space>
        {isLogin ? (
          <>
            <Text style={{ color: 'white' }}>{user.sub}（{user.role}）</Text>
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
