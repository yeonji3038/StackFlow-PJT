import React, { useState } from "react";
import styles from "./ManagementStore.module.css";

const ManagementStore = () => {
  const [customerData, setCustomerData] = useState([
    {
      id: "hellohi123",
      store: "수원점",
      businessNumber: "453453254545",
      email: "hellohi@naver.com",
      storeCode: "ST001",
      storeCount: 5,
    },
    {
      id: "stackoverflow12",
      store: "대구점",
      businessNumber: "578365435472",
      email: "stackoverflow12@naver.com",
      storeCode: "ST002",
      storeCount: 2,
    },
    {
      id: "godls0215",
      store: "연천점",
      businessNumber: "1235645634357",
      email: "godls0215@naver.com",
      storeCode: "ST003",
      storeCount: 6,
    },
  ]);

  const handleStatusChange = (id) => {
    setCustomerData((prev) =>
      prev.map((customer) =>
        customer.id === id
          ? {
              ...customer,
              accountStatus: customer.accountStatus === "approve" ? "inactive" : "approve",
              approvalDate: customer.accountStatus === "approve" ? "X" : new Date().toISOString().split('T')[0]
          }
          : customer
      )
    );
  };

  const handleDelete = (id) => {
    setCustomerData((prev) => prev.filter((customer) => customer.id !== id));
  };

  return (
    <div className={styles.managementPage}>
      <h1 className={styles.title}>매장 관리</h1>
      <hr />
      <section className={styles.section}>
        <table className={styles.table}>
          <thead>
            <tr>
              <th>아이디</th>
              <th>매장명</th>
              <th>이메일</th>
              <th>매장코드</th>
              {/* <th>승인 날짜</th>
              <th>승인 상태</th> */}
              {/* <th>승인 변경</th> */}
              <th>삭제</th>
            </tr>
          </thead>
          <tbody>
            {customerData.map((customer) => (
              <tr key={customer.id}>
                <td>{customer.id}</td>
                <td>{customer.store}</td>
                <td>{customer.email}</td>
                <td>{customer.storeCode}</td>
                {/* <td>{customer.approvalDate}</td>
                <td>{customer.accountStatus}</td> */}
                {/* <td>
                  <label className={styles.switch}>
                    <input 
                      type="checkbox" 
                      checked={customer.accountStatus === "approve"}
                      onChange={() => handleStatusChange(customer.id)}
                    />
                    <span className={styles.slider}></span>
                  </label>
                </td> */}
                <td>
                  <button 
                    className={styles.deleteButton}
                    onClick={() => handleDelete(customer.id)}
                    title="삭제"
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </div>
  );
};

export default ManagementStore;
