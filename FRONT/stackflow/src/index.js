import React from 'react';
import ReactDOM from 'react-dom/client'
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals'
import { CookiesProvider} from "react-cookie"
import { Provider } from "react-redux"
import { createStore } from "redux"

const csrfToken = "94C051EA049127D48B14FFB37DEAAC43"
const BASE_URL = "http://localhost:8080"
const useState = {
  csrfToken : csrfToken,
  BASE_URL : BASE_URL
}

function reducer(state = useState, action){
  return state
}

let store = createStore(reducer)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <CookiesProvider>
        <App /> 
      </CookiesProvider>
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
