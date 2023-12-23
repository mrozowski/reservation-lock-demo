import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SearchPage from './pages/search/SearchPage';

function App() {
  return (
      <Router>
        <div>
          <Routes>
            <Route path="/" element={<SearchPage/>} />
              {console.log("call app.js")}
            {/* Add more routes for other pages if needed */}
          </Routes>
        </div>
      </Router>
  );
}

export default App;
