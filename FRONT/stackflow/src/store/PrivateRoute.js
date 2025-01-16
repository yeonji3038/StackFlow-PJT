import { Navigate, Outlet } from 'react-router-dom';
import useAuth from './tokenManage'

const PrivateRoute = () => {
  const { getToken } = useAuth();
  const token = getToken();

<<<<<<< HEAD
  return token ? <Outlet /> : <Navigate to="/login" />;
  return <Outlet/>
=======
  // return token ? <Outlet /> : <Navigate to="/login" />;
  return <Outlet />;
>>>>>>> refs/remotes/origin/main
};

export default PrivateRoute;