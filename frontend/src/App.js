import React, {useState} from 'react';
import {Route, Routes, useNavigate} from 'react-router-dom';
import SearchPage from './pages/search/SearchPage';
import './App.css'
import TopBar from "./components/menu/TopBar";
import ReservationDetailsForm from "./pages/reservation-details/ReservationDetailsForm";
import ReservationDetailsResult from "./pages/reservation-details/ReservationDetailsResult";
import TripReservationPage from "./pages/trip/TripReservationPage";

function App() {
    const navigate = useNavigate();
    const [details, setDetails] = useState(null);
    const [trip, setTrip] = useState(null);

    const handleFormSubmit = (details) => {
        setDetails(details);
        navigate('/reservation/details');
    };

    const handleTripDetailRequest = (trip) => {
        setTrip(trip);
        navigate('/trips/details'); // maybe we should add to url id of the trip
    };

    return (
        <div>
            <TopBar/>
            <Routes>
                <Route path="/" element={<SearchPage onTripClick={handleTripDetailRequest}/>}/>
                <Route path="/" element={<TripReservationPage trip={trip}/>}/>
                <Route path="/reservation" element={<ReservationDetailsForm onSubmit={handleFormSubmit}/>}/>
                <Route path="/reservation/details" element={<ReservationDetailsResult details={details}/>}/>
            </Routes>
        </div>
    );
}

export default App;
