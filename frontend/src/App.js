import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SearchPage from './pages/search/SearchPage';
import './App.css'
import TopBar from "./components/menu/TopBar";

function App() {
  return (
      <Router>
        <div>
            <TopBar/>
          <Routes>
            <Route path="/" element={<SearchPage/>} />

          </Routes>
        </div>
      </Router>
  );
}

export default App;
