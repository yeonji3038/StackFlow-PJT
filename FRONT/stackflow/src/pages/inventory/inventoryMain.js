import "./InventoryMain.css"
import { Outlet, useLocation } from "react-router-dom"

const InventoryMain = () => {
  const location = useLocation().pathname // 현재 URL 정보  

  // 현재 URL에 따라 title 값 바꾸기
  const getTitle = () => { 
    switch (location) {
      case '/inventory/history' : return '입출고 내역 조회';
      case '/inventory/shipping' : return '출고'
      default : return '입출고 내역 조회'
    }
  }

  return (
    <div className="inventoryMainPage">
      <h1 className="title">{getTitle()}</h1>
      <hr />
      <article className="InventoryContent">
        <Outlet/>
      </article>
    </div>
  )
}

export default InventoryMain