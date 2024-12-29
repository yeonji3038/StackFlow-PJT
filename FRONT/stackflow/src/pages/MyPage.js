import React, { useState } from 'react';
import styles from './MyPage.module.css';

const MyPage = () => {
  const [formData, setFormData] = useState({
    id: 'user123',
    password: '',
    passwordConfirm: '',
    email: '',
    storeCode: 'ST001',
    storeName: '',
    phone: '',
    address: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formData);
  };

  return (
    <div className={styles.managementPage}>
      <h1 className={styles.title}>MY PAGE</h1>
      <hr />
      <div className={styles.formWrapper}>
        <div className={styles.formCard}>
          <h2 className={styles.sectionTitle}>매장 정보 수정</h2>
          <form className={styles.form} onSubmit={handleSubmit}>
            <div className={styles.inputGroup}>
              <label htmlFor="id">아이디</label>
              <input
                id="id"
                className={`${styles.input} ${styles.readOnly}`}
                type="text"
                name="id"
                value={formData.id}
                readOnly
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="password">비밀번호</label>
              <input
                id="password"
                className={styles.input}
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="passwordConfirm">비밀번호 확인</label>
              <input
                id="passwordConfirm"
                className={styles.input}
                type="password"
                name="passwordConfirm"
                value={formData.passwordConfirm}
                onChange={handleChange}
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="email">이메일</label>
              <input
                id="email"
                className={styles.input}
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="storeCode">매장코드</label>
              <input
                id="storeCode"
                className={`${styles.input} ${styles.readOnly}`}
                type="text"
                name="storeCode"
                value={formData.storeCode}
                readOnly
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="storeName">지점명</label>
              <input
                id="storeName"
                className={styles.input}
                type="text"
                name="storeName"
                value={formData.storeName}
                onChange={handleChange}
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="phone">전화번호</label>
              <input
                id="phone"
                className={styles.input}
                type="tel"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
              />
            </div>
            <div className={styles.inputGroup}>
              <label htmlFor="address">매장 주소</label>
              <input
                id="address"
                className={styles.input}
                type="text"
                name="address"
                value={formData.address}
                onChange={handleChange}
              />
            </div>
            <div className={styles.buttonContainer}>
              <button type="submit" className={styles.submitButton}>수정</button>
              <button type="button" className={styles.cancelButton}>취소</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
