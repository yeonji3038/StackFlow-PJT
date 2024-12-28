import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Signup.module.css';
import axios from 'axios';

function Signup() {
    const BASE_URL = "http://localhost:8080"
  // define ======================================================
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const nav = useNavigate();

 // 로그인 버튼
 const signupSubmit = async (e) => {
  e.preventDefault()
  console.log('gg')
  try {
  const response = await axios({
      method : 'POST',
      url : `${BASE_URL}/api/user/signup`,
      data : {
        username: "test1",
        password: "ssafy1234",
        email: "test1@naver.com",
        role: "ROLE_USER",
        storeCode: "11122"
      }
    })
    console.log(response)
  }  catch (err) {
    console.log(err)
  }
}

  const togglePasswordVisibility = () => {
    setShowPassword((prevState) => !prevState);
  };

  const toggleConfirmPasswordVisibility = () => {
    setShowConfirmPassword((prevState) => !prevState);
  };

  const goBack = () => {
    return nav("../")
  }

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
          <button 
            type="submit" 
            className={styles.signupButton}
            onClick={signupSubmit}>
              Sign Up</button>
          <button 
            className={styles.backButton}
            onClick={goBack}>뒤로 가기</button>
        </form>
      </div>
    </div>
  );
}

export default Signup;