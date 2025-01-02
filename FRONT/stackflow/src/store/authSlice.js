import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isLoggedIn: false, // 로그인 상태
  user: null, // 사용자 정보
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login(state, action) {
      state.isLoggedIn = true; // 로그인 상태 true
      state.user = action.payload; // 사용자 정보 저장
      console.log(state.user)
    },
    logout(state) {
      state.isLoggedIn = false; // 로그인 상태 false
      state.user = null; // 사용자 정보 초기화
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;