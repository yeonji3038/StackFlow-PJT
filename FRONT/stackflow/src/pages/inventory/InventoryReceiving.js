import React, { useState, useEffect } from 'react';
import styles from './InventoryReceiving.module.css';

const InventoryReceiving = () => {
  const [selectAll, setSelectAll] = useState(false);
  const [checkedItems, setCheckedItems] = useState({
    item1: false,
    item2: false,
    item3: false
  });

  useEffect(() => {
    const totalItems = Object.keys(checkedItems).length;
    const checkedCount = Object.values(checkedItems).filter(Boolean).length;
    
    setSelectAll(totalItems === checkedCount && totalItems > 0);
  }, [checkedItems]);

  const handleSelectAll = (e) => {
    const isChecked = e.target.checked;
    setSelectAll(isChecked);
    
    const updatedCheckedItems = {};
    Object.keys(checkedItems).forEach(key => {
      updatedCheckedItems[key] = isChecked;
    });
    setCheckedItems(updatedCheckedItems);
  };

  const handleCheck = (id) => {
    setCheckedItems(prev => ({
      ...prev,
      [id]: !prev[id]
    }));
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.header}>입고</h1>
      <hr />
      <div className={styles.searchSection}>
        <form className={styles.searchForm}>
          <div className={styles.inputRow}>
            <select>
              <option value="">카테고리 그룹</option>
              {/* 카테고리 그룹 옵션들 */}
            </select>
            <select>
              <option value="">카테고리 코드</option>
              {/* 카테고리 코드 옵션들 */}
            </select>
          </div>
          <div className={styles.inputRow}>
            <select>
              <option value="">색상 코드</option>
              {/* 색상 코드 옵션들 */}
            </select>
            <select>
              <option value="">사이즈</option>
              {/* 사이즈 옵션들 */}
            </select>
          </div>
          <div className={styles.lastRow}>
            <input type="text" placeholder="상품 코드를 입력하세요." />
            <button type="submit" className={styles.searchButton}>검색</button>
          </div>
        </form>
      </div>
      
      <table className={styles.table}>
        <thead className={styles.tableHeader}>
          <tr>
            <th>
              <input 
                type="checkbox" 
                checked={selectAll}
                onChange={handleSelectAll} 
                className={styles.checkbox}
              />
            </th>
            <th>상품코드</th>
            <th>상품명</th>
            <th>색상</th>
            <th>사이즈</th>
            <th>총수량</th>
            <th>입고 수량</th>
          </tr>
        </thead>
        <tbody className={styles.tableBody}>
          <tr>
            <td>
              <input 
                type="checkbox" 
                checked={checkedItems.item1}
                onChange={() => handleCheck('item1')} 
                className={styles.checkbox}
              />
            </td>
            <td>YIBOTTEMPTN1184c374-e987-43ce-9b1c-eb414283670c</td>
            <td>GG버킷</td>
            <td>BK</td>
            <td>FREE</td>
            <td>403</td>
            <td>
              <input 
                type="number" 
                className={styles.inputQuantity}
                placeholder="수량 입력"
                min="0"
              />
            </td>
          </tr>
          <tr>
            <td>
              <input 
                type="checkbox" 
                checked={checkedItems.item2}
                onChange={() => handleCheck('item2')} 
                className={styles.checkbox}
              />
            </td>
            <td>YIBOTTEMPTN1184c374-e987-43ce-9b1c-eb414283670c</td>
            <td>GG버킷</td>
            <td>BK</td>
            <td>FREE</td>
            <td>403</td>
            <td>
              <input 
                type="number" 
                className={styles.inputQuantity}
                placeholder="수량 입력"
                min="0"
              />
            </td>
          </tr>
          <tr>
            <td>
              <input 
                type="checkbox" 
                checked={checkedItems.item3}
                onChange={() => handleCheck('item3')} 
                className={styles.checkbox}
              />
            </td>
            <td>YIBOTTEMPTN1184c374-e987-43ce-9b1c-eb414283670c</td>
            <td>GG버킷</td>
            <td>BK</td>
            <td>FREE</td>
            <td>403</td>
            <td>
              <input 
                type="number" 
                className={styles.inputQuantity}
                placeholder="수량 입력"
                min="0"
              />
            </td>
          </tr>
        </tbody>
      </table>
      
      <div className={styles.buttonContainer}>
        <button className={styles.submitButton}>신청</button>
        <button className={styles.resetButton}>취소</button>
      </div>
    </div>
  );
};

export default InventoryReceiving;