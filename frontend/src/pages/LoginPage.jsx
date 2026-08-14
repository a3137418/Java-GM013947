import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Form, Input, Button, Card, message } from "antd";
import { UserOutlined, LockOutlined } from "@ant-design/icons";
import { useAuth } from "../state/AuthContext";

function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const onFinish = async (values) => {
    setLoading(true);
    try {
      await login(values.username, values.password);
      message.success('登入成功');
      navigate('/');
    } catch (err) {
      message.error('登入失敗：' + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', marginTop: 80 }}>
      <Card title="登入" style={{ width: 360 }}>
        <Form onFinish={onFinish} layout="vertical">
          <Form.Item name="username" label="帳號" rules={[{ required: true, message: '請輸入帳號' }]}>
            <Input prefix={<UserOutlined />} />
          </Form.Item>
          <Form.Item name="password" label="密碼" rules={[{ required: true, message: '請輸入密碼' }]}>
            <Input.Password prefix={<LockOutlined />} />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading} block>
              登入
            </Button>
          </Form.Item>
        </Form>
        <div style={{ textAlign: 'center' }}>
          還沒有帳號？<Link to="/register">註冊</Link>
        </div>
      </Card>
    </div>
  );
}

export default LoginPage;
