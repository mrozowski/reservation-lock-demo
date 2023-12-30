import React, {useState} from 'react';
import './ReservationDetails.css'
import DefaultButton from "../../components/button/DefaultButton";
import ConfirmationPopup from "../../components/popup/ConfirmationPopup";
import ApiService from "../../services/apiService";


const ReservationDetailsResult = ({details}) => {
    const [isPopupVisible, setPopupVisibility] = useState(false);

    const handleButtonClick = () => {
        console.log("click & canceled")
        setPopupVisibility(true);
    }

    const handleCancelReservationActionConfirmed = async () => {
        console.log("User Canceled reservation")
        try {
            const cancellation = await ApiService.cancelReservation({
                reference: details.reference,
                clientName: details.customerName
            });
            // show toast
            console.log("Reservation canceled")
        } catch (error) {
            // Handle error
            console.log("error: " + error)
        }
        setPopupVisibility(false);
    }

    const handleCancelReservationActionCanceled = () => {
        console.log("User abort action")
        // show toast
        setPopupVisibility(false);
    }

    function copyToClipboard(reference) {
        console.log("clicked & copied")
    }

    return (
        <div className="main-container">
            <div className="reservation-details-container main-background-card">
                {/* Reservation Section */}
                <div>
                    <div className="title">Reservation</div>
                    <div className="indent">
                        <p className="details-name">Reference Nr.:</p> <span className="reference-value emphasis-text">{details.reference}</span>
                        <span className="copy-icon" onClick={() => copyToClipboard(details.reference)}>
            &#128203;
          </span>
                        <div><p className="details-name">Status:</p> {details.status}</div>
                    </div>
                    <div className="title card-section">Flight Details</div>
                </div>

                {/* Flight Details Section */}
                <div className="details-columns indent">
                    {/* First Column */}
                    <div className="column">
                        <div><p className="details-name">Departure:</p> <span className="emphasis-text">{details.departure}</span></div>
                        <div><p className="details-name">Destination:</p> <span className="emphasis-text">{details.destination}</span></div>
                        <div><p className="details-name">Price:</p> <span className="regular-text">{details.price}</span></div>
                    </div>

                    {/* Second Column */}
                    <div className="column">
                        <div><p className="details-name">Date:</p> <span className="emphasis-text">{details.date}</span></div>
                        <div><p className="details-name">Time:</p> <span className="emphasis-text">{details.time}</span></div>
                        <div><p className="details-name">Seat Number:</p> <span className="emphasis-text">{details.seatNumber}</span></div>
                    </div>
                </div>

                {/* Gray Divider Line */}
                <hr className="gray-divider"/>

                {/* Customer Details Section */}
                <div>
                    <div className="card-section title">Customer Details</div>
                    <div className="indent"><p className="details-name">Name:</p> {details.customerName}</div>
                </div>

                {/* Cancel Button */}
                <div className="cancel-button">
                    <DefaultButton text="Cancel" clickEvent={handleButtonClick}></DefaultButton>
                </div>
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