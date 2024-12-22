import React from 'react';
import styles from './ProductCategoryRegister.module.css';

const ProductCategoryRegister = () => {
    return (
        <div className={styles.container}>
            <h1 className={styles.pageTitle}>카테고리 등록</h1>
            <hr />
            <div className={styles.mainContainer}>
                <div className={styles.formContainer}>
                    <h2>카테고리 등록</h2>
                    <div className={styles.inputGroup}>
                        <label>카테고리 그룹</label>
                        <input type="text" />
                    </div>
                    <div className={styles.inputGroup}>
                        <label>카테고리 코드</label>
                        <input type="text" />
                    </div>
                    <div className={styles.inputGroup}>
                        <label>카테고리 이름</label>
                        <input type="text" />
                    </div>
                    <div className={styles.inputGroup}>
                        <label>카테고리 설명</label>
                        <textarea />
                    </div>
                    <button>등록</button>
                </div>
                <div className={styles.formContainer}>
                    <h2>브랜드 등록</h2>
                    <div className={styles.inputGroup}>
                        <label>브랜드 코드</label>
                        <input type="text" />
                    </div>
                    <div className={styles.inputGroup}>
                        <label>브랜드 설명</label>
                        <textarea />
                    </div>
                    <button>등록</button>
                </div>
                <div className={styles.formContainer}>
                    <h2>색상 코드 등록</h2>
                    <div className={styles.inputGroup}>
                        <label>색상 코드</label>
                        <input type="text" />
                    </div>
                    <div className={styles.inputGroup}>
                        <label>색상 이름</label>
                        <input type="text" />
                    </div>
                    <button>등록</button>
                </div>
            </div>
        </div>
    );
};

export default ProductCategoryRegister;
