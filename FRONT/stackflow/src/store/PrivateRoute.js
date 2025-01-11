import { Navigate, Outlet } from 'react-router-dom';
import useAuth from './tokenManage'

const PrivateRoute = () => {
  const { getToken } = useAuth();
  const token = getToken();

  // return token ? <Outlet /> : <Navigate to="/login" />;
  return <Outlet />;
};

export default PrivateRoute;