import axios from "axios"

const Notice = () => {
  const BASE_URL = "http://localhost:8080"
  const csrfToken = "72900BBA49A02D53A893D789E339F216"; // CSRF 토큰

   // userInput func
   const GETTEST = async (commend) => {
    try {
      const response = await axios({
        method: "GET",
        url: `${BASE_URL}/notice/api`,
        withCredentials: true,
        headers: {
          "X-CSRF-TOKEN": csrfToken,
        },
      })
      console.log(response)
      } catch (err) {
        console.error("Error fetching or filtering data:", err);
        }
    } 
    
    const GETDETAILTEST = async (commend) => {
      try {
        const response = await axios({
          method: "GET",
          url: `${BASE_URL}/notice/api/1`,
          withCredentials: true,
          headers: {
            "X-CSRF-TOKEN": csrfToken,
          },
        })
        console.log(response)
        } catch (err) {
          console.error("Error fetching or filtering data:", err);
          }
      } 
      const DELETETEST = async (commend) => {
        try {
          const response = await axios({
            method: "DELETE",
            url: `${BASE_URL}/notice/api/delete/3`,
            withCredentials: true,
            maxRedirects: 0,
            headers: {
              "x-www-form-urlencoded": csrfToken,
            }
           
          })
          console.log(response)
          } catch (err) {
            console.error("Error fetching or filtering data:", err);
            }
        } 
  

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
      <button onClick={DELETETEST}>test</button>
    </>

  )
}

export default Notice