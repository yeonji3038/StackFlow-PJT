import React from 'react';
import styles from './ProductStockStatus.module.css';

const ProductStockStatus = () => {
  return (
    <div className={styles.container}>
        <h1 className={styles.header}>매장별 재고 조회</h1>
        <hr />
      {/* 검색 섹션 */}
      <div className={styles.searchSection}>
        <form className={styles.searchForm}>
          <div className={styles.formRow}>
            <label>매장</label>
            <select defaultValue="">
              <option value="" disabled>전주점</option>
              <option value="jeonju">전주점</option>
              <option value="seoul">서울점</option>
            </select>
          </div>
          
          <div className={styles.formRow}>
            <label>상세검색</label>
            <div className={styles.searchDetails}>
              <select defaultValue="">
                <option value="" disabled>색상 코드</option>
                <option value="BLACK">BLACK</option>
                <option value="WHITE">WHITE</option>
              </select>
              <select defaultValue="">
                <option value="" disabled>카테고리 그룹</option>
                <option value="top">상의</option>
                <option value="bottom">하의</option>
              </select>
              <select defaultValue="">
                <option value="" disabled>카테고리 코드</option>
                <option value="ts">티셔츠</option>
                <option value="pt">팬츠</option>
              </select>
            </div>
          </div>

          <div className={styles.formRow}>
            <label>상품코드</label>
            <div className={styles.searchInput}>
              <input type="text" placeholder="상품 코드를 입력하세요." />
              <button type="submit" className={styles.searchButton}>검색</button>
            </div>
          </div>
        </form>
      </div>

      {/* 테이블 섹션 */}
        <table className={styles.table}>
          <thead className={styles.tableHeader}>
            <tr>
              <th>상품코드</th>
              <th>품목명</th>
              <th>색상</th>
              <th>사이즈</th>
              <th>판매가</th>
              <th>수량</th>
            </tr>
          </thead>
          <tbody className={styles.tableBody}>
            <tr>
              <td>StackFlowTOPTSFREEcfb50a28-d0b7-458e-b024-f6469fd0179f</td>
              <td>404 에러티셔츠</td>
              <td>BLACK</td>
              <td>FREE</td>
              <td>30,000</td>
              <td>50</td>
            </tr>
            <tr>
              <td>NIKYBOTTOMMDNXL2509e99c-4918-425b-827b-de126645aa27</td>
              <td>500 베트먼티셔츠</td>
              <td>WHITE</td>
              <td>L</td>
              <td>400000</td>
              <td>3</td>
            </tr>
          </tbody>
        </table>
      </div>
  );
};

export default ProductStockStatus;
