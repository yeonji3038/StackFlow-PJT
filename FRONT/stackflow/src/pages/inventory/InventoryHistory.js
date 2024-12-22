import React, { useState } from 'react';
import styles from './InventoryHistory.module.css';

const InventoryHistory = () => {
  const [activeType, setActiveType] = useState('입고');

  return (
    <div className={styles.container}>
        <h1>입출고 내역 조회</h1>
        <hr />
      <div className={styles.header}>
        <div className={styles.centerButtons}>
          <label className={styles.radioLabel}>
            <input 
              type="radio"
              id="inbound"
              name="type"
              value="입고"
              defaultChecked
              className={styles.radioInput}
            />
            <span className={styles.radioButton}></span>
            입고
          </label>
          
          <label className={styles.radioLabel}>
            <input 
              type="radio"
              name="type"
              value="출고"
              checked={activeType === '출고'}
              onChange={(e) => setActiveType(e.target.value)}
              className={styles.radioInput}
            />
            <span className={styles.radioButton}></span>
            출고
          </label>
        </div>

        <button className={styles.searchButton}>조회</button>
      </div>

      <table className={styles.table}>
        <thead className={styles.tableHeader}>
          <tr>
            <th>매장명</th>
            <th>상품코드</th>
            <th>수량</th>
            <th>결재일</th>
            <th>입출고 상태</th>
          </tr>
        </thead>
        <tbody className={styles.tableBody}>
          <tr>
            <td>부산점</td>
            <td>StackFlowTOPTSFREEcfb50a28-d0b7-458e-b024-f6469fd0179f</td>
            <td>402</td>
            <td>2024-12-10 12:38:34</td>
            <td>요청 승인</td>
          </tr>
        </tbody>
      </table>
      <div className={styles.downloadSection}>
        <button className={styles.downloadButton}>
          <span className={styles.downloadIcon}>💾</span> Download
        </button>
      </div>
    </div>
  );
};

export default InventoryHistory;