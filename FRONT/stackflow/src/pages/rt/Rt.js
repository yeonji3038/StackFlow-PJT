import "./Rt.css";

const Rt = () => {
  return(
    <div className="container">
      <h1>매장 RT 관리 </h1>
      <hr />

      <div className="mainContent">
        <div className="inputData">
          <span>지시기간</span>
          <input class="dateInput" type="date" />
          <span>처리구분</span>
          <input type="checkbox" />
          <input type="checkbox" />
          <input type="checkbox" />
          <span>매장</span> 
          <input type="text" />
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
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>YJBOTTEMPTM</td>
                <td>GG바지</td>
                <td>BK</td>
                <td>FREE</td>
                <td>수원점</td>
                <td>1</td>
                <td>24-12-10 16:19:34 </td>
              </tr>
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
              <tr>
                <td>YJBOTTEMPTM</td>
                <td>GG바지</td>
                <td>BK</td>
                <td>FREE</td>
                <td>수원점</td>
                <td>1</td>
                <td>24-12-10 16:19:34 </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      

    </div>
  )
}

export default Rt