import axios from 'axios';
import {API_BASE_URL, PAYMENT_API_BASE_URL} from './ApiConfig';

// Backend Service
const TRIP_API_ENDPOINT = 'v1/trips/search';
const RESERVATION_DETAILS_API_ENDPOINT = 'v1/reservations/details';
const CANCEL_RESERVATION_API_ENDPOINT = 'v1/reservations/cancel';
const TRIP_SEAT_API_ENDPOINT = 'v1/trips/:tripId/seats';
const SEAT_LOCK_API_ENDPOINT = 'v1/trips/:tripId/seats/:seatNumber/lock';
const PROCESS_RESERVATION_API_ENDPOINT = 'v1/reservations/process';
const PAYMENT_INTENT_API_ENDPOINT = 'v1/payment/stripe/create-intent';

// Payment service
const PAYMENT_REQUEST_API_ENDPOINT = 'v1/payment/process';


const getTrips = async ({size, page, date, destination, departure}) => {
    try {
        const response = await axios.get(`${API_BASE_URL}${TRIP_API_ENDPOINT}`, {
            params: {size, page, date, destination, departure},
        });

        return response.data;
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

const getReservationDetails = async ({reference, clientName}) => {
    try {
        const response = await axios.get(`${API_BASE_URL}${RESERVATION_DETAILS_API_ENDPOINT}`, {
            params: {reference: reference, name: clientName},
        });

        return response.data;
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

const cancelReservation = async ({reference, clientName}) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}${CANCEL_RESERVATION_API_ENDPOINT}`, {
            params: {reference: reference, name: clientName},
        });
        return response.data;
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

const getSeats = async ({tripId}) => {
    try {
        const response = await axios.get(`${API_BASE_URL}${TRIP_SEAT_API_ENDPOINT.replace(":tripId", tripId)}`);
        return response.data;
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

const lockSeat = async (tripId, seatNumber) => {
    try {
        console.log("lockSeat: ", tripId, seatNumber)
        const response = await axios.post(`${API_BASE_URL}${SEAT_LOCK_API_ENDPOINT.replace(":tripId", tripId).replace(":seatNumber", seatNumber)}`);
        console.log("lockSeat: ", response)
        const authorization = response.headers['authorization']
        const sessionExpiration = response.headers['x-session-expiration'];
        console.log("authorization: ", authorization, sessionExpiration)
        return {authorization, sessionExpiration};
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

const processReservation = async (reservation, headers) => {
    try {
        const requestConfiguration = {headers: headers};
        console.log(requestConfiguration)
        const response = await axios.post(`${API_BASE_URL}${PROCESS_RESERVATION_API_ENDPOINT}`, reservation, requestConfiguration);
        return response.data;
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

const createPaymentIntent = async (paymentIntentRequest, headers) => {
    try {
        const requestConfiguration = {headers: headers};
        const response = await axios.post(`${API_BASE_URL}${PAYMENT_INTENT_API_ENDPOINT}`, paymentIntentRequest, requestConfiguration);
        return response.data;
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

const makeStripePayment = async (paymentRequest) => {
    try {
        console.log("payment request: ", paymentRequest);
        const response = await axios.post(`${PAYMENT_API_BASE_URL}${PAYMENT_REQUEST_API_ENDPOINT}`, paymentRequest);
        return response.data;
    } catch (error) {
        // Handle error, e.g., log it or throw a custom error
        console.error('Error fetching trips:', error);
        throw error;
    }
};

export {
    getTrips,
    getReservationDetails,
    cancelReservation,
    getSeats,
    lockSeat,
    processReservation,
    createPaymentIntent,
    makeStripePayment
};