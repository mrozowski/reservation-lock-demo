import React, { useState } from 'react';

const SearchBar = ({ onSearch }) => {
    const [searchQuery, setSearchQuery] = useState({
        date: '',
        departure: '',
        destination: '',
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setSearchQuery((prevQuery) => ({
            ...prevQuery,
            [name]: value,
        }));
    };

    const handleSearchSubmit = () => {
        // Pass the search query to the parent component or perform the search here
        onSearch(searchQuery);
    };

    return (
        <div>
            <h1>SearchPage</h1>
            <div>
                <label>Date:</label>
                <input
                    type="text"
                    name="date"
                    value={searchQuery.date}
                    onChange={handleInputChange}
                    placeholder="YYYY-MM-DD"
                />
            </div>
            <div>
                <label>Departure:</label>
                <input
                    type="text"
                    name="departure"
                    value={searchQuery.departure}
                    onChange={handleInputChange}
                    placeholder="Departure City"
                />
            </div>
            <div>
                <label>Destination:</label>
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
    );
};

export default SearchBar;