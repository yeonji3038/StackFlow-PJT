import React, { useState, useEffect } from 'react';
import styles from './InventoryShipping.module.css';
import axios from 'axios';
import { useSelector } from 'react-redux';

const InventoryShipping = () => {
  const [selectAll, setSelectAll] = useState(false);
  const [checkedItems, setCheckedItems] = useState({
    item1: false,
    item2: false,
    item3: false
  });
  const [products, setProducts] = useState([]);
  const state = useSelector( (state) => (state))
  const csrfToken = state.csrfToken // CSRF 토큰
  const BASE_URL = state.BASE_URL


  // async func
  const fetchData = async (url, setData) => {
    try {
      const response = await axios.get(url, {
        withCredentials: true,
        headers: {
          "X-CSRF-TOKEN": csrfToken,
        },
      });
      setData(response.data);
    } catch (err) {
      console.error("Error:", err);
    }
  };

// 
  // async func
  const postTest = async () => {
    const response = await axios({
      method: "POST",
      url: `${BASE_URL}/api/retrieval/submit/admin`,
      data: { instructions: [
        {
          "productId": 2,
          "storeId": 2,
          "retrivalQuantity": 10
        }
    ]},
      withCredentials: true,
      maxRedirects: 0,
      headers: {
        "X-CSRF-TOKEN": csrfToken,
        "Content-Type": "application/json",
      },
    });
    console.log(response.data)
  }

  // 

  useEffect(() => {
    fetchData(`${BASE_URL}/api/retrieval/list`,setProducts)

    // 모든 항목이 체크되었는지 확인
    const totalItems = Object.keys(checkedItems).length;
    const checkedCount = Object.values(checkedItems).filter(Boolean).length;
    
    // 모든 항목이 체크되었으면 전체 선택도 체크
    setSelectAll(totalItems === checkedCount && totalItems > 0);
  }, [checkedItems]);

  const handleSelectAll = (e) => {
    const isChecked = e.target.checked;
    setSelectAll(isChecked);
    
    // 모든 항목의 체크 상태를 변경
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
  // http://localhost:8080/

  console.log(products)
  return (
    <div className={styles.container}>
      <h1 className={styles.header}>출고</h1>
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
            <th>출고 수량</th>
            <th>수령 매장</th>
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
            <td>YIBOTTEMPTM1184c374-e987-43ce-9b1c-eb414283670c</td>
            <td>404메리디서스</td>
            <td>BK</td>
            <td>FREE</td>
            <td>206</td>
            <td>
              <input 
                type="number"
                className={styles.inputQuantity}
                placeholder="출고 수량"
                min="0"
              />
            </td>
            <td>
              <select className={styles.storeSelect}>
                <option value="">매장 선택</option>
              </select>
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
            <td>NIKYBOTTOMDNXL2509e99c-4918-425b-827b-de126645aa2</td>
            <td>502수면바지</td>
            <td>WH</td>
            <td>XL</td>
            <td>300</td>
            <td>
              <input 
                type="number"
                className={styles.inputQuantity}
                placeholder="출고 수량"
                min="0"
              />
            </td>
            <td>
              <select className={styles.storeSelect}>
                <option value="">매장 선택</option>
              </select>
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
            <td>betmonTOPTSL3d51084c-de8a-43ef-942b-14e306d9c3df</td>
            <td>500베트먼셔츠</td>
            <td>WH</td>
            <td>L</td>
            <td>20</td>
            <td>
              <input 
                type="number"
                className={styles.inputQuantity}
                placeholder="출고 수량"
                min="0"
              />
            </td>
            <td>
              <select className={styles.storeSelect}>
                <option value="">매장 선택</option>
              </select>
            </td>
          </tr>
        </tbody>
      </table>

      <div className={styles.buttonContainer}>
        <button 
          className={styles.submitButton}
          onClick={postTest}>출고</button>
        <button className={styles.resetButton}>취소</button>
      </div>
    </div>
  );
};

export default InventoryShipping;
