import { useEffect, useState } from "react";
import { Table, Tag, Typography, Card, Statistic } from "antd";
import { apiFetch } from "../api/client";
import { formatNumber } from "../utils/format";

const { Title } = Typography;

function OrderHistoryPage(){

    const [orders , setOrders] = useState([]);
    const [loading , setLoading] = useState(true);

    useEffect(() =>{
        async function loadingOrders() {
            const result = await apiFetch('/order');
            setOrders(result.data);
            setLoading(false);
        }
        loadingOrders();
    },[]);

    // 加總所有已實現損益
    const totalRealizedPnl = orders.reduce(
        (sum, order) => sum + Number(order.realizedPnl ?? 0),
        0
    );
    
    const columns = [
        {
            title: '時間',
            dataIndex: 'createdAt',
            key: 'createdAt',
            render: (value) => new Date(value).toLocaleString('zh-TW'),
        },
        { title: '股票代碼', dataIndex: 'stockId', key: 'stockId' },
        { title: '股票名稱', dataIndex: 'stockName', key: 'stockName' },
        {
            title: '買賣別',
            dataIndex: 'orderType',
            key: 'orderType',
            render: (value) => (
                <Tag color={value === 'BUY' ? 'red' : 'green'}>{value === 'BUY' ? '買進' : '賣出'}</Tag>
            ),
        },
        {
            title: '成交價',
            dataIndex: 'price',
            key: 'price',
            render: (value) => formatNumber(value),
        },
        {
            title: '股數',
            dataIndex: 'shares',
            key: 'shares',
            render: (value) => formatNumber(value),
        },
        {
            title: '已實現損益',
            dataIndex: 'realizedPnl',
            key: 'realizedPnl',
            render: (value) => {
                if (value == null) return '-';
                return (
                    <span style={{ color: value > 0 ? 'red' : value < 0 ? 'green' : 'black' }}>
                        {formatNumber(value)}
                    </span>
                );
            },
        },
        {
            title: '狀態',
            dataIndex: 'orderStatus',
            key: 'orderStatus',
            render: (value, record) => {
                if (value === 'FILLED') return <Tag color="blue">成交</Tag>;
                return (
                    <Tag color="default" title={record.failReason}>
                        失敗{record.failReason ? `（${record.failReason}）` : ''}
                    </Tag>
                );
            },
        },
    ];

    

    return (
        <div style={{ padding: 24 }}>
            <Title level={2}>歷史訂單</Title>
            <Card style={{ marginBottom: 16, maxWidth: 280 }}>
                <Statistic
                    title="總已實現損益"
                    value={totalRealizedPnl}
                    precision={2}
                    valueStyle={{ color: totalRealizedPnl > 0 ? 'red' : totalRealizedPnl < 0 ? 'green' : 'black' }}
                />
            </Card>
            <Table rowKey="id" columns={columns} dataSource={orders} loading={loading} />
        </div>
    );
}

export default OrderHistoryPage;