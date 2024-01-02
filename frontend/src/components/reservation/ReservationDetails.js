const ReservationDetails = ({details}) => {

    return (
        <>
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
                        <span className="regular-text">{details.displayPrice}</span>
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
        </>
    );
};

export default ReservationDetails;