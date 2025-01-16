import MainStyle from "./RtMain.module.css"
import customStyle from "./RtRegister.module.css";

import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from "sweetalert2";

import { useConfig } from "../../store";
import useToken from "../../store/tokenManage";

const RtRegister = () => {
  const nav = useNavigate();
  
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

  // 선택된 상품 데이터 업데이트 함수
  const onChangeInput = (productId, field, value) => {
    setSelectedProduct((preData) => {
      const isExisting = preData.some((data) => data.productId === productId);

      if (isExisting) {
        // 기존 데이터가 있으면 업데이트
        return preData.map((data) =>
          data.productId === productId ? { ...data, [field]: value } : data
        );
      } else {
        // 기존 데이터가 없으면 추가
        return [
          ...preData,
          { productId, storeId: field === "storeId" ? value : "", requestQuantity: field === "requestQuantity" ? value : "" },
        ];
      }
    });
  };

  // 사용자 입력 필터 데이터 기반 상품 데이터 조회
  const userInputData = async (commend) => {
    try {
      const response = await axios({
        method: "GET",
        url: `${BASE_URL}/api/rt/product`,
        withCredentials: true,
        headers: {
          "X-CSRF-TOKEN": csrfToken,
        },
      });
      console.log(response.data);

      if (commend === 'all') {
        // 전체 데이터 설정
        setProducts(response.data);
      } else {
        // 사용자 입력 필터를 기반으로 데이터 필터링
        const filteredData = response.data.filter((item) => {
          return (
            (userInput.categoryGroup ? userInput.categoryGroup === item.category_group : true) &&
            (userInput.categoryCode ? userInput.categoryCode === item.category_code : true) &&
            (userInput.colorCode ? userInput.colorCode === item.color_code : true) &&
            (userInput.size ? userInput.size === item.product_size : true) &&
            (userInput.input ? userInput.input === item.product_name : true)
          );
        });
        setProducts(filteredData);
      }
    } catch (err) {
      console.error("Error fetching or filtering data:", err);
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
  
  // 검색 페이지로 이동
  const goSearch = () => {
    return nav("../search");
  };
  
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

  // 체크박스 상태 토글
  const onChangeCheckBox = (product) => {
    setSelectedProduct((preData) => {
      if (preData.some((data) => data.productId === product.prod_id)) {
        // 이미 선택된 경우 제거
        return preData.filter((data) => data.productId !== product.prod_id);
      } else {
        // 선택되지 않은 경우 추가
        return [
          ...preData,
          { productId: product.prod_id, storeId: "", requestQuantity: "" },
        ];
      }
    });
  };
 

  // 전체 선택/해제 핸들러
  const [selectAll, setSelectAll] = useState(false); // 전체 선택 상태
  const handleSelectAll = (isChecked) => {
    setSelectAll(isChecked); // 전체 선택 상태 업데이트
    if (isChecked) {
      setSelectedProduct(products.map((product) => ({ productId: product.prod_id }))); // 모든 제품 선택
    } else {
      setSelectedProduct([]); // 선택 해제
    }
  };

  // 마감 처리 함수
  const onSubmit = async () => {
    try {
      // 유효성 검사
      if (selectedProduct.length === 0) {
        Swal.fire({
          icon: 'error',
          title: '선택된 상품이 없습니다.',
          text: '상품을 선택하고 요청 수량과 매장을 입력해주세요.',
        });
        return;
      }

      // 선택된 데이터 필드 검사
      for (const product of selectedProduct) {
        if (!product.storeId || !product.requestQuantity) {
          Swal.fire({
            icon: 'error',
            title: '입력 값이 누락되었습니다.',
            text: '모든 상품의 매장 ID와 요청 수량을 입력해주세요.',
          });
          return;
        }
        if (isNaN(product.requestQuantity) || product.requestQuantity <= 0) {
          Swal.fire({
            icon: 'error',
            title: '유효하지 않은 요청 수량입니다.',
            text: '요청 수량은 1 이상의 숫자여야 합니다.',
          });
          return;
        }
      }

      // 서버 요청
      const response = await axios({
        method: "POST",
        url: `${BASE_URL}/api/rt/submit`,
        data: { instructions: selectedProduct },
        withCredentials: true,
        headers: {
          "X-CSRF-TOKEN": csrfToken,
          "Content-Type": "application/json",
        },
      });

      // 응답 확인
      if (response && response.status === 200) {
        Swal.fire({
          icon: 'success',
          title: '마감 처리가 완료되었습니다.',
        });
        setSelectedProduct([]); // 선택된 상품 초기화
      } else {
        Swal.fire({
          icon: 'warning',
          title: '마감 처리에 실패했습니다.',
          text: '다시 시도해주세요.',
        });
      }
    } catch (err) {
      console.error("Error:", err);
      Swal.fire({
        icon: 'error',
        title: '서버 요청 중 오류가 발생했습니다.',
        text: err.response?.data?.message || '알 수 없는 오류가 발생했습니다.',
      });
    }
  };

  // 컴포넌트 로드 시 데이터 가져오기
  useEffect(() => {
    fetchData(`${BASE_URL}/api/rt/all`, setSelectOptions);
  }, []);
  

  return (
    <>
      <header className={MainStyle.mainTop}>
        <div className={MainStyle.buttonSet}>
          <button 
            className={`${MainStyle.button} ${customStyle.allSearchButton}`} 
            onClick={() => userInputData('all')}>
              전체조회
          </button>
          <button 
            className={`button ${customStyle.navButton}`}
            onClick={goSearch}>취소
          </button>
          <button
          className={`button ${customStyle.orderCommitButton}`} 
          onClick={onSubmit}>마감처리</button>
        </div>
      </header>

      <section className={customStyle.inputSection}>
        <div className={customStyle.inputContainer}>
          <div className={customStyle.searchSelect}>
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
            <select
              name="colorCode"
              value={userInput.colorCode}
              onChange={(e) =>
                setUserInput({ ...userInput, colorCode: e.target.value })
              }
            >
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

          <div className={customStyle.searchInputPart}>
            <input
              type="text"
              className={customStyle.searchInput}
              placeholder="상품명을 입력해주세요"
              name="input"
              maxLength= "20"
              onChange={(e) =>
                setUserInput({ ...userInput, input: e.target.value })
              }
            />
            <button type="submit" className={customStyle.submitButton} onClick={userInputData}>
              검색
            </button>
          </div>
        </div>
      </section>

      <section className={MainStyle.RtTableSection}>
        <table className={MainStyle.RtTable}>
          <thead>
            <tr>
              <th className={MainStyle.allCheck}>
                <input
                  className={MainStyle.checkbox}
                  type="checkbox"
                  checked={selectAll}
                  onChange={(e) => handleSelectAll(e.target.checked)}
                />
              </th>
              <th>상품코드</th>
              <th>상품명</th>
              <th>색상</th>
              <th>사이즈</th>
              <th>지시수량</th>
              <th>지시매장</th>
            </tr>
          </thead>
          <tbody>
            {products && products.length > 0 ? 
            (products.map((item) => (
                <tr key={item.prod_id}>
                  <td>
                    <input
                      className={MainStyle.checkbox}
                      type="checkbox"
                      checked={selectedProduct.some((data) => data.productId === item.prod_id)}
                      onChange={() => onChangeCheckBox(item)}
                    />
                  </td>
                  <td>{item.product_code}</td>
                  <td>{item.product_name}</td>
                  <td>{item.color_code}</td>
                  <td>{item.product_size}</td>
                  <td>
                    <input
                      type="number"
                      placeholder="수량"
                      onChange={(e) =>
                        onChangeInput(item.prod_id, "requestQuantity", e.target.value)
                      }/>
                  </td>
                  <td>
                    <select
                      onChange={(e) => {
                        onChangeInput(item.prod_id, "storeId", e.target.selectedIndex);
                      }}>
                      <option value="">매장 선택</option>
                      {mapSelectOptions("store_name")}
                    </select>
                  </td>
                </tr>
              ))
            ) : (
              <tr className={MainStyle.noDataTr}>
                    <td className={MainStyle.noData}>
                      데이터가 없습니다
                    </td>
              </tr>
            )}
         </tbody>
        </table>
      </section>
    </>
  );
};

export default RtRegister;