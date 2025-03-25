import React from 'react';
import axios from 'axios';

import ReactDOM from 'react-dom/client'
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals'
import { CookiesProvider} from "react-cookie"
import store from './store/index'
import { Provider } from "react-redux"
import { ConfigProvider } from "./store/index";

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;

const root = ReactDOM.createRoot(document.getElementById('root'));


root.render(
  <React.StrictMode>
    <Provider store={store}>
      <CookiesProvider>
        <ConfigProvider>
          <App /> 
        </ConfigProvider>
      </CookiesProvider>
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
