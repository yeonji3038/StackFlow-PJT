import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Signup.module.css';

function Signup() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const navigate = useNavigate();

  const togglePasswordVisibility = () => {
    setShowPassword((prevState) => !prevState);
  };

  const toggleConfirmPasswordVisibility = () => {
    setShowConfirmPassword((prevState) => !prevState);
  };

  return (
    <div className={styles.container}>
      <div className={styles.textContainer}>
        <h1>
          Stack<br/>
          &nbsp;&nbsp;&nbsp;&nbsp;Smarter,<br/>
          Flow<br/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Better.
        </h1>
      </div>
      <div className={styles.formContainer}>
        <form className={styles.signupForm}>
          <div className={styles.inputGroup}>
            <label htmlFor="username" className={styles.label}>ID</label>
            <input
              type="text"
              id="username"
              placeholder="아이디"
              className={styles.inputField}
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="password" className={styles.label}>Password</label>
            <div className={styles.passwordContainer}>
              <input
                type={showPassword ? 'text' : 'password'}
                id="password"
                placeholder="비밀번호"
                className={styles.inputField}
              />
              <button
                type="button"
                onClick={togglePasswordVisibility}
                className={styles.showPassword}
                aria-label="Toggle password visibility"
              >
                {showPassword ? '🔒' : '👁️'}
              </button>
            </div>
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="confirmPassword" className={styles.label}>Password Confirmation</label>
            <div className={styles.passwordContainer}>
              <input
                type={showConfirmPassword ? 'text' : 'password'}
                id="confirmPassword"
                placeholder="비밀번호 확인"
                className={styles.inputField}
              />
              <button
                type="button"
                onClick={toggleConfirmPasswordVisibility}
                className={styles.showPassword}
                aria-label="Toggle password visibility"
              >
                {showConfirmPassword ? '🔒' : '👁️'}
              </button>
            </div>
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="email" className={styles.label}>Email</label>
            <input
              type="email"
              id="email"
              placeholder="example@site.com"
              className={styles.inputField}
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="companyName" className={styles.label}>회사명</label>
            <input
              type="text"
              id="companyName"
              className={styles.inputField}
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="businessNumber" className={styles.label}>사업자등록번호</label>
            <input
              type="text"
              id="businessNumber"
              className={styles.inputField}
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="operatingStores" className={styles.label}>운영 매장 개수</label>
            <input
              type="text"
              id="operatingStores"
              placeholder="30개 초과 시 관리자 문의"
              className={styles.inputField}
            />
          </div>
          <button type="submit" className={styles.signupButton}>Sign Up</button>
        </form>
      </div>
    </div>
  );
}

export default Signup;