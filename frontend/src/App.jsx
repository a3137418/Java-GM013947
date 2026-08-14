import { Route ,Routes } from "react-router-dom";
import StockListPage from "./pages/StockListPage";
import LoginPage from "./pages/LoginPage";
import OrderPage from "./pages/OrderPage";
import ProtectedRoute from "./components/ProtectedRoute";

function App(){
    return(
      <Routes>
          <Route path="/" element={<StockListPage/>} />
          <Route path="/login" element={<LoginPage />}/> 
          <Route path="/order/:stockId/:action" element={
            <ProtectedRoute>
                <OrderPage/>
            </ProtectedRoute>
          }/>
      </Routes>
    )
}

export default App