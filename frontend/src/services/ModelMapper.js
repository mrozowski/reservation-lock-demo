import ReservationDetailsModel from "../models/ReservationDetailsModel";
import TextFormatter from "../utils/TextFormatter";
import CancellationDetailsModel from "../models/CancellationDetailsModel";
import TripModel from "../models/TripModel";
import PageModel from "../models/PageModel";
import ReservationConfirmation from "../models/ReservationConfirmation";
import PaymentIntentSecret from "../models/PaymentIntentSecret";
import SessionAuth from "../models/SessionAuth";
import StripePaymentRequest from "../api/StripePaymentRequest";

const ModelMapper = {
    mapReservationDetails: (data) => {
        let extractedDateTime = TextFormatter.extractDateAndTime(data.offsetDateTime);
        return new ReservationDetailsModel(
            data.reference,
            data.departure,
            data.destination,
            extractedDateTime.formattedDate,
            extractedDateTime.formattedTime,
            data.seatNumber,
            data.customerName,
            data.status
        );
    },

    mapCancellationDetails: (data) => {
        return new CancellationDetailsModel(data.status, data.message);
    },

    mapPageOfTrips: (data) => {
        let trips = data.content.map(trip => {
            let extractedDateTime = TextFormatter.extractDateAndTime(trip.date);
            let formattedPrice = TextFormatter.formatPrice(trip.price, "USD");
            return new TripModel(trip.id, trip.departure, trip.destination, extractedDateTime.formattedDate, extractedDateTime.formattedTime, trip.price, formattedPrice)});
        return new PageModel(trips, data.empty, data.first, data.last, data.numberOfElements, data.size, data.totalElements, data.totalPages);
    },

    mapReservationConfirmation: (data) => {
        return new ReservationConfirmation(data.reference, data.reservationId, data.price);
    },

    mapPaymentIntentResponse: (data) => {
        return new PaymentIntentSecret(data.clientSecret, data.price, data.productId);
    },

    mapSessionAuth: (data) => {
        return new SessionAuth(data.authorization, data.sessionExpiration);
    },

    mapPaymentRequest: (data) => {
        return new StripePaymentRequest(data.amount, ""+data.productId, data.referenceNumber, "card", data.currency, data.clientSecret);
    }
}

export default ModelMapper;