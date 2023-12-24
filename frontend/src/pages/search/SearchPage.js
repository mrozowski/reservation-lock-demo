import React, {useEffect, useState} from 'react';
import apiService from '../../services/apiService';
import RectangleCard from "../../components/card/RectangleCard";
import TextFormatter from "../../utils/TextFormatter";
import './SearchPage.css'

const SearchPage = () => {
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

    const handleTripCardClick = (tripId) => {
        console.log('Trip card clicked:', tripId);
    };

    const handleSearchSubmit = () => {
        // Perform the search based on the searchQuery
        console.log('Search Query:', searchQuery);
        fetchData2()
    };

    const fetchData2 = async () => {
        try {
            // Fetch trips using the apiService
            const tripsData = await apiService.getTrips({
                size: 10,
                page: searchQuery.page,
                departure: searchQuery.departure,
                destination: searchQuery.destination,
                date: searchQuery.date
            });
            setTrips(tripsData.content);
            setLoading(false);
        } catch (error) {
            // Handle error
            setLoading(false);
        }
    };

    useEffect(() => {
        console.log("fetching data")
        const fetchData = async () => {
            try {
                // Fetch trips using the apiService
                const tripsData = await apiService.getTrips({size: 10, page: 1});
                setTrips(tripsData.content);
                setLoading(false);
            } catch (error) {
                // Handle error
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="container">
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
                            price={TextFormatter.formatPrice(trip.price, "USD")}
                            dateTime={TextFormatter.formatDate(trip.date)}
                            clickEvent={(()=> handleTripCardClick(trip.id))}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};

export default SearchPage;