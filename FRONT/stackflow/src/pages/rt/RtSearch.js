import "./RtMain.css"
import "./RtSearch.css"

import { useState, useEffect } from 'react'
import { useNavigate } from "react-router-dom";
import axios from "axios"
import moment from 'moment'
import { useConfig } from "../../store";
import useToken from "../../store/tokenManage";

const RtSearch = () => {
  const { getToken } = useToken()
  const csrfToken =  getToken() 
  const { BASE_URL } = useConfig()

  // 상태 변수 정의
  const [products, setProducts] = useState([]);  // 조회된 상품 데이터 저장
  const [selectedProduct, setSelectedProduct] = useState([]); // 선택된 상품 목록
  const [myRtData, myRtSetData] = useState([]) // 데이터를 저장할 상태
  const [otherRtData, otherRtSetData] = useState([]) // 데이터를 저장할 상태
  const [storeList, storeListSetData ] = useState([])  // store 정보 담는 변수 

  // 날짜 데이터 포멧 함수
  const formatDate = (date) => {
    return moment(date).format('YYYY-MM-DD HH:MM')
  }

  // nav
  const nav = useNavigate()

  const goRegister = () => {
    return nav('../register')
  }

  // 필터링 조건을 담는 변수 
  const [filterSelct, setFilterSelect] = useState({
    date : "",
    check : "",
    select : ""
  })

  // 필터링 조건 담는 이벤트 함수
  const onChangeInput = (e) => {
    setFilterSelect({
      ...filterSelct,
      [e.target.name] : e.target.value
    })
  }

  // 체크박스에 사용되는 옵션들 
  const checkboxOptions = [
    { id: "REQUEST", label: "미확정", value: "REQUEST" },
    { id: "APPROVAL", label: "확정", value: "APPROVAL" },
    { id: "REFUSE", label: "불이행", value: "REFUSE" },
  ];

  // RtFilterBox의 checkbox 해제 함수
  const onClickCheck = (e) => {
    const checkTag = document.getElementById(`${filterSelct.check}`);
    if (filterSelct.check && e.target.value === filterSelct.check) {
      checkTag.checked = false
      filterSelct.check = ''
    } 
  }

 // 서버로부터 데이터를 가져오는 함수
  const fetchData = async (url, setData) => {
    try {
      const response = await axios.get(url, {
        withCredentials: true,
        maxRedirects: 0, // 리다이렉션 방지
        headers: {
          'X-CSRF-TOKEN': csrfToken,
        },
      });
      setData(response.data); // 데이터를 상태에 저장
    } catch (err) {
      if (err.response && err.response.status === 302) {
        console.error('리다이렉션 발생:', err.response.headers.location);
      } else {
        console.error('Error:', err);
      }
    }
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

  


  // rt_status 매핑 객체 
  const statusMap = {
    "REQUEST" : '미확정',
    "APPROVAL" : '확정',
    "REFUSE" : '대기',
  };

  // myRtdata filter 함수
  const filteredData = async () => {
    try {
      // 데이터 가져오기
      const response = await axios({
        method: "GET",
        url: `${BASE_URL}/api/rt/meToOtherRtlist`,
        withCredentials: true,
        maxRedirects: 0, // 리다이렉션 방지
        headers: {
          'X-CSRF-TOKEN': csrfToken,
        },
      });
      if (response && response.status === 200){
        // 데이터 필터링
        const filtered = filterSelct.date  || filterSelct.check  || filterSelct.select 
        ? response.data.filter((item) => {
            return (
              (filterSelct.date ? item.request_date.includes(filterSelct.date) : true) &&
              (filterSelct.check ? item.rt_status === filterSelct.check : true) &&
              (filterSelct.select ? item.request_store === filterSelct.select : true)
            );
          })
        : response.data; // input 값이 없으면 원래 데이터 반환
        // 필터링된 데이터 상태 업데이트
        myRtSetData(filtered);
      }  
    } catch (err) {
      console.error('Error fetching or filtering data:', err);
    }
  };

  // 페이지 랜더링 시 호출되는 함수
  useEffect(() => {
      fetchData(`${BASE_URL}/api/rt/meToOtherRtlist`, myRtSetData)
      fetchData('http://localhost:8080/api/rt/OtherToMeRtlist', otherRtSetData);
      fetchData('http://localhost:8080/api/rt/store', storeListSetData);
    }, []);
   
  return (
    <>
      <section className="mainTop">
        <div className="RtFilterBox">
          <div className="dateSelector">
              <label className="deteSelectorName">지시기간</label>
              <input id="inputDate" type="date" name="date"
                value={filterSelct.date} onChange={onChangeInput}/>
          </div>  
          
          <div className="processingCheckbox">
            <label className="checkboxName">처리구분</label>
            {checkboxOptions.map((option) => (
              <span key={option.id} className={`checkboxItem ${option.id}`}>
                <label htmlFor={option.id}>{option.label}</label>
                <input
                  type="radio"
                  id={option.id}
                  name="check"
                  value={option.value}
                  onChange={onChangeInput}
                  onClick={onClickCheck}
                />
              </span>
            ))}
          </div>

          <div className="storeSelector">
            <label className="storeSelectorName">매장</label> 
            <select 
              name="select" 
              value={filterSelct.select}
              onChange={onChangeInput}
            >
              <option value={''}>매장 선택</option>
              {storeList.map((store, index) => {
                return (
                  <option key={index}>
                    {store.store_name}
                  </option>
                )
                })}
            </select>
          </div>
      </div>
          
          <div className="buttonSet">
            <button id="searchButton" 
              onClick={filteredData}>조회</button>
            <button id="createButton" onClick={goRegister}>등록</button>
          </div>
      </section>
        
        <section className="RtTableSection">
          <div className="tableName">
            <h3>지시요청 조회</h3>  
          </div>
          <div className="table">
            <table>
              <thead>
                <tr>
                  <th>품번</th>
                  <th>품명</th>
                  <th>색상</th>
                  <th>사이즈</th>
                  <th>지시매장</th>
                  <th>지시수량</th>
                  <th>RT 지시일</th>
                  <th>상태</th>

                </tr>
              </thead>
              <tbody>
                {myRtData.length > 0 ? (
                  myRtData.map((item, index) => (
                    <tr key={item.rt_id}>
                      <td>{item.product_code}</td>
                      <td>{item.product_name}</td>
                      <td>{item.color_code}</td>
                      <td>{item.product_size}</td>
                      <td>{item.request_store}</td>
                      <td>{item.rt_products[0].request_quantity}</td>
                      <td>{formatDate(item.request_date)}</td>
                      <td>{statusMap[item.rt_status]}</td>
                    </tr>
                  ))
                ) : ( 
                  <tr className="noDataTr" style={{ height: "100%" }}>
                    <td className="noData">
                      데이터가 없습니다
                    </td>
                </tr>
                )}        
              </tbody>
            </table>
          </div>
        </section>

        <section className="RtTableSection otherRtTable">
          <div className="otherRtTableTop">  
            <div className="tableName">
              <h3>타매장 지시요청</h3>  
              <div className="buttonSet otherRtButton">
                <button>취소</button>
                <button>승인</button>
            </div>
            </div>
          </div>
          <div className="table">   
            <table>
              <thead>
                <tr>
                  <th className="chooseProduct">
                    <input
                      type="checkbox"
                      checked={selectAll}
                      onChange={(e) => handleSelectAll(e.target.checked)}
                    />
                  </th>
                  <th>요청매장</th>
                  <th>품번</th>
                  <th>품명</th>
                  <th>색상</th>
                  <th>사이즈</th>
                  <th>지시수량</th>
                  <th>RT 요청일</th>
                </tr>
                
              </thead>
              <tbody>
              {otherRtData.length > 0 ? (
                otherRtData.map((item, index) => {
                  return (
                    <tr key={item.rt_id}>
                      <td>
                      <input
                        type="checkbox"
                        checked={selectedProduct.some((data) => data.productId === item.prod_id)}
                        onChange={() => onChangeCheckBox(item)}
                      />
                      </td>
                      <td>{item.rt_products[0].request_quantity}</td>
                      <td>{item.product_code}</td>
                      <td>{item.product_name}</td>
                      <td>{item.color_code}</td>
                      <td>{item.product_size}</td>
                      <td>{item.request_store}</td>
                      <td>{formatDate(item.request_date)}</td>
                    </tr>
                  )
                })                  
              ): (
                <tr>
                  <td className="noData">
                  데이터가 없습니다
                  </td>
                </tr>
                )
              }
               </tbody>
            </table>
          </div>
        </section>
    </>
  )
}

export default RtSearch