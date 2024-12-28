import "./Rt.css";
import "./RtRegister.css";
import axios from "axios";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const RtRegister = () => {
  const csrfToken = "297774247591B7CDF92FE8D68B95011F"; // CSRF 토큰

  const [category, setCategory] = useState([]);
  const [categorycode, setCategoryCode] = useState([]);
  const [colorCode, setColorCode] = useState([]);
  const [size, setSize] = useState([]);
  const [products, setProducts] = useState([]);
  const [store, setStore] = useState([]);
  const [rows, setRows] = useState([]); // 선택된 상품 데이터

  const [search, setSearch] = useState({
    categoryGroup: "",
    categoryCode: "",
    colorCode: "",
    size: "",
    input: "",
  });

  const fetchData = async (url, setData) => {
    try {
      const response = await axios.get(url, {
        withCredentials: true,
        // headers: {
        //   "X-CSRF-TOKEN": csrfToken,
        // },
      });
      setData(response.data);
    } catch (err) {
      console.error("Error:", err);
    }
  };

  useEffect(() => {
    fetchData("http://localhost:8080/api/rt/categoryGroup", setCategory);
    fetchData("http://localhost:8080/api/rt/category", setCategoryCode);
    fetchData("http://localhost:8080/api/rt/color", setColorCode);
    fetchData("http://localhost:8080/api/rt/size", setSize);
    fetchData("http://localhost:8080/api/rt/store", setStore);
  }, []);

  // 체크박스 선택/해제 핸들러
  const handleCheckboxChange = (product) => {
    setRows((prevRows) => {
      if (prevRows.some((row) => row.productId === product.prod_id)) {
        // 이미 선택된 경우 제거
        return prevRows.filter((row) => row.productId !== product.prod_id);
      } else {
        // 선택되지 않은 경우 추가
        return [
          ...prevRows,
          { productId: product.prod_id, storeId: "", requestQuantity: "" },
        ];
      }
    });
  };

  // 입력값 변경 핸들러
  const handleInputChange = (productId, field, value) => {
    setRows((prevRows) =>
      prevRows.map((row) =>
        row.productId === productId ? { ...row, [field]: value } : row
      )
    );
  };

  // 검색 필터링
  const filteredData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/rt/product", {
        withCredentials: true,
        headers: {
          "X-CSRF-TOKEN": csrfToken,
        },
      });
      const filtered = response.data.filter((item) => {
        return (
          (search.colorCode ? item.color_code === search.colorCode : true) &&
          (search.size ? item.product_size === search.size : true)
        );
      });
      setProducts(filtered);
    } catch (err) {
      console.error("Error fetching or filtering data:", err);
    }
  };

  // 데이터 전송
  const handleSubmit = async () => {
    console.log(rows)
    try {
      const response = await axios.post(
        "http://localhost:8080/api/rt/submit",
        { instructions: rows },
        {
          withCredentials: true,
          maxRedirects: 0,
          headers: {
            "X-CSRF-TOKEN": csrfToken,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("Response:", response.data);
    } catch (err) {
      console.error("Error:", err);
    }
  };

  const nav = useNavigate();
  const goSearch = () => {
    return nav("../search");
  };


  
  return (
    <>
      <header className="searchBar">
        <div className="buttons">
          <button onClick={filteredData}>조회</button>
          <button onClick={goSearch}>취소</button>
          <button onClick={handleSubmit}>마감처리</button>
        </div>
      </header>

      <div className="search">
        <div className="searchSelect">
          <select
            name="categoryGroup"
            value={search.categoryGroup}
            onChange={(e) =>
              setSearch({ ...search, categoryGroup: e.target.value })
            }
          >
            <option value={""}>카테고리 그룹</option>
            {category.map((item, index) => (
              <option key={index} value={item.category_group_code}>
                {item.category_group_code}
              </option>
            ))}
          </select>
          <select
            name="categoryCode"
            value={search.categoryCode}
            onChange={(e) =>
              setSearch({ ...search, categoryCode: e.target.value })
            }
          >
            <option value={""}>카테고리 코드</option>
            {categorycode.map((item, index) => (
              <option key={index} value={item.category_code}>
                {item.category_code}
              </option>
            ))}
          </select>
          <select
            name="colorCode"
            value={search.colorCode}
            onChange={(e) =>
              setSearch({ ...search, colorCode: e.target.value })
            }
          >
            <option value={""}>색상 코드</option>
            {colorCode.map((item, index) => (
              <option key={index} value={item.color_code}>
                {item.color_code}
              </option>
            ))}
          </select>
          <select
            name="size"
            value={search.size}
            onChange={(e) => setSearch({ ...search, size: e.target.value })}
          >
            <option value={""}>사이즈</option>
            {size.map((item, index) => (
              <option key={index} value={item.size}>
                {item.size}
              </option>
            ))}
          </select>
        </div>
        <div className="searchInputGroup">
          <input
            type="text"
            className="searchInput"
            placeholder="상품명을 입력해주세요"
            name="input"
            onChange={(e) =>
              setSearch({ ...search, input: e.target.value })
            }
          />
          <button type="submit" className="search-button" onClick={filteredData}>
            검색
          </button>
        </div>
      </div>

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
                        handleCheckboxChange(item, e.target.checked)
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
                        handleInputChange(item.prod_id, "requestQuantity", e.target.value)
                      }
                    />
                  </td>
                  <td>
                    <select
                      onChange={(e) =>
                        handleInputChange(item.prod_id, "storeId", e.target.value)
                      }
                    >
                      <option value="">매장 선택</option>
                      {store.map((storeItem, index) => (
                        <option key={index} value={storeItem.store_id}>
                          {storeItem.store_name}
                        </option>
                      ))}
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