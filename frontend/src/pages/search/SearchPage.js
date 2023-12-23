import React, { useState, useEffect } from 'react';
import apiService from '../../services/apiService';
import RectangleCard from "../../components/card/RectangleCard";
import TextFormatter from "../../utils/TextFormatter";

const SearchPage = () => {
    const [trips, setTrips] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
       console.log("fetching data")
        const fetchData = async () => {
            try {
                // Fetch trips using the apiService
                const tripsData = await apiService.getTrips({ size: 10, page: 1 });
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
        <div>
            <h2>SearchPage</h2>
            {loading && <p>Loading...</p>}
            {!loading && trips.length === 0 && <p>No trips found.</p>}
            {!loading && trips.length > 0 && (
                <ul>
                    {trips.map((trip) => (
                        <RectangleCard
                            title={trip.departure + " - " + trip.destination}
                            price={TextFormatter.formatPrice(trip.price, "USD")}
                            dateTime={TextFormatter.formatDate(trip.date)}
                        />
                    ))}
                </ul>
            )}
        </div>
    );
};

export default SearchPage;