import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Login.module.css';

function Login() {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword((prevState) => !prevState);
  };

  const handleSignUp = (e) => {
    e.preventDefault();
    navigate('/signup');
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
        <form className={styles.loginForm}>
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
            <label className={styles.label}>Password</label>
            <div className={styles.passwordContainer}>
              <input
                type={showPassword ? "text" : "password"}
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
          <button type="submit" className={styles.loginButton}>Log in</button>
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
