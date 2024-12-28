import "./RtMain.css";
import { Outlet } from "react-router-dom";

const RtMain = () => {
  return(
    <div className="rtMainPage">
      <h1 className="title">매장 RT 관리 </h1>
      <hr />
      <div className="mainContent">
        <Outlet/> {/* 중첩 라우트 설정 */}
      </div> 
    </div>
  )
}
export default RtMain