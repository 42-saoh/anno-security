import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { App } from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Sign } from './Sign';
import { Login } from './Login';
import { CallBack } from './CallBack';
import { Main } from './Main';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />}/>
        <Route path="/sign" element={<Sign />}/>
        <Route path="/login" element={<Login />}/>
        <Route path="/callback" element={<CallBack />}/>
        <Route path="/main" element={<Main />}/>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);