import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import MainLayout from './components/layout/MainLayout';
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import MainPage from './pages/MainPage';
import Rt from './pages/rt/Rt';
import ChatHistory from './pages/chatbot/ChatHistory';
import Notice from './pages/notice/Notice';
import ProductRegister from './pages/product/ProductRegister';
import ProductManagement from './pages/product/ProductManagement';
import ProductStockStatus from './pages/product/ProductStockStatus';
import ProductCategoryRegister from './pages/product/ProductCategoryRegister';
import InventoryReceiving from './pages/inventory/InventoryReceiving';
import InventoryShipping from './pages/inventory/InventoryShipping';
import InventoryHistory from './pages/inventory/InventoryHistory';
import ManagementPage from './pages/ManagementPage';
import MyPage from './pages/MyPage';

// 임시로 페이지 컴포넌트들 생성
const InventoryPages = () => <div>Inventory Page</div>;
const ProductPages = () => <div>Product Page</div>;

function App() {
  return (
    <Router>
      <Routes>
        {/* 처음 접속시 로그인 페이지로 리다이렉트 */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        
        {/* 인증이 필요없는 라우트 */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        
        {/* MainLayout이 적용되는 라우트들 */}
        <Route
          path="/*"
          element={
            <MainLayout>
              <Routes>
                <Route path="main" element={<MainPage />} />
                <Route path="inventory/*" element={<InventoryPages />} />
                <Route path="/inventory/receiving" element={<InventoryReceiving />} />
                <Route path="/inventory/shipping" element={<InventoryShipping />} />
                <Route path="/inventory/history" element={<InventoryHistory />} />
                <Route path="notice/*" element={<Notice />} />
                <Route path="product/*" element={<ProductPages />} />
                <Route path="/product/register" element={<ProductRegister />} />
                <Route path="/product/management" element={<ProductManagement />} />
                <Route path="/product/stockstatus" element={<ProductStockStatus />} />
                <Route path="/product/categoryregister" element={<ProductCategoryRegister />} />
                <Route path="rt/*" element={<Rt />} />
                <Route path="chat" element={<ChatHistory/>}/>
                <Route path="management" element={<ManagementPage/>}/>
                <Route path="mypage" element={<MyPage/>}/>
              </Routes>
            </MainLayout>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
