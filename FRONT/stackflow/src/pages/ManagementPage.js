import React, { useState } from "react";
import styles from "./ManagementPage.module.css";

const ManagementPage = () => {
  const [customerData, setCustomerData] = useState([
    {
      id: "hellohi123",
      company: "hellohi",
      businessNumber: "453453254545",
      email: "hellohi@naver.com",
      approvalDate: "2024-12-04",
      accountStatus: "approve",
      storeCount: 5,
    },
    {
      id: "stackoverflow12",
      company: "stackoverflow",
      businessNumber: "578365435472",
      email: "stackoverflow12@naver.com",
      approvalDate: "X",
      accountStatus: "inactive",
      storeCount: 2,
    },
    {
      id: "godls0215",
      company: "godls",
      businessNumber: "1235645634357",
      email: "godls0215@naver.com",
      approvalDate: "2024-12-01",
      accountStatus: "denied",
      storeCount: 6,
    },
  ]);

  const [inputs, setInputs] = useState({});

  const handleInputChange = (id, value) => {
    setInputs((prev) => ({
      ...prev,
      [id]: value,
    }));
  };

  const handleAddStore = (id) => {
    const inputValue = parseInt(inputs[id] || 0, 10);
    if (isNaN(inputValue) || inputValue <= 0) {
      alert("유효한 숫자를 입력하세요.");
      return;
    }

    setCustomerData((prev) =>
      prev.map((customer) =>
        customer.id === id
          ? { ...customer, storeCount: customer.storeCount + inputValue }
          : customer
      )
    );

    setInputs((prev) => ({
      ...prev,
      [id]: "",
    }));
  };

  const handleDelete = (id) => {
    setCustomerData((prev) => prev.filter((customer) => customer.id !== id));
  };

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

  return (
    <div className={styles.managementPage}>
      <h1 className={styles.title}>MANAGEMENT PAGE</h1>
      <hr />
      {/* 고객사 정보 섹션 */}
      <section className={styles.section}>
        <h2 className={styles.sectionTitle}>고객사 정보</h2>
        <hr />
        <table className={styles.table}>
          <thead>
            <tr>
              <th>아이디</th>
              <th>회사명</th>
              <th>사업자등록번호</th>
              <th>이메일</th>
              <th>승인 날짜</th>
              <th>계정 상태</th>
              <th>상태 변경</th>
              <th>매장 수</th>
              <th>매장 추가</th>
              <th>삭제</th>
            </tr>
          </thead>
          <tbody>
            {customerData.map((customer) => (
              <tr key={customer.id}>
                <td>{customer.id}</td>
                <td>{customer.company}</td>
                <td>{customer.businessNumber}</td>
                <td>{customer.email}</td>
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
                <td>{customer.storeCount}</td>
                <td className={styles.addContainer}>
                  <input
                    type="number"
                    min="1"
                    value={inputs[customer.id] || ""}
                    onChange={(e) => handleInputChange(customer.id, e.target.value)}
                    className={styles.addInput}
                    placeholder="추가"
                  />
                  <button
                    onClick={() => handleAddStore(customer.id)}
                    className={styles.addButton}
                    title="매장 추가"
                  />
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

      {/* 매장 관리 섹션 */}
      <section className={styles.section}>
        <h2 className={styles.sectionTitle}>매장 관리</h2>
        <hr />
        <table className={styles.table}>
          <thead>
            <tr>
              <th>아이디</th>
              <th>회사명</th>
              <th>이메일</th>
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
