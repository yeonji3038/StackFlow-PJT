import React, { useState } from 'react';
import styles from './ProductRegister.module.css';

const Modal = ({ isOpen, onClose, onRegister, type }) => {
  const [code, setCode] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onRegister(code);
    setCode('');
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>{type} 등록</h2>
        <form onSubmit={handleSubmit}>
          <input 
            type="text" 
            placeholder={`새로운 ${type} 코드를 입력하세요.`}
            value={code}
            onChange={(e) => setCode(e.target.value)}
          />
          <button type="submit">등록</button>
          <button type="button" onClick={onClose}>닫기</button>
        </form>
      </div>
    </div>
  );
};

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

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalType, setModalType] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // API 호출 로직 추가
  };

  const openModal = (type) => {
    setModalType(type);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleRegister = (code) => {
    if (modalType === '브랜드 코드') {
      setFormData({ ...formData, brandCode: code });
    } else if (modalType === '카테고리') {
      setFormData({ ...formData, categoryCode: code });
    } else if (modalType === '색상 코드') {
      setFormData({ ...formData, color: code });
    }
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
            <select value={formData.brandCode} onChange={(e) => setFormData({...formData, brandCode: e.target.value})}>
              <option value="" disabled>등록된 브랜드 코드가 없습니다.</option>
            </select>
            <button type="button" className={styles.registerButton} onClick={() => openModal('브랜드 코드')}>브랜드코드 등록</button>
          </div>
        </div>

        <div className={styles.formGroup}>
          <label>카테고리</label>
          <div className={styles.categoryInputs}>
            <select value={formData.categoryGroup} onChange={(e) => setFormData({...formData, categoryGroup: e.target.value})}>
              <option value="" disabled>카테고리 그룹</option>
            </select>
            <select value={formData.categoryCode} onChange={(e) => setFormData({...formData, categoryCode: e.target.value})}>
              <option value="" disabled>카테고리 코드</option>
            </select>
            <button type="button" className={styles.registerButton} onClick={() => openModal('카테고리')}>카테고리 등록</button>
          </div>
        </div>

        <div className={styles.formGroup}>
          <label>색상</label>
          <select value={formData.color} onChange={(e) => setFormData({...formData, color: e.target.value})}>
            <option value="" disabled>색상 코드</option>
          </select>
          <button type="button" className={styles.registerButton} onClick={() => openModal('색상 코드')}>색상코드 등록</button>
        </div>

        {/* 나머지 입력 필드들 */}
        <div className={styles.formGroup}>
          <label>사이즈</label>
          <select value={formData.size} onChange={(e) => setFormData({...formData, size: e.target.value})}>
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

      <Modal 
        isOpen={isModalOpen} 
        onClose={closeModal} 
        onRegister={handleRegister} 
        type={modalType} 
      />
    </div>
  );
};

export default ProductRegister;