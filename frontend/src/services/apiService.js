import { getTrips, getReservationDetails, cancelReservation } from '../api/tripApi';
import ModelMapper from "./ModelMapper";

const ApiService = {
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
            return ModelMapper.mapReservationDetails(details);
        } catch (error) {
            console.error('API Service Error:', error);
            throw error;
        }
    },

    cancelReservation: async ({reference, clientName}) =>{
        try{
            const data = await cancelReservation({ reference, clientName });
            return ModelMapper.mapCancellationDetails(data);
        } catch (error) {
            console.error('API Service Error:', error);
            throw error;
        }
    }
};

export default ApiService;