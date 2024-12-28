import "./RtMain.css";
import "./RtRegister.css";
import axios from "axios";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";

const RtRegister = () => {
  const csrfToken = "9218C4C25DA62D44A15CCE2EB5FC52EA"; // CSRF 토큰
  const BASE_URL = "http://localhost:8080"

// define ===============================================
  
  // nav
  const nav = useNavigate();
    
  // state 
  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState([]); // 선택된 상품 목록
  const [selectOptions, setSelectOptions] = useState([]) // option에 넣을 데이터
  const [userInput, setUserInput] = useState({
    categoryGroup: "",  
    categoryCode: "",
    colorCode: "",
    size: "",
    input: "",
  });

// function ============================================
  
  // input data for filtering 
  const onChangeInput = (productId, field, value) => {
    setSelectedProduct((preData) =>
      preData.map((data) =>
        data.productId === productId ? { ...data, [field]: value } : data
      )
    );
  };

  // userInput func
  const userInputData = async (commend) => {
    try {
      const response = await axios({
        method: "GET",
        url: `${BASE_URL}/api/rt/product`,
        withCredentials: true,
        headers: {
          "X-CSRF-TOKEN": csrfToken,
        },
      })
      if (commend === 'all') {
        setProducts(response.data)
      } else {
        const filteredData = response.data.filter((item) => {
          return (
            (userInput.categoryGroup ? userInput.categoryGroup === item.category_group : true) &&
            (userInput.categoryCode ? userInput.categoryCode === item.category_code : true) &&
            (userInput.colorCode ? userInput.colorCode === item.color_code : true) &&
            (userInput.size ? userInput.size === item.product_size : true) 
          );
        });
        setProducts(filteredData);
      }  
    } catch (err) {
      console.error("Error fetching or filtering data:", err);
      }
    };
  
  // mapping options data
  const mapSelectOptions = (data) => {
    if (selectOptions[data]) {
      const entry = Object.entries(selectOptions[data])
      return entry.map(([key, value]) => (
        <option itemID={key} key={key} value={value}>
          {value}
        </option>
      ))
    } 
  };
   
  // nav to rt/search
  const goSearch = () => {
    return nav("../search");
  };
  
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

  // check box on / off handling
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

  // 랜더링 시 함수 호출 
  useEffect(() => {
    fetchData(`${BASE_URL}/api/rt/all`,setSelectOptions)
  }, []);

  // 마감처리
  const handleSubmit = async () => {
    try {
      const response = await axios({
        method : "POST",
        url : `${BASE_URL}/api/rt/submit`,
        data : { instructions: selectedProduct },
        withCredentials : true,
        maxRedirects : 0,
        headers : {
          "X-CSRF-TOKEN": csrfToken,
          "Content-Type": "application/json",
        },
    });
    if (response) {
      Swal.fire({
      icon: 'success',
      title: '마감 처리가 완료되었습니다.',
    }) 
    }
        
    } catch (err) {
      console.error("Error:", err);
    }
  };

  return (
    <>
      <header className="searchSection">
        <div className="searchButtons">
          <button 
            className="button allSearchButton" 
            onClick={() => userInputData('all')}>
              전체조회
          </button>
          <button 
            className="button navButton"
            onClick={goSearch}>취소
          </button>
          <button
          className="button orderCommitButton" 
          onClick={handleSubmit}>마감처리</button>
        </div>
      </header>

      <section className="userInputSection">
        <div className="userInputContainer">
          <div className="userSearchSelect">
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
              }
            >
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
              onChange={(e) => setUserInput({ ...userInput, size: e.target.value })}
            >
              <option value={""}>사이즈</option>
              {mapSelectOptions("size")}
            </select>
          </div>

          <div className="SearchInputPart">
            <input
              type="text"
              className="userSearchInput"
              placeholder="상품명을 입력해주세요"
              name="input"
              maxLength= "20"
              onChange={(e) =>
                setUserInput({ ...userInput, input: e.target.value })
              }
            />
            <button type="submit" className="submitButton" onClick={userInputData}>
              검색
            </button>
          </div>
        </div>
      </section>

      <article className="table">
        <table>
          <thead>
            <tr>
              <th className="chooseProduct">
                <input type="checkbox" />
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
            {products.length > 0 ? (
              products.map((item) => (
                <tr key={item.prod_id}>
                  <td>
                    <input
                      type="checkbox"
                      onChange={(e) =>
                        onChangeCheckBox(item, e.target.checked)
                      }
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
                      }
                    />
                  </td>
                  <td>
                    <select
                      onChange={(e) => {
                        onChangeInput(item.prod_id, "storeId", e.target.selectedIndex -1)

                      }
                      }
                    >
                      <option value="">매장 선택</option>
                      {mapSelectOptions("store_name")}
                    </select>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td className="noData" colSpan="7">
                  데이터가 없습니다
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </article>
    </>
  );
};

export default RtRegister;