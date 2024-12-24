import "./Rt.css";
import { Outlet } from "react-router-dom";

const RtMain = () => {
  
 
  
  return(
    
    <div className="rtPage">
      <h1>매장 RT 관리 </h1>
      <hr />
      <div className="mainContent">
        <Outlet/>
       
      </div> 
      
    </div>
  )
}

export default RtMain