const Notice = () => {
  return (
    <>
      <h1>공지사항</h1>
      <hr />
      <div className="content">
        <div className="inputData">
          <input type="text" /> 
          <button>검색</button>
        </div>
        <button>글쓰기</button>
      </div>
      <table>
        <thead>
          <th>No</th>
          <th>제목</th>
          <th>등록일</th>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td>기존 고객 추가 매장 등록</td>
            <td>2024-12-10</td>
          </tr>

        </tbody>

      </table>
    </>

  )
}

export default Notice