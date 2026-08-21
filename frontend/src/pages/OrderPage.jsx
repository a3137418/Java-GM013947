import { useState , useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Form, InputNumber, Button, Card, message, Descriptions } from "antd";
import { apiFetch } from "../api/client";

function OrderPage() {
  const { stockId, action } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const [stock , setStock] = useState(null);

  const onFinish = async (values) => {
    setLoading(true);
    try {
      await apiFetch(`/order/${action}`, {
        method: 'POST',
        body: JSON.stringify({ stockId, shares: values.shares }),
      });
      message.success('下單成功！');
      navigate('/');
    } catch (err) {
      message.error('下單失敗：' + err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    async function loadStock() {
      const result = await apiFetch(`/stock/${stockId}`)
      setStock(result.data)
    }
    loadStock();
  }, [stockId])

  return (
    <div style={{ display: 'flex', justifyContent: 'center', marginTop: 80 }}>
      <Card title={`${action === 'buy' ? '買進' : '賣出'} ${stockId}`} style={{ width: 360 }}>
        {stock && (
          <Descriptions column={1} style={{ marginBottom: 16 }}>
            <Descriptions.Item label="股票名稱">{stock.stockName}</Descriptions.Item>
            <Descriptions.Item label="現價">{stock.price}</Descriptions.Item>
          </Descriptions>
        )}
        <Form onFinish={onFinish} layout="vertical">
          <Form.Item name="shares" label="股數" rules={[{ required: true, message: '請輸入股數' }]}>
            <InputNumber min={1} style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading} block>
              送出
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
}

export default OrderPage;
