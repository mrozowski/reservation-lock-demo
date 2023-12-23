import { getTrips } from '../api/tripApi';

const apiService = {
    getTrips: async ({ size, page, date, destination, departure }) => {
        try {
            const trips = await getTrips({ size, page, date, destination, departure });
            // can do mapping into internal object
            return trips;
        } catch (error) {
            // Handle error, e.g., log it or throw a custom error
            console.error('API Service Error:', error);
            throw error;
        }
    },
};

export default apiService;