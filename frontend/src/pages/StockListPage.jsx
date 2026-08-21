import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Table, Button, Space, Typography, Input, message } from "antd";
import { apiFetch } from "../api/client";
import { useAuth } from "../state/AuthContext";
import { formatNumber } from "../utils/format";

const { Title } = Typography;

// 漲跌停判斷門檻：因為檔位捨去規則，實際漲跌停很少剛好是 10.000%，用 9.5% 當簡化門檻
const LIMIT_THRESHOLD = 9.5;

function renderPriceCell(stock) {
  const { price, previousClose } = stock;

  // 還沒有昨收價（例如剛新增、還沒同步過第二次），沒有基準可以算漲跌，純顯示現價
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

  // 漲停/跌停：紅底白字、綠底白字
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

  // 一般漲跌：紅漲綠跌文字
  return (
    <span style={{ color: isUp ? 'red' : isDown ? 'green' : 'black' }}>
      {formatNumber(price)}（{percentText}）
    </span>
  );
}

function StockListPage() {
  const [stocks, setStocks] = useState([]);
  const [keyword, setKeyword] = useState('');
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const { isLogin } = useAuth();
  const [page , setPage] = useState(0);
  const [totalSizes , setTotalSizes] = useState(0);


  const handleAddToWatchlist = async (stockId) => {
    try {
      await apiFetch('/watchlist', {
        method: 'POST',
        body: JSON.stringify({ stockId }),
      });
      message.success('已加入自選清單');
    } catch (err) {
      message.error('加入失敗：' + err.message);
    }
  };

  useEffect(() => {
    async function loadStocks() {

      const result = await apiFetch(`/stock?page=${page}&size=10&keyword=${keyword}`);
      setStocks(result.data.content);
      setTotalSizes(result.data.totalElements);
      setLoading(false);
    }
    loadStocks();
  }, [ page , keyword ]);

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
          {isLogin && <Button onClick={() => handleAddToWatchlist(stock.stockId)}>加入自選</Button>}
        </Space>
      ),
    },
  ];

  return (
    <div style={{ padding: 24 }}>
      <Title level={2}>股票清單</Title>
      <Input.Search
        placeholder="搜尋股票代碼或名稱"
        allowClear
        value={keyword}
        onChange={(e) => {setKeyword(e.target.value) ;  setPage(0)}}
        style={{ maxWidth: 320, marginBottom: 16 }}
      />
      <Table rowKey="stockId" columns={columns} dataSource={stocks} loading={loading} pagination={{
        current:page+1,
        pageSize:10,
        total:totalSizes,
        onChange: (newPage) => {setPage(newPage-1)}
      }} />
    </div>
  );
}

export default StockListPage;
