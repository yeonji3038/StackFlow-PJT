import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './components/layout/MainLayout';
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import PrivateRoute from './components/auth/PrivateRoute';
import MainPage from './pages/MainPage';

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
        
        {/* 인증이 필요한 라우트 */}
        <Route element={<PrivateRoute />}>
          <Route
            path="/*"
            element={
              // MainLayout 안에 페이지들이 들어가있는 구조 => js 파일에서 수정하면 됨
              <MainLayout>
                <Routes>
                  <Route path="/main" element={<MainPage />} />
                  <Route path="/inventory/*" element={<InventoryPages />} />
                  <Route path="/notice/*" element={<NoticePages />} />
                  <Route path="/product/*" element={<ProductPages />} />
                  <Route path="/rt/*" element={<RtPages />} />
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
