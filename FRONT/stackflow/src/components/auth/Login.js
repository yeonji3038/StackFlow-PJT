import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Login.module.css';
import axios from 'axios';

function Login() {
  const BASE_URL = "http://localhost:8080"
  // define =========================================
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false)
  const [userInput, setUserInput] = useState(
    { username : "",
      password : ""
    }
  )  
  const [csrfToken, setCsrfToken] = useState()
  
  // func ==========================================
  const togglePasswordVisibility = () => {
    setShowPassword((prevState) => !prevState);
  };

  const handleSignUp = (e) => {
    e.preventDefault();
    navigate('/signup');
  };
  
  // 로그인 버튼
  const loginSubmit = async (e) => {
    e.preventDefault()
    console.log('gg')
    try {
    const response = await axios({
        method : 'POST',
        url : `${BASE_URL}/api/user/login`,
        data : userInput
      })
      console.log(response)
    }  catch (err) {
      console.log(err)
    }
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
        <form className={styles.loginForm}>
          <div className={styles.inputGroup}>
            <label htmlFor="username" className={styles.label}>ID</label>
            <input
              type="text"
              username="username"
              placeholder="아이디"
              value={userInput.username}
              className={styles.inputField}
              onChange={(e) => 
                setUserInput({...userInput, username: e.target.value})
              }
            />
          </div>
          <div className={styles.inputGroup}>
            <label className={styles.label}>Password</label>
            <div className={styles.passwordContainer}>
              <input
                type={showPassword ? "text" : "password"}
                placeholder="비밀번호"
                className={styles.inputField}
                value={userInput.password}
                onChange={(e) =>
                  setUserInput({...userInput, password: e.target.value})
                }
              />
              <span 
                className={styles.showPassword} 
                onClick={togglePasswordVisibility}
              >
                {showPassword ? '🔒' : '👁️'}
              </span>
            </div>
          </div>
          <button 
            type="submit" 
            className={styles.loginButton}
            onClick={loginSubmit}>
              Log in
            </button>
          <div className={styles.signupContainer}>
            <span className={styles.signupText}>아직 회원이 아니신가요?</span>
            <button 
              type="button"
              className={styles.signUpButton} 
              onClick={handleSignUp}
            >
              Sign Up
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Login;
