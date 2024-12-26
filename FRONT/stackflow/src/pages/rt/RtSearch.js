import "./Rt.css";
import axios from "axios"
import { useState, useEffect } from 'react'
import moment from 'moment'
import { useNavigate } from "react-router-dom";

const RtSearch = () => {

   //  검색 조건 담는 변수
   const [input, setInput] = useState({
    date : "",
    check : "",
    select : ""
  })

  // 검색 조건 담는 이벤트 함수
  const onChangeInput = (e) => {
    setInput({
      ...input,
      [e.target.name] : e.target.value
    })
  }

  // 데이터 가져오는 함수 
  const [myRtData, myRtSetData] = useState([]) // 데이터를 저장할 상태
  const [otherRtData, otherRtSetData] = useState([]) // 데이터를 저장할 상태
  const [storeList, storeListSetData ] = useState([])
  // const [test, setTest ] = useState([])
  const csrfToken = "38354D593B8BEC5217C2CF402FC25EFC"; // CSRF 토큰

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

    useEffect(() => {
      fetchData('http://localhost:8080/api/rt/meToOtherRtlist', myRtSetData)
      fetchData('http://localhost:8080/api/rt/OtherToMeRtlist', otherRtSetData);
      fetchData('http://localhost:8080/api/rt/store', storeListSetData);
    }, []);

    const filteredData = async () => {
      try {
        // 데이터 가져오기
        const response = await axios.get('http://localhost:8080/api/rt/meToOtherRtlist', {
          withCredentials: true,
          maxRedirects: 0, // 리다이렉션 방지
          headers: {
            'X-CSRF-TOKEN': csrfToken,
          },
        });
        // 데이터 필터링
        const filtered = input.date  || input.check  || input.select 
        ? response.data.filter((item) => {
            return (
              (input.date ? item.request_date === input.date : true) &&
              (input.check ? item.rt_status === input.check : true) &&
              (input.select ? item.request_store === input.select : true)
            );
          })
        : response.data; // input 값이 없으면 원래 데이터 반환
       
        // 필터링된 데이터 상태 업데이트
        myRtSetData(filtered);
      } catch (err) {
        console.error('Error fetching or filtering data:', err);
      }
    };

    // rt_status 매핑 객체 
    const statusMap = {
      "REQUEST" : '미확정',
      "APPROVAL" : '확정',
      "REFUSE" : '대기',
    };

    // check 해제 함수
    const onClickCheck = (e) => {
      const checkTag = document.getElementById(`${input.check}`);
      if (input.check && e.target.value === input.check) {
        checkTag.checked = false
        input.check = ''
      } 
    }

    // 날짜 데이터 포멧 함수
    const formatDate = (date) => {
      return moment(date).format('YYYY-MM-DD HH:MM')
    }

    const nav = useNavigate()

    const goRegister = () => {
      return nav('../register')
    }
  return (
    <>
     <div className="searchBar">
          <div className="date">
            <label className="name">지시기간</label>
            <input 
              id="input_date" 
              type="date" 
              name="date"
              
              value={input.date} 
              onChange={onChangeInput}/>
          </div>

          <div className="checkbox">
            <label className="name">처리구분</label>
            <span className="fristCheckbox">
              <label htmlFor="REQUEST">미확정</label>
              <input 
                type="radio" 
                id="REQUEST" 
                name="check"
                value="REQUEST" 
                onChange={onChangeInput}
                onClick={onClickCheck}
                
                />
            </span>
            <span>
              <label htmlFor="APPROVAL">확정</label>
              <input 
                type="radio" 
                id="APPROVAL" 
                name="check"
                value="APPROVAL" 
                onChange={onChangeInput}
                onClick={onClickCheck}
                />
                
            </span>
            <span>
              <label htmlFor="REFUSE">불이행</label>
              <input 
                type="radio" 
                id="REFUSE" 
                name="check"
                value="REFUSE"
                onChange={onChangeInput}
                onClick={onClickCheck}
                />
            </span>
          </div>

          <div className="selector">
            <label className="name">매장</label> 
            <select 
              name="select" 
              value={input.select}
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

          <div className="buttons">
            <button id="searchButton" 
              onClick={filteredData}>조회</button>
            <button id="createButton" onClick={goRegister}>등록</button>
          </div>
        </div>
        
        <div className="searchTable">
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
                  <tr>
                    <td className="noData">
                      데이터가 없습니다
                    </td>
                </tr>
                )}        
              </tbody>
            </table>
          </div>
        </div>
        <div className="searchTable">
          <div className="tableName">
            <h3>타매장 지시요청</h3>  
          </div>
          <div className="buttons">
            <button>취소</button>
            <button>승인</button>
          </div>
          <div className="table">   
            <table>
              <thead>
                <tr>
                  <th className="chooseProduct">
                    <input type="checkbox"/>
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
                      <td className="chooseProduct"><input type="checkbox"/></td>
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
        </div>
    </>
  )
}

export default RtSearch