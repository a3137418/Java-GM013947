import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Table, Button, Space, Typography, message, Popconfirm } from "antd";
import { apiFetch } from "../api/client";
import { formatNumber } from "../utils/format";

const { Title } = Typography;

const LIMIT_THRESHOLD = 9.5;

function renderPriceCell(stock) {
  const { price, previousClose } = stock;

  if (previousClose == null) {
    return <span>{formatNumber(price)}</span>;
  }

  const change = price - previousClose;
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
        {formatNumber(price)}（{percentText}）
      </span>
    );
  }

  return (
    <span style={{ color: isUp ? 'red' : isDown ? 'green' : 'black' }}>
      {price}（{percentText}）
    </span>
  );
}

function WatchlistPage() {
  const [watchlist, setWatchlist] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const loadWatchlist = async () => {
    setLoading(true);
    const result = await apiFetch('/watchlist');
    setWatchlist(result.data);
    setLoading(false);
  };

  useEffect(() => {
    loadWatchlist();
  }, []);

  const handleRemove = async (stockId) => {
    try {
      await apiFetch(`/watchlist/${stockId}`, { method: 'DELETE' });
      message.success('已從自選清單移除');
      loadWatchlist();
    } catch (err) {
      message.error('移除失敗：' + err.message);
    }
  };

  const columns = [
    { title: '股票代碼', dataIndex: 'stockId', key: 'stockId' },
    { title: '股票名稱', dataIndex: 'stockName', key: 'stockName' },
    {
      title: '現價（漲跌幅）',
      key: 'price',
      render: (_, stock) => renderPriceCell(stock),
    },
    {
      title: '操作',
      key: 'action',
      render: (_, stock) => (
        <Space>
          <Button onClick={() => navigate(`/kbar/${stock.stockId}`)}>日K線圖</Button>
          <Button type="primary" onClick={() => navigate(`/order/${stock.stockId}/buy`)}>買進</Button>
          <Button danger onClick={() => navigate(`/order/${stock.stockId}/sell`)}>賣出</Button>
          <Popconfirm
            title="確定要從自選清單移除嗎？"
            onConfirm={() => handleRemove(stock.stockId)}
          >
            <Button>移除自選</Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div style={{ padding: 24 }}>
      <Title level={2}>自選股</Title>
      <Table rowKey="stockId" columns={columns} dataSource={watchlist} loading={loading} />
    </div>
  );
}

export default WatchlistPage;
