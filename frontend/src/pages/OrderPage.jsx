import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiFetch } from "../api/client";


function OrderPage(){

    // 從網址拿到 stockId and action (buy or sell)
    const {stockId , action} = useParams();

    const [shares , setShares] = useState('');
    const [error , setError] = useState('');
    const [success , setSuccess] = useState('');

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');

        try {
            const result = await apiFetch(`/order/${action}` , {
                method: 'POST',
                body: JSON.stringify({
                    stockId,
                    shares: Number(shares)
                })
            });
            setSuccess('下單成功!');
            // 也可以選擇下單成功後自動導回股票清單
            navigate('/');
        } catch (err) {
            setError('下單失敗: ' + err.message);
        }
    };

    return (
        <div>
            <h1>{action === 'buy' ? '買進' : '賣出'} {stockId}</h1>
            {error && <p style={{ color : 'red'}}>{error}</p>}
            {success && <p style={{ color : 'green'}}>{success}</p>}

            <form onSubmit={handleSubmit}>
                <label>股數</label>
                <input 
                    type="number"
                    value={shares}
                    onChange={(e) => setShares(e.target.value)} 
                />
                <button type="submit">送出</button>
            </form>
        </div>
    )
}

export default OrderPage;