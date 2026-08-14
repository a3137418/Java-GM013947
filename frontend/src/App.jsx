import { Route ,Routes } from "react-router-dom";
import StockListPage from "./pages/StockListPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import OrderPage from "./pages/OrderPage";
import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar";
import PositionPage from "./pages/PositionPage";
import KbarPage from "./pages/KbarPage";
import WatchlistPage from "./pages/WatchlistPage";

function App(){
    return(
        <>
            <Navbar/>
            <Routes>
                <Route path="/" element={<StockListPage/>} />
                <Route path="/login" element={<LoginPage />}/>
                <Route path="/register" element={<RegisterPage />}/>
                <Route path="/order/:stockId/:action" element={
                    <ProtectedRoute>
                        <OrderPage/>
                    </ProtectedRoute>
                }/>
                <Route path="/positions" element={
                    <ProtectedRoute>
                        <PositionPage/>
                    </ProtectedRoute>
                }/>
                <Route path="/watchlist" element={
                    <ProtectedRoute>
                        <WatchlistPage/>
                    </ProtectedRoute>
                }/>
                <Route path="/kbar/:stockId" element={<KbarPage />} />
            </Routes>
        </>
    )
}

export default App