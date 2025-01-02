import { useState } from 'react';
import { Link } from 'react-router-dom';
import styles from './Sidebar.module.css';
import { useDispatch } from 'react-redux';
import { logout } from '../../store/authSlice';
import { useNavigate } from 'react-router-dom';
import useAuth from '../../store/tokenManage'

const Sidebar = () => {
  const [openMenu, setOpenMenu] = useState(null);
  const { deleteToken } = useAuth();


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
        { title: '입고', path: '/inventory/receiving' },
        { title: '출고', path: '/inventory/shipping' },
        { title: '입출고 내역 조회', path: '/inventory/history' },
      ]
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
      title: '공지사항',
      path: '/notice',
      subMenus: []
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
  
  const dispatch = useDispatch();
  const navigate = useNavigate();
  
  const onClickLogout = (e) => {
    deleteToken() // 쿠키에서 토큰 제거 
    // Redux Store에서 사용자 정보 및 로그인 상태 초기화
    dispatch(logout());
    navigate('/login'); // 로그인 페이지로 이동
  };

  
  return (
    <div className={styles.sidebarContainer}>
      <div className={styles.logo}>
        <Link to="/main">
          <img src="/images/stackflow_logo.png" alt="StackFlow" />
        </Link>
      </div>
      <div className={styles.divider} />
      
      <ul className={styles.menuList}>
        {menuItems.map((item, index) => (
          <li key={index}>
            {item.subMenus.length > 0 ? (
              <div className={styles.menuItem} onClick={() => toggleMenu(index)}>
                {item.title}
              </div>
            ) : (
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
          <div onClick={() => toggleMenu('management')}>MANAGEMENT</div>
          {openMenu === 'management' && (
            <ul className={styles.subMenuList}>
              <li className={styles.subMenuItem}>
                <Link to="/management/store">매장 관리</Link>
              </li>
              <li className={styles.subMenuItem}>
                <Link to="/management/code">매장 코드 생성</Link>
              </li>
            </ul>
          )}
        </div>
        <div className={styles.menuItem}>
          <Link to="/mypage">MY PAGE</Link>
        </div>
        <button 
          className={styles.logoutButton} 
          onClick={onClickLogout}>LOGOUT</button>
      </div>
    </div>
  );
};

export default Sidebar;
