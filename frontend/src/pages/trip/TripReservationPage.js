import React, {useState} from "react";
import DefaultButton from "../../components/button/DefaultButton";

const TripReservationPage = (trip) => {

    const handleButtonClick = () => {
        console.log("click & canceled")
    }

    // html is not yet finished
    return (
        <div className="main-container">
            <div className="reservation-details-container main-background-card">
                {/* Reservation Section */}
                <div>
                    <div className="title card-section">Flight Details</div>
                </div>

                <div className="details-columns indent">
                    <div className="column">
                        <div><p className="details-name">Departure:</p> <span className="emphasis-text">{trip.departure}</span></div>
                        <div><p className="details-name">Destination:</p> <span className="emphasis-text">{trip.destination}</span></div>
                        <div><p className="details-name">Price:</p> <span className="regular-text">{trip.price}</span></div>
                    </div>

                    <div className="column">
                        <div><p className="details-name">Date:</p> <span className="emphasis-text">{trip.date}</span></div>
                        <div><p className="details-name">Time:</p> <span className="emphasis-text">{trip.time}</span></div>
                    </div>
                </div>

                <hr className="gray-divider"/>

                <div>
                    <div className="card-section title">Customer Details</div>
                     {/*    add form to input customer data */}
                </div>


            </div>
        </div>
    );
};

export default TripReservationPage;