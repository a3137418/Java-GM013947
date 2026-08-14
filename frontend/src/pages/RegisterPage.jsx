import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Form, Input, Select, Button, Card, message } from "antd";
import { UserOutlined, LockOutlined, MailOutlined } from "@ant-design/icons";
import { apiFetch } from "../api/client";

const CAPITAL_OPTIONS = [
  { value: 'TEN_W', label: '10 萬' },
  { value: 'ONE_HUNDRED_W', label: '100 萬' },
  { value: 'ONE_THOUSAND_W', label: '1000 萬' },
];

function RegisterPage() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const onFinish = async (values) => {
    setLoading(true);
    try {
      await apiFetch('/auth/register', {
        method: 'POST',
        body: JSON.stringify(values),
      });
      message.success('註冊成功，請登入');
      navigate('/login');
    } catch (err) {
      message.error('註冊失敗：' + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', marginTop: 80 }}>
      <Card title="註冊" style={{ width: 360 }}>
        <Form onFinish={onFinish} layout="vertical">
          <Form.Item
            name="username"
            label="帳號"
            rules={[
              { required: true, message: '請輸入帳號' },
              { min: 3, max: 50, message: '帳號長度必須介於 3 到 50' },
            ]}
          >
            <Input prefix={<UserOutlined />} />
          </Form.Item>
          <Form.Item
            name="password"
            label="密碼"
            rules={[
              { required: true, message: '請輸入密碼' },
              { min: 6, message: '密碼長度至少 6 個字' },
            ]}
          >
            <Input.Password prefix={<LockOutlined />} />
          </Form.Item>
          <Form.Item
            name="email"
            label="Email"
            rules={[
              { required: true, message: '請輸入 Email' },
              { type: 'email', message: 'Email 格式不正確' },
            ]}
          >
            <Input prefix={<MailOutlined />} />
          </Form.Item>
          <Form.Item
            name="initialCapital"
            label="初始資金"
            rules={[{ required: true, message: '請選擇初始資金' }]}
          >
            <Select options={CAPITAL_OPTIONS} placeholder="請選擇初始資金" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading} block>
              註冊
            </Button>
          </Form.Item>
        </Form>
        <div style={{ textAlign: 'center' }}>
          已經有帳號？<Link to="/login">登入</Link>
        </div>
      </Card>
    </div>
  );
}

export default RegisterPage;
