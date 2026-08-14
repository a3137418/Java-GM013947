import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Table, Button, Space, Typography, Descriptions, Card } from "antd";
import { apiFetch } from "../api/client";
import { formatNumber } from "../utils/format";

const { Title } = Typography;

// 漲跌停判斷門檻：因為檔位捨去規則，實際漲跌停很少剛好是 10.000%，用 9.5% 當簡化門檻
const LIMIT_THRESHOLD = 9.5;

function renderPriceCell(currentPrice, previousClose) {
  if (previousClose == null) {
    return <span>{formatNumber(currentPrice)}</span>;
  }

  const change = currentPrice - previousClose;
  const changePercent = (change / previousClose) * 100;
  const isUp = change > 0;
  const isDown = change < 0;
  const isLimit = Math.abs(changePercent) >= LIMIT_THRESHOLD;
  const sign = isUp ? '+' : '';
  const percentText = `${sign}${changePercent.toFixed(2)}%`;

  if (isLimit) {
    return (
      <span
        style={{
          display: 'inline-block',
          padding: '2px 8px',
          borderRadius: 4,
          color: 'white',
          background: isUp ? 'red' : 'green',
        }}
      >
        {formatNumber(currentPrice)}（{percentText}）
      </span>
    );
  }

  return (
    <span style={{ color: isUp ? 'red' : isDown ? 'green' : 'black' }}>
      {currentPrice}（{percentText}）
    </span>
  );
}

function PositionPage() {
  const [positions, setPositions] = useState([]);
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    async function loadData() {
      const [positionResult, meResult] = await Promise.all([
        apiFetch('/position'),
        apiFetch('/auth/me'),
      ]);
      setPositions(positionResult.data);
      setProfile(meResult.data);
      setLoading(false);
    }
    loadData();
  }, []);

  const columns = [
    { title: '股票代碼', dataIndex: 'stockId', key: 'stockId' },
    { title: '股票名稱', dataIndex: 'stockName', key: 'stockName' },
    {
      title: '現價（漲跌幅）',
      key: 'currentPrice',
      render: (_, position) => renderPriceCell(position.currentPrice, position.previousClose),
    },
    {
      title: '成本均價',
      dataIndex: 'costPrice',
      key: 'costPrice',
      render: (value) => formatNumber(value),
    },
    {
      title: '未實現損益',
      dataIndex: 'unrealizedPnl',
      key: 'unrealizedPnl',
      render: (value) => (
        <span style={{ color: value > 0 ? 'red' : value < 0 ? 'green' : 'black' }}>
          {formatNumber(value)}
        </span>
      ),
    },
    {
      title: '股數',
      dataIndex: 'shares',
      key: 'shares',
      render: (value) => formatNumber(value),
    },
    {
      title: '操作',
      key: 'action',
      render: (_, position) => (
        <Space>
          <Button onClick={() => navigate(`/kbar/${position.stockId}`)}>日K線圖</Button>
          <Button type="primary" onClick={() => navigate(`/order/${position.stockId}/buy`)}>買進</Button>
          <Button danger onClick={() => navigate(`/order/${position.stockId}/sell`)}>賣出</Button>
        </Space>
      ),
    },
  ];

  // 加總所有持股的未實現損益
  const totalUnrealizedPnl = positions.reduce(
    (sum, position) => sum + Number(position.unrealizedPnl ?? 0),
    0
  );

  return (
    <div style={{ padding: 24 }}>
      <Title level={2}>持倉</Title>

      {profile && (
        <Card style={{ marginBottom: 24 }} loading={loading}>
          <Descriptions title="帳戶資訊" column={3}>
            <Descriptions.Item label="帳號">{profile.username}</Descriptions.Item>
            <Descriptions.Item label="Email">{profile.email}</Descriptions.Item>
            <Descriptions.Item label="虛擬餘額">{formatNumber(profile.assets)}</Descriptions.Item>
            <Descriptions.Item label="未實現總損益">
              <span style={{ color: totalUnrealizedPnl > 0 ? 'red' : totalUnrealizedPnl < 0 ? 'green' : 'black' }}>
                {formatNumber(totalUnrealizedPnl)}
              </span>
            </Descriptions.Item>
          </Descriptions>
        </Card>
      )}

      <Table rowKey="stockId" columns={columns} dataSource={positions} loading={loading} />
    </div>
  );
}

export default PositionPage;
