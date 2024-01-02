import React, {useEffect, useState} from 'react';
import ApiService from '../../services/ApiService';
import RectangleCard from "../../components/card/RectangleCard";
import Navigator from "../../utils/Navigator";
import './SearchPage.css'

const SearchPage = () => {
    const {navigateToTripReservationPage} = Navigator();
    const [trips, setTrips] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState({
        date: '',
        departure: '',
        destination: '',
        page: 1,
    });

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setSearchQuery((prevQuery) => ({
            ...prevQuery,
            [name]: value,
        }));
    };

    const handleSearchSubmit = () => {
        fetchData();
    };

    const fetchData = async () => {
        try {
            const tripsData = await ApiService.getTrips({
                size: 10,
                page: searchQuery.page,
                departure: searchQuery.departure,
                destination: searchQuery.destination,
                date: searchQuery.date
            });
            setTrips(tripsData.content);
            setLoading(false);
        } catch (error) {
            setLoading(false);
            // Handle error
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleTripCardClick = (trip) => {
        navigateToTripReservationPage(trip);
    };

    return (
        <div className="search-page-container main-container">
            <div className="search-bar-container">
                <div className="search-option">
                    <label>Date</label>
                    <input
                        type="text"
                        name="date"
                        value={searchQuery.date}
                        onChange={handleInputChange}
                        placeholder="DD-MM-YYYY"
                    />
                </div>
                <div className="search-option">
                    <label>Departure</label>
                    <input
                        type="text"
                        name="departure"
                        value={searchQuery.departure}
                        onChange={handleInputChange}
                        placeholder="Departure City"
                    />
                </div>
                <div className="search-option">
                    <label>Destination</label>
                    <input
                        type="text"
                        name="destination"
                        value={searchQuery.destination}
                        onChange={handleInputChange}
                        placeholder="Destination City"
                    />
                </div>
                <button onClick={handleSearchSubmit}>Search</button>
            </div>
            {loading && <p>Loading...</p>}
            {!loading && trips.length === 0 && <p>No trips found.</p>}
            {!loading && trips.length > 0 && (
                <div className="card-container">
                    {trips.map((trip, index) => (
                        <RectangleCard
                            key={index}
                            title={trip.departure + " - " + trip.destination}
                            price={trip.displayPrice}
                            dateTime={trip.date + ", " + trip.time}
                            clickEvent={(() => handleTripCardClick(trip))}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};

export default SearchPage;