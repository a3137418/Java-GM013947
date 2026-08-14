import { useEffect, useState } from "react"
import { apiFetch } from "../api/client"
import { useNavigate } from "react-router-dom";

function StockListPage(){
    //存股ˋ票清單，初始值是空陣列
    const [stocks , setStocks] = useState([]);

    const navigate = useNavigate();

    // useEffect 裡的函式會在「元件第一次出現在畫面上」時執行
    // 第二個參數傳空陣列 [] 代表「只執行一次，之後不會重複執行」
    useEffect(() => {
        // 因為 useEffect 本身不能是 async 函式，
        // 所以裡面另外定義一個 async 函式再呼叫它
        async function loadStocks() {
            const result = await apiFetch('/stock')

            // 後端 ApiResponse 的資料在 result.data 裡
            setStocks(result.data);
        }
        loadStocks();
    } , []);


    return (
        <div>
            <h1>股票清單</h1>
            <table>
                <thead>
                    <tr>
                        <th>股票代碼</th>
                        <th>股票名稱</th>
                        <th>現價</th>
                    </tr>
                </thead>
                <tbody>
                    {/* stocks.map 把陣列裡每一筆資料轉成一個 <tr>*/}
                    {stocks.map((stock) => (
                        // key 是 React 用來追蹤每個元素身份的必要屬性，用股票代碼當 key（不會重複）
                        <tr key={stock.stockId}>
                            <td>{stock.stockId}</td>
                            <td>{stock.stockName}</td>
                            <td>{stock.price}</td>
                            <td >
                                <button onClick={() => navigate(`/order/${stock.stockId}/buy`)}>買進</button>
                                <button onClick={() => navigate(`/order/${stock.stockId}/sell`)}>賣出</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default StockListPage