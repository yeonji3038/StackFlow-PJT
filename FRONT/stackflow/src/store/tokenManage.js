import { useCookies } from "react-cookie";

const useToken = () => {
  const [cookies, setCookie, removeCookie] = useCookies(['authToken']);
  
  // 쿠키에 토큰 저장
  const saveToken = (token) => {
    setCookie('JSESSIONID', token, {
      path: '/',       // 모든 경로에서 쿠키 접근 가능
      maxAge: 3600,    // 1시간 동안 유효
      secure: true,    // HTTPS에서만 전송
      sameSite: 'Strict', // CSRF 보호
    });
  };

  // 쿠키에서 토큰 제거
  const deleteToken = () => {
    removeCookie('JSESSIONID', { path: '/' });
  };

  // 쿠키에서 토큰 가져오기
  const getToken = () => cookies.JSESSIONID;

  return { saveToken, deleteToken, getToken };
};

export default useToken;