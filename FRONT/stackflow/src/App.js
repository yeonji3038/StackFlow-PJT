import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './components/layout/MainLayout';
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import PrivateRoute from './components/auth/PrivateRoute';
import MainPage from './pages/MainPage';
import Rt from './pages/rt/Rt';
import ChatHistory from './pages/chatbot/Chat_history';

// 임시로 페이지 컴포넌트들 생성
const InventoryPages = () => <div>Inventory Page</div>;
const NoticePages = () => <div>Notice Page</div>;
const ProductPages = () => <div>Product Page</div>;
const RtPages = () => <div>RT Page</div>;

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* 인증이 필요없는 라우트 */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        
        {/* MainLayout이 적용되는 라우트들 */}
        <Route
          path="/*"
          element={
            <MainLayout>
              <Routes>
                {/* 각 경로에서 앞의 / 제거 */}
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
