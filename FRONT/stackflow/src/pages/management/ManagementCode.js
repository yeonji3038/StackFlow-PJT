import React, { useState } from "react";
import styles from "./ManagementCode.module.css";

const ManagementCode = () => {
  const [storeCodeData, setStoreCodeData] = useState([
    {
      storeCode: "ST001",
      company: "hellohi",
      registrationDate: "2024-03-20",
      status: "active",
      description: "본사"
    },
    {
      storeCode: "ST002",
      company: "stackoverflow",
      registrationDate: "2024-03-21",
      status: "inactive",
      description: "강남지점"
    }
  ]);

  const handleStatusChange = (storeCode) => {
    setStoreCodeData((prev) =>
      prev.map((store) =>
        store.storeCode === storeCode
          ? {
              ...store,
              status: store.status === "active" ? "inactive" : "active"
            }
          : store
      )
    );
  };

  const handleDelete = (storeCode) => {
    setStoreCodeData((prev) => 
      prev.filter((store) => store.storeCode !== storeCode)
    );
  };

  return (
    <div className={styles.managementPage}>
      <h1 className={styles.title}>매장 코드 생성</h1>
      <hr />
      <div className={styles.contentWrapper}>
        {/* 좌측 컬럼 - 매장 목록 */}
        <div className={styles.leftColumn}>
          <section className={styles.section}>
            <table className={styles.table}>
              <thead>
                <tr>
                  <th>매장 코드</th>
                  <th>회사명</th>
                  <th>등록일</th>
                  <th>상태</th>
                  <th>매장 설명</th>
                  <th>상태 변경</th>
                  <th>삭제</th>
                </tr>
              </thead>
              <tbody>
                {storeCodeData.map((store) => (
                  <tr key={store.storeCode}>
                    <td>{store.storeCode}</td>
                    <td>{store.company}</td>
                    <td>{store.registrationDate}</td>
                    <td>{store.status}</td>
                    <td>{store.description}</td>
                    <td>
                      <label className={styles.switch}>
                        <input 
                          type="checkbox" 
                          checked={store.status === "active"}
                          onChange={() => handleStatusChange(store.storeCode)}
                        />
                        <span className={styles.slider}></span>
                      </label>
                    </td>
                    <td>
                      <button 
                        className={styles.deleteButton}
                        onClick={() => handleDelete(store.storeCode)}
                        title="삭제"
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </section>
        </div>
        
        {/* 우측 컬럼 - 지도 */}
        <div className={styles.rightColumn}>
          <div className={styles.mapContainer}>
            {/* 여기에 지도 API 컴포넌트 */}
            <p>지도가 표시될 영역</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ManagementCode;
