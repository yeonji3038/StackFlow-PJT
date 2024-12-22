import React from 'react';
import styles from './ProductModal.module.css';

const ProductModal = ({ isOpen, onClose, productData, onSubmit }) => {
  if (!isOpen) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>상품명</h2>
        <div className={styles.formContent}>
          <div className={styles.inputGroup}>
            <label>상품코드</label>
            <input type="text" value={productData.productCode} readOnly />
          </div>
          <div className={styles.inputGroup}>
            <label>브랜드코드</label>
            <input type="text" value={productData.brandCode} readOnly />
          </div>
          <div className={styles.inputGroup}>
            <label>카테고리</label>
            <input type="text" value={productData.category} readOnly />
          </div>
          <div className={styles.inputGroup}>
            <label>사이즈</label>
            <input type="text" value={productData.size} readOnly />
          </div>
          <div className={styles.inputGroup}>
            <label>색상</label>
            <input type="text" value={productData.color} />
          </div>
          <div className={styles.inputGroup}>
            <label>입고 금액</label>
            <input type="number" value={productData.inPrice} />
          </div>
          <div className={styles.inputGroup}>
            <label>판매 금액</label>
            <input type="number" value={productData.outPrice} />
          </div>
          <div className={styles.inputGroup}>
            <label>상품 설명</label>
            <textarea value={productData.description} />
          </div>
        </div>
        <div className={styles.buttonGroup}>
          <button className={styles.submitButton} onClick={onSubmit}>수정</button>
          <button className={styles.closeButton} onClick={onClose}>취소</button>
        </div>
      </div>
    </div>
  );
};

export default ProductModal;
