import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }) => {
  const isAuthenticated = false; // 여기에 실제 인증 로직을 추가

  return isAuthenticated ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
