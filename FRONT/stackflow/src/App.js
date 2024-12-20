import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import MainLayout from './components/layout/MainLayout';
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import PrivateRoute from './components/auth/PrivateRoute';
import MainPage from './pages/MainPage';
import Rt from './pages/rt/Rt';
import ChatHistory from './pages/chatbot/ChatHistory';

// 임시로 페이지 컴포넌트들 생성
const InventoryPages = () => <div>Inventory Page</div>;
const NoticePages = () => <div>Notice Page</div>;
const ProductPages = () => <div>Product Page</div>;
const RtPages = () => <div>RT Page</div>;

function App() {
  return (
    <BrowserRouter>
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
                <Route path="notice/*" element={<NoticePages />} />
                <Route path="product/*" element={<ProductPages />} />
                <Route path="rt/*" element={<Rt />} />
                <Route path="chat" element={<ChatHistory/>}/>
              </Routes>
            </MainLayout>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
