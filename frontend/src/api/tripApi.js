import axios from 'axios';
import { API_BASE_URL } from './apiConfig';

const TRIP_API_ENDPOINT = 'v1/trips/search';

const getTrips = async ({ size, page, date, destination, departure }) => {
    try {
        const response = await axios.get(`${API_BASE_URL}${TRIP_API_ENDPOINT}`, {
            params: { size, page, date, destination, departure },
        });

        return response.data;
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

export { getTrips };