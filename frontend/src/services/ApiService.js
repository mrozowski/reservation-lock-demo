import {
    getTrips,
    getReservationDetails,
    cancelReservation,
    getSeats,
    processReservation,
    createPaymentIntent, lockSeat, makeStripePayment
} from '../api/TripApi';
import ModelMapper from "./ModelMapper";

const ApiService = {
    getTrips: async ({ size, page, date, destination, departure }) => {
        try {
            const trips = await getTrips({ size, page, date, destination, departure });
            // can do mapping into internal object
            return ModelMapper.mapPageOfTrips(trips);
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
    },

    getSeats: async ({tripId}) =>{
        try{
            const data = await getSeats({tripId});
            console.log(data);
            return data;
        } catch (error) {
            console.error('API Service Error:', error);
            throw error;
        }
    },

    lockSeat: async (tripId, seatNumber) =>{
        try{
            const data = await lockSeat(tripId, seatNumber);
            return ModelMapper.mapSessionAuth(data);
        } catch (error) {
            console.error('API Service Error:', error);
            throw error;
        }
    },

    processReservation: async (reservation, authorization) =>{
        try{
            const headers = {Authorization: authorization};
            const data = await processReservation(reservation, headers);
            console.log("processReservation response: ", data);
            return ModelMapper.mapReservationConfirmation(data);
        } catch (error) {
            console.error('API Service Error:', error);
            throw error;
        }
    },

    createPaymentIntent: async (paymentIntentRequest, authorization) =>{
        try{
            const headers = {Authorization: authorization};
            const data = await createPaymentIntent(paymentIntentRequest, headers);
            console.log("Payment intent response: ", data);
            return ModelMapper.mapPaymentIntentResponse(data);
        } catch (error) {
            console.error('API Service Error:', error);
            throw error;
        }
    },

    makeStripePayment: async (paymentDetails) =>{
        try{
            const request = ModelMapper.mapPaymentRequest(paymentDetails)
            const data = await makeStripePayment(request);
            console.log("Finished payment: ", data);
        } catch (error) {
            console.error('API Service Error:', error);
            throw error;
        }
    }
};

export default ApiService;