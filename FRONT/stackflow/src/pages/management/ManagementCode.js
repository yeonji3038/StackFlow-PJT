import React, { useState } from "react";
import DaumPostcode from "react-daum-postcode"; // DaumPostcode import
import styles from "./ManagementCode.module.css";
import axios from "axios";

const ManagementCode = () => {
  const [modalState, setModalState] = useState(false); // 주소 검색 모달 상태
  const [inputAddressValue, setInputAddressValue] = useState(""); // 선택된 주소 값
  const [inputZipCodeValue, setInputZipCodeValue] = useState(""); // 선택된 우편번호 값
  const [showCodeModal, setShowCodeModal] = useState(false); // 코드 생성 모달 상태
  const [generatedCode, setGeneratedCode] = useState(""); // 생성된 코드
  const [email, setEmail] = useState(""); // 이메일 입력 값
  const [storeName, setStoreName] = useState(""); // 지점명 입력 값

  const handleAddressSearch = () => {
    setModalState(true); // 주소 검색 모달 열기
  };

  const handleCompletePost = (data) => {
    setModalState(false); // 주소 선택 후 모달 닫기
    setInputAddressValue(data.address); // 선택된 주소 값 업데이트
    setInputZipCodeValue(data.zonecode); // 선택된 우편번호 값 업데이트
  };

  const generateRandomCode = () => {
    return "STF" + Math.random().toString(36).substr(2, 8).toUpperCase();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const code = generateRandomCode();
    setGeneratedCode(code);

    console.log("폼 제출, 생성된 코드:", code);

    try {
      // DB에 저장하는 API 호출
      await axios.post("/api/store/code", {
        code,
        storeName,
        address: inputAddressValue,
        addressDetail: document.querySelector(".addressDetail").value,
      });

      setShowCodeModal(true); // 코드 생성 후 모달 표시
    } catch (error) {
      console.error("코드 생성 실패:", error);
    }
  };

  const handleSendEmail = async () => {
    try {
      await axios.post("/api/store/send-code", {
        email,
        code: generatedCode,
        storeName,
      });
      alert("코드가 이메일로 전송되었습니다!");
      setShowCodeModal(false);
      setEmail("");
    } catch (error) {
      console.error("이메일 전송 실패:", error);
    }
  };

  return (
    <div className={styles.managementPage}>
      <h1 className={styles.title}>매장 코드 생성</h1>
      <hr />
      <div className={styles.contentWrapper}>
        <div className={styles.leftColumn}>
          <form className={styles.registrationForm} onSubmit={handleSubmit}>
            <div className={styles.formGroup}>
              <label>지점명</label>
              <input
                type="text"
                placeholder="지점명을 입력하세요"
                value={storeName}
                onChange={(e) => setStoreName(e.target.value)}
                required
              />
            </div>

            <div className={styles.formGroup}>
              <label>매장 주소</label>
              <div className={styles.addressWrapper}>
                <input
                  type="text"
                  placeholder="주소를 입력하세요"
                  value={inputAddressValue}
                  readOnly
                  required
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
                className={`${styles.addressDetail} addressDetail`}
                placeholder="상세 주소를 입력하세요"
                required
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
            <button
              className={styles.closeButton}
              onClick={() => setModalState(false)}
            >
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

      {/* 코드 전송 모달 */}
      {showCodeModal && (
        <div className={styles.modal}>
          <div className={styles.modalContent}>
            <button
              className={styles.closeButton}
              onClick={() => setShowCodeModal(false)}
            >
              ❌
            </button>
            <h2>매장 코드가 생성되었습니다</h2>
            <p>생성된 코드: {generatedCode}</p>
            <div className={styles.emailForm}>
              <input
                type="email"
                placeholder="이메일 주소를 입력하세요"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
              <button onClick={handleSendEmail}>코드 전송</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ManagementCode;
