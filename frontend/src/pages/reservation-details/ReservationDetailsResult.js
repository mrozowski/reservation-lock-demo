import React, {useEffect, useState} from 'react';
import './ReservationDetails.css'
import DefaultButton from "../../components/button/DefaultButton";
import ConfirmationPopup from "../../components/popup/ConfirmationPopup";
import ApiService from "../../services/ApiService";
import {useParams} from "react-router-dom";


const ReservationDetailsResult = () => {
    const {reference, clientName} = useParams();
    const [details, setDetails] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isPopupVisible, setPopupVisibility] = useState(false);


    const handleButtonClick = () => {
        setPopupVisibility(true);
    }

    const handleCancelReservationActionConfirmed = async () => {
        try {
            const cancellation = await ApiService.cancelReservation({
                reference: reference,
                clientName: clientName
            });
            // show toast
            console.log("Reservation canceled");
        } catch (error) {
            // Handle error
            console.log("error: ", error);
        }
        setPopupVisibility(false);
    }

    const handleCancelReservationActionCanceled = () => {
        console.log("User abort action")
        // show toast
        setPopupVisibility(false);
    }

    const fetchTrip = async () => {
        try {
            const tripsData = await ApiService.getReservationDetails({
                reference: reference,
                clientName: clientName
            });
            console.log(tripsData);
            setDetails(tripsData);
            setLoading(false)
        } catch (error) {
            // Handle error
            console.log("error: " + error)
        }
    };

    useEffect(() => {
        console.log("ref: " + reference + ". name: " + clientName);
        fetchTrip();
    }, []);

    return (
        <div className="main-container">
            <div className="main-card-page main-card-background">
                {loading && <p>Loading...</p>}
                {!loading && details && (
                    <>
                        <div className="title">Reservation</div>
                        <div className="indent">
                            <p className="row-name">Reference Nr.:</p>
                            <span className="reference-value emphasis-text">{details.reference}</span>
                            <div><p className="row-name">Status:</p> {details.status}</div>
                        </div>

                        <div className="title card-section">Flight Details</div>
                        <div className="columns indent">
                            <div className="column">
                                <div>
                                    <p className="row-name">Departure:</p>
                                    <span className="emphasis-text">{details.departure}</span>
                                </div>
                                <div>
                                    <p className="row-name">Destination:</p>
                                    <span className="emphasis-text">{details.destination}</span>
                                </div>
                                <div>
                                    <p className="row-name">Price:</p>
                                    <span className="regular-text">{details.price}</span>
                                </div>
                            </div>

                            <div className="column">
                                <div><p className="row-name">Date:</p>
                                    <span className="emphasis-text">{details.date}</span>
                                </div>
                                <div>
                                    <p className="row-name">Time:</p>
                                    <span className="emphasis-text">{details.time}</span>
                                </div>
                                <div>
                                    <p className="row-name">Seat Number:</p>
                                    <span className="emphasis-text">{details.seatNumber}</span>
                                </div>
                            </div>
                        </div>

                        <hr className="divider"/>

                        <div>
                            <div className="card-section title">Customer Details</div>
                            <div className="indent"><p className="row-name">Name:</p> {details.customerName}</div>
                        </div>

                        <div className="button-section-right">
                            <DefaultButton text="Cancel" clickEvent={handleButtonClick}></DefaultButton>
                        </div>
                    </>
                )}
            </div>

            {isPopupVisible && (
                <ConfirmationPopup
                    text="Are you sure you want to cancel the reservation?"
                    onCancel={handleCancelReservationActionCanceled}
                    onConfirm={handleCancelReservationActionConfirmed}
                />
            )}
        </div>
    );
};

export default ReservationDetailsResult;