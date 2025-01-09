import { configureStore } from '@reduxjs/toolkit'
import { createContext, useContext } from "react";
// import { combineReducers } from 'redux';
import authReducer from './authSlice';



// 정적 데이터 관리 
const ConfigContext = createContext();

export const ConfigProvider = ({ children }) => {
  const config = {
    BASE_URL: "http://localhost:8080",
  };

  return (
    <ConfigContext.Provider value={config}>
      {children}
    </ConfigContext.Provider>
  );
};

export const useConfig = () => useContext(ConfigContext);

// 동적 데이터 관리 
const store = configureStore({
  reducer : {
    auth: authReducer,
   
  }
})

export default store


// const useState = {
//   csrfToken : csrfToken,
//   BASE_URL : BASE_URL
// }

// function reducer(state = useState, action){
//   return state
// }

