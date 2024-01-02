import React from "react";
import DefaultButton from "../../components/button/DefaultButton";

import {useLocation} from "react-router-dom";
import Navigator from "../../utils/Navigator";
import ReservationDetails from "../../components/reservation/ReservationDetails";
import ApiService from "../../services/ApiService";
import PaymentIntentRequest from "../../models/PaymentIntentRequest";
import MemoryService from "../../services/MemoryService";


const TripReservationSummary = () => {
    const location = useLocation();
    const reservationSummary = location.state.reservationSummary;
    const {navigateToPaymentPage} = Navigator();

    const handleButtonClick = async () => {
        try {
            const sessionAuth = MemoryService.getSessionAuth();
            const respond = await ApiService.processReservation(reservationSummary, sessionAuth.authorization);

            // if response is ok and there were no errors it means reservation was created properly
            const paymentIntentRequest = new PaymentIntentRequest(
                respond.price,
                "usd",
                "Reservation payment",
                respond.reference,
                respond.reservationId,
                {
                    customerName: reservationSummary.customerName,
                    email: reservationSummary.email
                })
            const paymentIntentResponse = await ApiService.createPaymentIntent(paymentIntentRequest, sessionAuth.authorization);
            MemoryService.storeInSession(respond.reservationId, {...paymentIntentRequest, clientSecret: paymentIntentResponse.clientSecret, displayPrice: reservationSummary.displayPrice});

            navigateToPaymentPage(respond.reservationId);
        } catch (error) {

        }
    }


    return (
        <div className="main-container">
            <div className="main-card-page main-card-background">
                <div className="title card-section">Summary</div>
                <ReservationDetails details={reservationSummary}/>
                <div className="right-side section">
                    <DefaultButton text="Confirm" clickEvent={handleButtonClick}></DefaultButton>
                </div>
            </div>
        </div>
    );
};

export default TripReservationSummary;