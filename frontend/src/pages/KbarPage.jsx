import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import ReactECharts from "echarts-for-react";
import { Card, Typography } from "antd";
import { apiFetch } from "../api/client";

const { Title } = Typography;

function KbarPage() {
  const { stockId } = useParams();
  const [kbars, setKbars] = useState([]);

  useEffect(() => {
    async function loadKbars() {
      const result = await apiFetch(`/kbar/${stockId}`);
      setKbars(result.data);
    }
    loadKbars();
  }, [stockId]);

  const dates = kbars.map((k) => k.date);
  const values = kbars.map((k) => [k.open, k.close, k.low, k.high]);

  const option = {
    xAxis: { type: 'category', data: dates },
    yAxis: { type: 'value', scale: true },
    series: [{
      type: 'candlestick',
      data: values,
      itemStyle: { color: 'red', color0: 'green', borderColor: 'red', borderColor0: 'green' },
    }],
    tooltip: { trigger: 'axis' },
  };

  return (
    <div style={{ padding: 24 }}>
      <Title level={2}>{stockId} K 線圖</Title>
      <Card>
        <ReactECharts option={option} style={{ height: '500px' }} />
      </Card>
    </div>
  );
}

export default KbarPage;
