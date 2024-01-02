import React, {useEffect, useState} from 'react';
import Navigator from "../../utils/Navigator";
import {useParams} from "react-router-dom";
import MemoryService from "../../services/MemoryService";
import Input from "../../components/input/Input";
import DefaultButton from "../../components/button/DefaultButton";
import ApiService from "../../services/ApiService";

const PaymentPage = () => {
    const {reservationId} = useParams();
    const {navigateToReservationDetailsPage} = Navigator();
    const [loading, setLoading] = useState(true);
    const [paymentDetails, setPaymentDetails] = useState(null)
    const [cardDetails, setCardDetails] = useState({
        name: '',
        cardNr: '',
        expireDate: '',
        ccv: '',
    });
    const [error, setError] = useState({
        name: '',
        cardNr: '',
        expireDate: '',
        ccv: '',
    })

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setCardDetails((prevQuery) => ({
            ...prevQuery,
            [name]: value,
        }));
    };

    useEffect(() => {
        const paymentDetails = MemoryService.getFromSession(reservationId);
        setPaymentDetails(paymentDetails)
        setLoading(false);
    }, []);

    const handleFormSubmit = async () => {
        console.log("details a: ", paymentDetails)
        ApiService.makeStripePayment(paymentDetails)
            .then(()=> navigateToReservationDetailsPage(paymentDetails.referenceNumber, paymentDetails.metadata.customerName))
            .catch(error => console.log("error: ", error))
    };

    return (
        <div className="main-container">
            <div className="main-card-page main-card-background">
                {loading && <p>Loading...</p>}
                {!loading && paymentDetails && (
                    <>
                        <div className="title">Payment</div>
                        <div className="indent row">
                            <p className="row-name">To pay: </p>
                            <span className="regular-text">{paymentDetails.displayPrice}</span>
                        </div>

                        <div>
                            <div className="card-section title">Card Details</div>
                            <div className="row">
                                <Input type={"text"}
                                       name={"name"}
                                       value={cardDetails.name}
                                       label={"Name:"}
                                       labelLocation="left"
                                       onChange={handleInputChange}/>
                                {error.name && (<span className="error indent">{error.name}</span>)}
                            </div>
                            <div className="row">
                                <Input type={"number"}
                                       name={"cardNr"}
                                       value={cardDetails.cardNr}
                                       label={"Card nr:"}
                                       labelLocation="left"
                                       onChange={handleInputChange}/>
                                {error.cardNr && (<span className="error indent">{error.cardNr}</span>)}
                            </div>
                            <div className="row">
                                <Input type={"date"}
                                       name={"expireDate"}
                                       value={cardDetails.expireDate}
                                       label={"Expired:"}
                                       labelLocation="left"
                                       onChange={handleInputChange}/>
                                {error.expireDate && (<span className="error indent">{error.expireDate}</span>)}
                            </div>
                            <div className="row">
                                <Input type={"number"}
                                       name={"ccv"}
                                       value={cardDetails.ccv}
                                       label={"CCV:"}
                                       labelLocation="left"
                                       onChange={handleInputChange}/>
                                {error.ccv && (<span className="error indent">{error.ccv}</span>)}
                            </div>
                        </div>
                        <div className="button-section-right">
                            <DefaultButton text="Pay" clickEvent={handleFormSubmit}></DefaultButton>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

export default PaymentPage;