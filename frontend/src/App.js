import React, {useState} from 'react';
import {Route, Routes, useNavigate} from 'react-router-dom';
import SearchPage from './pages/search/SearchPage';
import './App.css'
import TopBar from "./components/menu/TopBar";
import ReservationDetailsForm from "./pages/reservation-details/ReservationDetailsForm";
import ReservationDetailsResult from "./pages/reservation-details/ReservationDetailsResult";

function App() {
    const navigate = useNavigate();
    const [details, setDetails] = useState(null);

    const handleFormSubmit = (details) => {
        setDetails(details);
        navigate('/reservation/details');
    };

    return (
        <div>
            <TopBar/>
            <Routes>
                <Route path="/" element={<SearchPage/>}/>
                <Route path="/reservation" element={<ReservationDetailsForm onSubmit={handleFormSubmit}/>}/>
                <Route path="/reservation/details" element={<ReservationDetailsResult details={details}/>}/>
            </Routes>
        </div>
    );
}

export default App;
