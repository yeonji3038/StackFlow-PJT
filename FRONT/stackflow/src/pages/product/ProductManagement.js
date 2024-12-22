import React, { useState } from 'react';
import styles from './ProductManagement.module.css';
import ProductModal from './ProductModal';

const ProductManagement = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  // 상품 클릭 핸들러
  const handleProductClick = (product) => {
    setSelectedProduct(product);
    setIsModalOpen(true);
  };

  // 모달 닫기 핸들러
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedProduct(null);
  };

  // 수정 제출 핸들러
  const handleSubmit = (updatedData) => {
    // API 호출 등 수정 로직 구현
    console.log('Updated data:', updatedData);
    handleCloseModal();
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.header}>상품조회</h1>
      <hr />
      <section className={styles.searchSection}>
        <form className={styles.searchForm}>
          <div className={styles.inputRow}>
            <select>
              <option>카테고리 그룹</option>
            </select>
            <select>
              <option>카테고리 코드</option>
            </select>
          </div>
          
          <div className={styles.inputRow}>
            <select>
              <option>색상 코드</option>
            </select>
            <select>
              <option>사이즈</option>
            </select>
          </div>
          
          <div className={styles.lastRow}>
            <input type="text" placeholder="상품 코드를 입력하세요." />
            <button className={styles.searchButton}>검색</button>
          </div>
        </form>
      </section>

      <table className={styles.table}>
        <thead className={styles.tableHeader}>
          <tr>
            <th>상품코드</th>
            <th>브랜드코드</th>
            <th>카테고리</th>
            <th>사이즈</th>
            <th>색상코드</th>
            <th>총수</th>
          </tr>
        </thead>
        <tbody className={styles.tableBody}>
          <tr onClick={() => handleProductClick({
            productCode: 'StackFlowTOPTSFREEcfb50a28-d0b7-458e-b024-f6469fd0179f',
            brandCode: 'StackFlow',
            category: 'TOP(TS)',
            size: 'FREE',
            color: 'BK',
            inPrice: '19000',
            outPrice: '30000',
            description: 'Page Not Found. 404 ERROR'
          })}>
            <td>StackFlowTOPTSFREEcfb50a28-d0b7-458e-b024-f6469fd0179f</td>
            <td>StackFlow</td>
            <td>TOP(TS)</td>
            <td>FREE</td>
            <td>BK</td>
            <td>206</td>
          </tr>
          <tr>
            <td>NIKYBOTTOMDNlXL2509e99c-4918-425b-827b-de126645aa27</td>
            <td>NIKY</td>
            <td>BOTTOM(DN)</td>
            <td>XL</td>
            <td>WH</td>
            <td>300</td>
          </tr>
        </tbody>
      </table>

      <ProductModal 
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        productData={selectedProduct}
        onSubmit={handleSubmit}
      />
    </div>
  );
};

export default ProductManagement;
