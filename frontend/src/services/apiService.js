import { getTrips } from '../api/tripApi';
import { getReservationDetails } from '../api/tripApi';
import ModelMapper from "./ModelMapper";

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
    getReservationDetails: async ({reference, clientName}) =>{
        try{
            const details = await getReservationDetails({ reference, clientName });
            console.log("Before call: ", details);
            return ModelMapper.mapReservationDetails(details);
        } catch (error) {
            console.error('API Service Error:', error);
            throw error;
        }
    }
};

export default apiService;