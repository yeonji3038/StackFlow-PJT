import styles from './MainPage.module.css';

const MainPage = () => {
  return (
    <div className={styles.mainContainer}>
      <div className={styles.dashboardGrid}>
        {/* 대시보드 형태로 구현, 일단 임시로 임의 데이터 채워둠 */}
        
        {/* R/T 지시내역 */}
        <div className={styles.dashboardItem}>
          <div className={styles.headerContainer}>
            <h2>R/T 지시내역</h2>
            <a href="/rt/search" className={styles.moreLink}>more</a>
          </div>
          <div className={styles.content}>
            <table className={styles.dataTable}>
              <thead>
                <tr>
                  <th>지시일자</th>
                  <th>반입매장</th>
                  <th>미처리수량</th>
                </tr>
              </thead>
              <tbody>
                <tr><td>2024-02-20</td><td>강남점</td><td>5</td></tr>
                <tr><td>2024-02-20</td><td>홍대점</td><td>3</td></tr>
                <tr><td>2024-02-19</td><td>신촌점</td><td>2</td></tr>
                <tr><td>2024-02-19</td><td>건대점</td><td>4</td></tr>
                <tr><td>2024-02-18</td><td>종로점</td><td>1</td></tr>
              </tbody>
            </table>
          </div>
        </div>

        {/* R/T 반출내역 */}
        <div className={styles.dashboardItem}>
          <div className={styles.headerContainer}>
            <h2>R/T 반출내역</h2>
            <a href="/rt/search" className={styles.moreLink}>more</a>
          </div>
          <div className={styles.content}>
            <table className={styles.dataTable}>
              <thead>
                <tr>
                  <th>반출일자</th>
                  <th>반입매장</th>
                  <th>미처리수량</th>
                </tr>
              </thead>
              <tbody>
                <tr><td>2024-02-20</td><td>강남점</td><td>3</td></tr>
                <tr><td>2024-02-20</td><td>홍대점</td><td>2</td></tr>
                <tr><td>2024-02-19</td><td>신촌점</td><td>4</td></tr>
                <tr><td>2024-02-19</td><td>건대점</td><td>1</td></tr>
                <tr><td>2024-02-18</td><td>종로점</td><td>5</td></tr>
              </tbody>
            </table>
          </div>
        </div>

        {/* R/T 반입내역 */}
        <div className={styles.dashboardItem}>
          <div className={styles.headerContainer}>
            <h2>R/T 반입내역</h2>
            <a href="/rt/search" className={styles.moreLink}>more</a>
          </div>
          <div className={styles.content}>
            <table className={styles.dataTable}>
              <thead>
                <tr>
                  <th>반입일자</th>
                  <th>반입매장</th>
                  <th>미처리수량</th>
                </tr>
              </thead>
              <tbody>
                <tr><td>2024-02-20</td><td>강남점</td><td>2</td></tr>
                <tr><td>2024-02-20</td><td>홍대점</td><td>4</td></tr>
                <tr><td>2024-02-19</td><td>신촌점</td><td>1</td></tr>
                <tr><td>2024-02-19</td><td>건대점</td><td>3</td></tr>
                <tr><td>2024-02-18</td><td>종로점</td><td>2</td></tr>
              </tbody>
            </table>
          </div>
        </div>

        {/* 공지사항 */}
        <div className={styles.dashboardItem}>
          <div className={styles.headerContainer}>
            <h2>공지사항</h2>
            <a href="/notice" className={styles.moreLink}>more</a>
          </div>
          <div className={styles.content}>
            <table className={styles.dataTable}>
              <thead>
                <tr>
                  <th>날짜</th>
                  <th>제목</th>
                  <th>확인</th>
                </tr>
              </thead>
              <tbody>
                <tr><td>2024-02-20</td><td>2월 업무 공지</td><td className={styles['notice-check']}>●</td></tr>
                <tr><td>2024-02-19</td><td>시스템 점검 안내</td><td className={styles['notice-check']}>●</td></tr>
                <tr><td>2024-02-18</td><td>신규 매장 안내</td><td className={styles['notice-check']}>●</td></tr>
                <tr><td>2024-02-17</td><td>배송 지연 안내</td><td className={styles['notice-check']}>●</td></tr>
                <tr><td>2024-02-16</td><td>휴무일 안내</td><td className={styles['notice-check']}>●</td></tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainPage;