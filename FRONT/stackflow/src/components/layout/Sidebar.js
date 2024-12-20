import { Link } from 'react-router-dom';
import styles from './Sidebar.module.css';

const Sidebar = () => {
  return (
    <div className={styles.sidebarContainer}>
      <div className={styles.logo}>
        <img src="/images/stackflow_logo.png" alt="StackFlow" />
      </div>
      <hr></hr>
      <ul className={styles.menuList}>
        <li className={styles.menuItem}>
          <Link to="/product">상품관리</Link>
        </li>
        <li className={styles.menuItem}>
          <Link to="/inventory">입출고 등록</Link>
        </li>
        <li className={styles.menuItem}>
          <Link to="/notice">게시판</Link>
        </li>
        <li className={styles.menuItem}>
          <Link to="/rt">RT</Link>
        </li>
        <li className={styles.menuItem}>
          <Link to="/chat">채팅</Link>
        </li>
      </ul>
      <hr></hr>
      <div className={styles.bottomSection}>
        <div className={styles.menuItem}>
          <Link to="/management">MANAGEMENT</Link>
        </div>
        <div className={styles.menuItem}>
          <Link to="/mypage">MY PAGE</Link>
        </div>
        <button className={styles.logoutButton}>LOGOUT</button>
      </div>
    </div>
  );
};

export default Sidebar;
