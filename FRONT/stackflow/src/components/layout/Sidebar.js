import { useState } from 'react';
import { Link } from 'react-router-dom';
import styles from './Sidebar.module.css';

const Sidebar = () => {
  const [openMenu, setOpenMenu] = useState(null);

  const menuItems = [
    {
      title: '상품관리',
      path: '/product',
      subMenus: [
        { title: '상품 등록', path: '/product/register' },
        { title: '등록 상품 관리', path: '/product/management' },
        { title: '매장별 재고 조회', path: '/product/stockstatus' },
        { title: '카테고리 등록', path: '/product/categoryregister' },
      ]
    },
    {
      title: '입출고 등록',
      path: '/inventory',
      subMenus: [
        { title: '입출고', path: '/inventory/register' },
        { title: '입출고 내역 조회', path: '/inventory/history' },
      ]
    },
    {
      title: '공지사항',
      path: '/notice',
      subMenus: []
    },
    {
      title: 'RT',
      path: '/rt',
      subMenus: [
        { title: '지시 등록', path: '/rt/register' },
        { title: '지시 조회', path: '/rt/search' },
      ]
    },
    {
      title: '채팅',
      path: '/chat',
      subMenus: []
    }
  ];

  const toggleMenu = (index) => {
    setOpenMenu(openMenu === index ? null : index);
  };
  return (
    <div className={styles.sidebarContainer}>
      <div className={styles.logo}>
        {/* 로고 클릭시 메인페이지로 이동 */}
        <Link to="/main">
          <img src="/images/stackflow_logo.png" alt="StackFlow" />
        </Link>
      </div>
      <div className={styles.divider} />
      
      <ul className={styles.menuList}>
        {menuItems.map((item, index) => (
          <li key={index}>
            {item.subMenus.length > 0 ? (
              // 서브메뉴가 있는 경우
              <div className={styles.menuItem} onClick={() => toggleMenu(index)}>
                {item.title}
              </div>
            ) : (
              // 서브메뉴가 없는 경우 - 직접 링크로 연결
              <div className={styles.menuItem}>
                <Link to={item.path}>{item.title}</Link>
              </div>
            )}
            {item.subMenus.length > 0 && openMenu === index && (
              <ul className={styles.subMenuList}>
                {item.subMenus.map((subItem, subIndex) => (
                  <li key={subIndex} className={styles.subMenuItem}>
                    <Link to={subItem.path}>{subItem.title}</Link>
                  </li>
                ))}
              </ul>
            )}
          </li>
        ))}
      </ul>
      <div className={styles.divider} />
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
