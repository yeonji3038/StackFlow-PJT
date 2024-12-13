import './Sidebar.module.css';

const Sidebar = () => {
  return (
    <div className="sidebar-container">
      <div className="logo">
        <img src="/stackflow-logo.png" alt="StackFlow" />
      </div>
      
      <ul className="menu-list">
        <li className="menu-item">상품관리</li>
        <li className="menu-item">입출고 등록</li>
        <li className="menu-item">게시판</li>
        <li className="menu-item">RT</li>
      </ul>

      <div className="bottom-section">
        <div className="menu-item">MANAGEMENT</div>
        <div className="menu-item">MY PAGE</div>
        <button className="logout-button">LOGOUT</button>
      </div>
    </div>
  );
};

export default Sidebar;
