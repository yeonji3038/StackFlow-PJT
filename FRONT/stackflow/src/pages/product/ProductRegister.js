import React, { useState } from 'react';
import styles from './ProductRegister.module.css';

const ProductRegister = () => {
  const [formData, setFormData] = useState({
    productName: '',
    brandCode: '',
    categoryGroup: '',
    categoryCode: '',
    color: '',
    size: '',
    purchasePrice: '',
    sellingPrice: '',
    stockQuantity: '',
    description: ''
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    // API 호출 로직 추가
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>상품등록</h1>
      <hr />
      <form onSubmit={handleSubmit} className={styles.form}>
        <div className={styles.formGroup}>
          <label>상품명</label>
          <input 
            type="text" 
            placeholder="상품명을 입력하세요."
            value={formData.productName}
            onChange={(e) => setFormData({...formData, productName: e.target.value})}
          />
        </div>

        <div className={styles.formGroup}>
          <label>브랜드 코드</label>
          <div className={styles.inputWithButton}>
            <select defaultValue="">
              <option value="" disabled>등록된 브랜드 코드가 없습니다.</option>
            </select>
            <button type="button" className={styles.registerButton}>브랜드코드 등록</button>
          </div>
        </div>

        <div className={styles.formGroup}>
          <label>카테고리</label>
          <div className={styles.categoryInputs}>
            <select defaultValue="">
              <option value="" disabled>카테고리 그룹</option>
            </select>
            <select defaultValue="">
              <option value="" disabled>카테고리 코드</option>
            </select>
            <button type="button" className={styles.registerButton}>카테고리 등록</button>
          </div>
        </div>

        {/* 나머지 입력 필드들 */}
        <div className={styles.formGroup}>
          <label>색상</label>
          <select defaultValue="">
            <option value="" disabled>색상 코드</option>
          </select>
          <button type="button" className={styles.registerButton}>색상코드 등록</button>
        </div>

        <div className={styles.formGroup}>
          <label>사이즈</label>
          <select defaultValue="">
            <option value="" disabled>사이즈</option>
          </select>
        </div>

        <div className={styles.formGroup}>
          <label>입고 가격</label>
          <input 
            type="number" 
            placeholder="숫자만 입력"
            value={formData.purchasePrice}
            onChange={(e) => setFormData({...formData, purchasePrice: e.target.value})}
          />
        </div>

        <div className={styles.formGroup}>
          <label>출고 가격</label>
          <input 
            type="number" 
            placeholder="숫자만 입력"
            value={formData.sellingPrice}
            onChange={(e) => setFormData({...formData, sellingPrice: e.target.value})}
          />
        </div>

        <div className={styles.formGroup}>
          <label>입고 수량</label>
          <input 
            type="number" 
            placeholder="숫자만 입력"
            value={formData.stockQuantity}
            onChange={(e) => setFormData({...formData, stockQuantity: e.target.value})}
          />
        </div>

        <div className={styles.formGroup}>
          <label>상세 설명</label>
          <textarea 
            value={formData.description}
            onChange={(e) => setFormData({...formData, description: e.target.value})}
          />
        </div>

        <button type="submit" className={styles.submitButton}>등록</button>
      </form>
    </div>
  );
};

export default ProductRegister;
