import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { login } from '../../store/authSlice';
import useAuth from '../../store/tokenManage'
import styles from './Login.module.css';
import axios from 'axios';
import { useConfig } from '../../store';

import Swal from 'sweetalert2';





function Login() {

  // define ===================================================
  const navigate = useNavigate();  // 다른 페이지로 이동하는 함수 
  // login 입력 정보 담는 state
  const [loginInput, setLoginInput] = useState(
    { username : "",
      password : ""
    }
  )  
  const [showPassword, setShowPassword] = useState(false)  // 비밀번호 보기
  const dispatch = useDispatch();
  const { saveToken } = useAuth()
  const { BASE_URL } = useConfig()
  // function ================================================

    // 로그인 버튼
    const loginSubmit = async (e) => {
      e.preventDefault()

      if (!loginInput.username.trim() || !loginInput.password.trim()) {
        return Swal.fire({
          icon: 'error',
          title: '입력 오류',
          text: '아이디와 비밀번호를 모두 입력해주세요.',
        });
      }
      try {
        // 서버 요청
        const response = await axios({
          url: `${BASE_URL}/api/user/login`,
          method: 'POST',
          data: loginInput,
        });
        // 서버 응답 처리
        if (response.status === 200) {
          console.log(response)
          // 리디렉션 처리 (예: 메인 페이지로 이동)
          const csrfToken = 'B44D4F899BE99B43F4248AD3D546FBC9'
          saveToken(csrfToken)
          dispatch(login(response.data));
          navigate('/main')
        } 
      } catch (err) {
        // 에러 응답 처리
        if (err.response && err.response.status === 401) {
          // 로그인 실패 (401 Unauthorized)
          Swal.fire({
            icon: 'error',
            title: '로그인 실패',
            text: '아이디 또는 비밀번호를 확인해주세요.',
          });
        } else {
          // 네트워크 또는 기타 서버 오류
          Swal.fire({
            icon: 'error',
            title: '오류 발생',
            text: '서버와 통신할 수 없습니다. 잠시 후 다시 시도해주세요.',
          })
        }
      }
    }

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
              username="username"
              placeholder="아이디"
              value={loginInput.username}
              className={styles.inputField}
              onChange={(e) => 
                setLoginInput({...loginInput, username: e.target.value})
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
                value={loginInput.password}
                onChange={(e) =>
                  setLoginInput({...loginInput, password: e.target.value})
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
