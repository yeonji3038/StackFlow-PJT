import React from 'react';
import { Routes, Route, Navigate, BrowserRouter } from 'react-router-dom';

import MainLayout from './components/layout/MainLayout';
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import MainPage from './pages/MainPage';
import RtMain from './pages/rt/RtMain';
import RtSearch from './pages/rt/RtSearch';
import RtRegister from './pages/rt/RtRegister';
import ChatHistory from './pages/chatbot/ChatHistory';
import Notice from './pages/notice/Notice';
import NoticeCreate from './pages/notice/NoticeCreate';
import NoticeEdit from './pages/notice/NoticeEdit';
import ProductRegister from './pages/product/ProductRegister';
import ProductManagement from './pages/product/ProductManagement';
import ProductStockStatus from './pages/product/ProductStockStatus';
import ProductCategoryRegister from './pages/product/ProductCategoryRegister';
import InventoryReceiving from './pages/inventory/InventoryReceiving';
import InventoryShipping from './pages/inventory/InventoryShipping';
import InventoryHistory from './pages/inventory/InventoryHistory';
import MyPage from './pages/MyPage';
import ManagementStore from './pages/management/ManagementStore';
import ManagementCode from './pages/management/ManagementCode';
import PrivateRoute from './store/PrivateRoute';


// 임시로 페이지 컴포넌트들 생성
const InventoryPages = () => <div>Inventory Page</div>;
const ProductPages = () => <div>Product Page</div>;

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* 처음 접속시 로그인 페이지로 리다이렉트 */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        
        {/* 인증이 필요없는 라우트 */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        

        <Route element={<PrivateRoute />}>
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
                <Route path="/notice/create" element={<NoticeCreate />} />
                <Route path="/notice/edit/:id" element={<NoticeEdit />} />
                <Route path="product/*" element={<ProductPages />} />
                <Route path="/product/register" element={<ProductRegister />} />
                <Route path="/product/management" element={<ProductManagement />} />
                <Route path="/product/stockstatus" element={<ProductStockStatus />} />
                <Route path="/product/categoryregister" element={<ProductCategoryRegister />} />
                <Route path="rt/*" element={<RtMain/>}>
                  <Route index element={<Navigate to="./search" replace />} />
                  <Route path="search" element={<RtSearch/>}/>
                  <Route path="register" element={<RtRegister/>}/>
                </Route>
                <Route path="chat" element={<ChatHistory/>}/>
                <Route path="management/*">
                  <Route path="store" element={<ManagementStore />} />
                  <Route path="code" element={<ManagementCode />} />
                </Route>
                <Route path="mypage" element={<MyPage/>}/>
              </Routes>
            </MainLayout>
          }
        />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
