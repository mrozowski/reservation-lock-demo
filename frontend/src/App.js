import './App.css'
import React from 'react';
import {Route, Routes} from 'react-router-dom';
import SearchPage from './pages/search/SearchPage';
import TopBar from "./components/menu/TopBar";
import ReservationDetailsForm from "./pages/reservation-details/ReservationDetailsForm";
import ReservationDetailsResult from "./pages/reservation-details/ReservationDetailsResult";
import TripReservationPage from "./pages/trip/TripReservationPage";
import SeatSelectionPage from "./pages/trip/SeatSelectionPage";

function App() {
    return (
        <div>
            <TopBar/>
            <Routes>
                <Route path="/" element={<SearchPage />}/>
                <Route path="/trips/:tripId" element={<TripReservationPage />}/>
                <Route path="/trips/:tripId/seat-selection" element={<SeatSelectionPage />}/>
                <Route path="/reservation" element={<ReservationDetailsForm />}/>
                <Route path="/reservations/:reference/:clientName" element={<ReservationDetailsResult />}/>
            </Routes>
        </div>
    );
}

export default App;
