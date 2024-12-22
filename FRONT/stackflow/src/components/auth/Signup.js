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
              <span 
                className={styles.showPassword} 
                onClick={togglePasswordVisibility}
              >
                {showPassword ? '🔒' : '👁️'}
              </span>
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
              <span 
                className={styles.showPassword} 
                onClick={toggleConfirmPasswordVisibility}
              >
                {showConfirmPassword ? '🔒' : '👁️'}
              </span>
            </div>
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="StoreCode" className={styles.label}>Store Code</label>
            <input
              type="text"
              id="StoreCode"
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