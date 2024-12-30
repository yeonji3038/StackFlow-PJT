import React, { useState } from "react";
import DaumPostcode from "react-daum-postcode"; // DaumPostcode import
import styles from "./ManagementCode.module.css";

const ManagementCode = () => {
  const [modalState, setModalState] = useState(false); // 주소 검색 모달 상태
  const [inputAddressValue, setInputAddressValue] = useState(""); // 선택된 주소 값
  const [inputZipCodeValue, setInputZipCodeValue] = useState(""); // 선택된 우편번호 값

  const handleAddressSearch = () => {
    setModalState(true); // 주소 검색 모달을 열기
  };

  const handleCompletePost = (data) => {
    setModalState(false); // 주소 선택 후 모달 닫기
    setInputAddressValue(data.address); // 선택된 주소
    setInputZipCodeValue(data.zonecode); // 선택된 우편번호
  };

  return (
    <div className={styles.managementPage}>
      <h1 className={styles.title}>매장 코드 생성</h1>
      <hr />
      <div className={styles.contentWrapper}>
        <div className={styles.leftColumn}>
          <form className={styles.registrationForm}>
            <div className={styles.formGroup}>
              <label>지점명</label>
              <input type="text" placeholder="지점명을 입력하세요" />
            </div>
            <div className={styles.formGroup}>
              <label>이메일</label>
              <input type="email" placeholder="이메일을 입력하세요" />
            </div>
            <div className={styles.formGroup}>
              <label>전화번호</label>
              <input type="tel" placeholder="전화번호를 입력하세요" />
            </div>
            <div className={styles.formGroup}>
              <label>매장 주소</label>
              <div className={styles.addressWrapper}>
                <input
                  type="text"
                  placeholder="주소를 입력하세요"
                  value={inputAddressValue}
                  readOnly
                />
                <button
                  type="button"
                  className={styles.addressButton}
                  onClick={handleAddressSearch}
                >
                  주소 찾기
                </button>
              </div>
              <input
                type="text"
                className={styles.addressDetail}
                placeholder="상세 주소를 입력하세요"
              />
            </div>
            <button type="submit" className={styles.submitButton}>
              매장 코드 생성
            </button>
          </form>
        </div>
        <div className={styles.rightColumn}>
          <div className={styles.mapContainer}>
            <p>지도가 표시될 영역</p>
          </div>
        </div>
      </div>

      {/* 주소 찾기 모달 */}
      {modalState && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <button className={styles.closeButton} onClick={() => setModalState(false)}>
             ❌
            </button>
            <div className={styles.row}>
              <div className={styles.column}>
                <div className={styles.apiContent}>
                  <DaumPostcode onComplete={handleCompletePost} />
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ManagementCode;


// handleCompletePost
// handleCompletePost 함수는 DaumPostcode에서 주소가 선택되었을 때 호출
// 이 함수에서 data 객체를 받아 선택된 주소와 우편번호를 상태로 업데이트
// 이 값을 사용하여 주소 입력란에 자동으로 값이 반영되도록 할 수 있음
