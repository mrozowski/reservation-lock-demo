import {useNavigate} from "react-router-dom";

const Navigator = () =>{
    const navigate = useNavigate();

    const navigateToReservationDetailsPage = (reference, clientName) => {
        const encodedClientName = encodeURIComponent(clientName);
        navigate(`/reservations/${reference}/${encodedClientName}`);
    }

    const navigateToTripReservationPage = (trip) => {
        navigate(`/trips/${trip.id}`, { state: { trip } });
    }

    const navigateToSeatSelectionPage = (tripId) => {
        navigate(`/trips/${tripId}/seat-selection`);
    }

    return {
        navigateToTripReservationPage,
        navigateToReservationDetailsPage,
        navigateToSeatSelectionPage
    };
}

export default Navigator;