import "./Rt.css";
import axios from "axios"
import { useState } from 'react'

import dumydata from './dumydata.json'

const Rt = () => {
  const otherRtList = dumydata
  

  const submitData = async (data) => {
    const csrfToken = "F29D2704967D3A7C165C2024A5E80BE3"

    await axios.get('http://localhost:8080/api/rt/meToOtherRtlist', {
      withCredentials: true,
      maxRedirects: 0, // 리다이렉션 방지
      headers: {
        'X-CSRF-TOKEN': csrfToken,
      },
    })
    .then((res) => {
      console.log(res.data)
    })
    .catch((err) => {
      if (err.response && err.response.status === 302) {
        console.error('리다이렉션 발생:', err.response.headers.location);
      } else {
        console.error('Error:', err);
      }
    });
  }

  submitData()
  // axios({
  //   method: 'GET',
  //   url: 'http://localhost:8080/api/rt/meToOtherRtlist',
  //   withCredentials: true, // 쿠키를 포함
  // })
  //   .then((res) => {
  //     console.log(res.data);
  //   })
  //   .catch((err) => {
  //     console.error('Error:', err);
  //   });

 
  
  function input(){
    const date = document.querySelector('#input_date').value
    console.log(date)
  }


  return(
    <div className="rtPage">
      <h1>매장 RT 관리 </h1>
      <hr />
      
      <div className="mainContent">
        
        <div className="searchBar">
          <div className="date">
            <label className="name">지시기간</label>
            <input id="input_date" type="date" onChange={input}/>
          </div>

          <div className="checkbox">
            <label className="name">처리구분</label>
            <span className="fristCheckbox">
              <label>미확정</label>
              <input type="radio" />
            </span>
            <span>
              <label>확정</label>
              <input type="radio" />
            </span>
            <span>
              <label>불이행</label>
              <input type="radio" />
            </span>
          </div>

          <div className="selector">
            <label className="name">매장</label> 
            <select>
              <option>매장 선택</option>
              <option>부산 서면점</option>
              <option>수원점</option>
              <option>강남점</option>
              <option>대전점</option>
              

            </select>
          </div>

          <div className="buttons">
            <button>조회</button>
            <button>등록</button>
          </div>
        </div>
        
        <div className="searchTable">
          <div className="tableName">
            <h3>지시요청 조회</h3>  
          </div>
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
              {otherRtList.map((item, index)=> {
                return (
                  <tr key={item.prodCode}> 
                    <td>{item.prodCode}</td>
                    <td>{item.prodName}</td>
                    <td>{item.colorCode}</td>
                    <td>{item.size}</td>
                    <td>{item.reqStore}</td>
                    <td>{item.rtProducts[0].reqQuant}</td>
                    <td>{item.reqDate}</td>
                    <td>{item.status}</td>
                  </tr>
                )})}
            </tbody>
          </table>
        </div>
        <div className="searchTable">
          <div className="tableName">
            <h3>타매장 지시요청</h3>  
          </div>
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
              </tr>
              
            </thead>
            <tbody>
              
            {otherRtList.map((item)=> {
                return (
                  <tr key={item.prodCode}> 
                    <td>{item.prodCode}</td>
                    <td>{item.prodName}</td>
                    <td>{item.colorCode}</td>
                    <td>{item.size}</td>
                    <td>{item.reqStore}</td>
                    <td>{item.rtProducts[0].reqQuant}</td>
                    <td>{item.reqDate}</td>
                    <td>{item.status}</td>
                  </tr>
                )})}
            </tbody>
          </table>
        </div>
      </div> 
      
    </div>
  )
}

export default Rt