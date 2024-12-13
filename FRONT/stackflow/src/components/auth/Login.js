import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Login.module.css';

function Login() {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword((prevState) => !prevState);
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
          <button type="submit" className={styles.loginButton}>Log in</button>
          <div className={styles.signupLink}>
            아직 회원이 아니신가요?{' '}
            <button 
              type="button" 
              className={styles.signUpButton}
              onClick={() => navigate('/signup')}
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
