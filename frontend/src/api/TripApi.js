import axios from 'axios';
import {API_BASE_URL} from './ApiConfig';

const TRIP_API_ENDPOINT = 'v1/trips/search';
const RESERVATION_DETAILS_API_ENDPOINT = 'v1/reservations/details';
const CANCEL_RESERVATION_API_ENDPOINT = 'v1/reservations/cancel';
const TRIP_SEAT_API_ENDPOINT = 'v1/trips/:tripId/seats';

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

export {getTrips, getReservationDetails, cancelReservation, getSeats};