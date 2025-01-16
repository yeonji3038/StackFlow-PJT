import "./RtMain.css";
import { Outlet, useLocation } from "react-router-dom";


const RtMain = () => {
  const location = useLocation().pathname  // 현재 URL 정보  
  
  // 현재 URL에 따라 title 값 바꾸기
  const getTitle = () => { 
    switch (location) {
      case '/rt/search' : return '지시 조회';
      case '/rt/register' : return '지시 등록'
      default : return '지시 조회'
    }
  }
  return(
    <div className="rtMainPage">
      <h1 className="title">{getTitle()} </h1>
      <hr />
      <div className="mainContent">
        <Outlet/> {/* 중첩 라우트 설정 */}
      </div> 
    </div>
  )
}
export default RtMain