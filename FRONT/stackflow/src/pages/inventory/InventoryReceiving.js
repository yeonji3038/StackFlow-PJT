import React, { useState, useEffect } from 'react';
import styles from './InventoryReceiving.module.css';

import axios from 'axios';
import qs from "qs";

import { useConfig } from "../../store";
import useToken from "../../store/tokenManage";

const InventoryReceiving = () => {

  // 상태 변수 정의
  const [products, setProducts] = useState([]);  // 조회된 상품 데이터 저장
  const [selectedProduct, setSelectedProduct] = useState([]); // 선택된 상품 목록
  const [selectOptions, setSelectOptions] = useState([]); // 옵션 데이터를 저장
  const [userInput, setUserInput] = useState({ // 사용자 입력 필터 데이터
    categoryGroup: "",  
    categoryCode: "",
    colorCode: "",
    size: "",
    input: "",
  });
  
  // 인증 및 설정 관련 변수
  const { getToken } = useToken();  
  const csrfToken = getToken(); // CSRF 토큰
  const { BASE_URL } = useConfig(); // API 기본 URL

  // 서버에서 데이터 가져오는 함수
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

  // select box에 표시될 옵션 데이터 매핑
  const mapSelectOptions = (data) => {
    if (selectOptions[data]) {
      const entry = Object.entries(selectOptions[data]);
      return entry
        .filter((_, index) => index !== 0) // 첫 번째 항목 제외
        .map(([key, value]) => (
          <option itemID={key} key={key} value={value}>
            {value}
          </option>
      ));
    }
    return null; // 옵션이 없을 경우 null 반환
  };

    // 컴포넌트 로드 시 데이터 가져오기
    useEffect(() => {
      fetchData(`${BASE_URL}/api/rt/all`, setSelectOptions);
      fetchData(`${BASE_URL}/notice/api`, setProducts)
    }, []);


    



    const test = async () => {
      try {
        const response = await axios({
          method: "POST",
          url: `${BASE_URL}/api/retrieval/submit/user`,
          data: {
            "instructions": [
                {
                    "productId": 2,
                    "retrivalQuantity": 3
                }
            ]
        },
        
          withCredentials: true,
          headers: {
            "X-CSRF-TOKEN": csrfToken,
            "Content-Type": "application/json",
          },
        });
        console.log(response.data);
      } catch (error) {
        console.error("Error occurred:", error);
      }
    };




    const test2 = async () => {
      try {
        const response = await axios({
          method: "DELETE",
          url: `${BASE_URL}/notice/api/delete/1`,
          data: {
            title : "tttt",
            content : "fffff"
          },
          withCredentials: true,
          headers: {
            "X-CSRF-TOKEN": csrfToken,
            "Content-Type": "application/x-www-form-urlencoded",
          },
        });
        console.log(response.data);
      } catch (error) {
        console.error("Error occurred:", error);
      }
    };
  
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
          <select
              name="categoryGroup"
              value={userInput.categoryGroup}
              onChange={(e) => 
                setUserInput({ ...userInput, categoryGroup: e.target.value })
              }>
              <option value={""}>카테고리 그룹</option>
              {mapSelectOptions("category_group_code")}
            </select>
            <select
              name="categoryCode"
              value={userInput.categoryCode}
              onChange={(e) =>
                setUserInput({ ...userInput, categoryCode: e.target.value })
              }>
              <option value={""}>카테고리 코드</option>
              {mapSelectOptions("category_code")}
            </select>
          </div>
          <div className={styles.inputRow}>
            <select
                name="colorCode"
                value={userInput.colorCode}
                onChange={(e) =>
                  setUserInput({ ...userInput, colorCode: e.target.value })
                }>
                <option value={""}>색상 코드</option>
                {mapSelectOptions("color_code")}
        
            </select>
            <select
              name="size"
              value={userInput.size}
              onChange={(e) => setUserInput({ ...userInput, size: e.target.value })}>
              <option value={""}>사이즈</option>
              {mapSelectOptions("size")}
            </select>
          </div>
          <div className={styles.lastRow}>
            <input
                type="text"
                className="userSearchInput"
                placeholder="상품 코드를 입력하세요"
                name="input"
                maxLength= "20"
                onChange={(e) =>
                  setUserInput({ ...userInput, input: e.target.value })
                }/>
            
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
        <button className={styles.submitButton} onClick={test2}>신청</button>
        <button className={styles.resetButton} onClick={test}>취소</button>
      </div>
    </div>
  );
};

export default InventoryReceiving;