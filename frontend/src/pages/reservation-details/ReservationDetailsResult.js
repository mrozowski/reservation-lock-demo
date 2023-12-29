import React from 'react';
import './ReservationDetails.css'
import DefaultButton from "../../components/button/DefaultButton";


const ReservationDetailsResult = ({details}) => {

    const handleButtonClick = () => {
        console.log("click & canceled")
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
                        Reference Nr.: <span className="reference-value emphasis-text">{details.reference}</span>
                        <span className="copy-icon" onClick={() => copyToClipboard(details.reference)}>
            &#128203;
          </span>
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
        </div>
    );
};

export default ReservationDetailsResult;