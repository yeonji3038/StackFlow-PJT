import React, { useState } from "react";
import styles from "./ManagementPage.module.css";

const ManagementPage = () => {
  const [customerData, setCustomerData] = useState([
    {
      id: "hellohi123",
      company: "hellohi",
      businessNumber: "453453254545",
      email: "hellohi@naver.com",
      storeCode: "ST001",
      approvalDate: "2024-12-04",
      accountStatus: "approve",
      storeCount: 5,
    },
    {
      id: "stackoverflow12",
      company: "stackoverflow",
      businessNumber: "578365435472",
      email: "stackoverflow12@naver.com",
      storeCode: "ST002",
      approvalDate: "X",
      accountStatus: "inactive",
      storeCount: 2,
    },
    {
      id: "godls0215",
      company: "godls",
      businessNumber: "1235645634357",
      email: "godls0215@naver.com",
      storeCode: "ST003",
      approvalDate: "2024-12-01",
      accountStatus: "denied",
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
      <h1 className={styles.title}>MANAGEMENT PAGE</h1>
      <hr />
      <section className={styles.section}>
        <h2 className={styles.sectionTitle}>매장 관리</h2>
        <table className={styles.table}>
          <thead>
            <tr>
              <th>아이디</th>
              <th>회사명</th>
              <th>이메일</th>
              <th>매장코드</th>
              <th>승인 날짜</th>
              <th>승인 상태</th>
              <th>승인 변경</th>
              <th>삭제</th>
            </tr>
          </thead>
          <tbody>
            {customerData.map((customer) => (
              <tr key={customer.id}>
                <td>{customer.id}</td>
                <td>{customer.company}</td>
                <td>{customer.email}</td>
                <td>{customer.storeCode}</td>
                <td>{customer.approvalDate}</td>
                <td>{customer.accountStatus}</td>
                <td>
                  <label className={styles.switch}>
                    <input 
                      type="checkbox" 
                      checked={customer.accountStatus === "approve"}
                      onChange={() => handleStatusChange(customer.id)}
                    />
                    <span className={styles.slider}></span>
                  </label>
                </td>
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

export default ManagementPage;
