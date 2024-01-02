import {useNavigate} from "react-router-dom";

const Navigator = () =>{
    const navigate = useNavigate();

    const navigateToReservationDetailsPage = (reference, clientName) => {
        console.log("show details: ", reference, clientName)
        const encodedClientName = encodeURIComponent(clientName);
        navigate(`/reservations/${reference}/${encodedClientName}`);
    }

    const navigateToTripReservationPage = (trip) => {
        navigate(`/trips/${trip.id}`, { state: { trip } });
    }

    const navigateToSeatSelectionPage = (reservationSummary) => {
        navigate(`/trips/${reservationSummary.tripId}/seat-selection`, { state: { reservationSummary } });
    }

    const navigateToReservationSummaryPage = (reservationSummary) => {
        navigate(`/trips/${reservationSummary.tripId}/summary`, { state: { reservationSummary } });
    }

    const navigateToPaymentPage = (reservationId) => {
        navigate(`/payment/${reservationId}`);
    }


    return {
        navigateToTripReservationPage,
        navigateToReservationDetailsPage,
        navigateToSeatSelectionPage,
        navigateToReservationSummaryPage,
        navigateToPaymentPage
    };
}

export default Navigator;