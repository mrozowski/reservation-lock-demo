import React, {useState} from "react";
import {useLocation} from "react-router-dom";
import Input from "../../components/input/Input";
import DefaultButton from "../../components/button/DefaultButton";
import InputValidator from "../../utils/InputValidator";
import Navigator from "../../utils/Navigator";

const TripReservationPage = () => {
    const location = useLocation();
    const trip = location.state.trip;
    const {navigateToSeatSelectionPage} = Navigator();
    const [userData, setUserData] = useState({
        name: '',
        surname: '',
        phone: '',
        email: ''
    });
    const [error, setError] = useState({
        name: '',
        surname: '',
        phone: '',
        email: ''
    })

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setUserData((prevQuery) => ({
            ...prevQuery,
            [name]: value,
        }));
    };

    const handleButtonClick = () => {
        console.log("click & submit")
        const validationErrors = {
            name: InputValidator.validateName(userData.name) ? "" : "Invalid name",
            surname: InputValidator.validateName(userData.surname) ? "" : "Invalid surname",
            phone: InputValidator.validatePhone(userData.phone) ? "" : "Invalid phone number",
            email: InputValidator.validateEmail(userData.email) ? "" : "Invalid email address",
        };
        setError(validationErrors);
        const hasErrors = Object.values(validationErrors).some((error) => error !== "");

        if (hasErrors) {
            return;
        }

        navigateToSeatSelectionPage(trip.id);
    }

    return (
        <div className="main-container">
            <div className="main-card-page main-card-background">
                <div className="title">Flight Details</div>
                <div className="columns indent">
                    <div className="column">
                        <div>
                            <p className="row-name">Departure:</p>
                            <span className="emphasis-text">{trip.departure}</span>
                        </div>
                        <div>
                            <p className="row-name">Destination:</p>
                            <span className="emphasis-text">{trip.destination}</span>
                        </div>
                        <div>
                            <p className="row-name">Price:</p>
                            <span className="regular-text">{trip.price}</span>
                        </div>
                    </div>

                    <div className="column">
                        <div>
                            <p className="row-name">Date:</p>
                            <span className="emphasis-text">{trip.date}</span>
                        </div>
                        <div>
                            <p className="row-name">Time:</p>
                            <span className="emphasis-text">{trip.time}</span>
                        </div>
                    </div>
                </div>

                <hr className="divider"/>

                <div>
                    <div className="card-section title">Customer Details</div>
                    <div className="row">
                        <Input type={"text"}
                               name={"name"}
                               value={userData.name}
                               label={"Name:"}
                               labelLocation="left"
                               onChange={handleInputChange}/>
                        {error.name && (<span className="error indent">{error.name}</span>)}
                    </div>
                    <div className="row">
                        <Input type={"text"}
                               name={"surname"}
                               value={userData.surname}
                               label={"Surname:"}
                               labelLocation="left"
                               onChange={handleInputChange}/>
                        {error.surname && (<span className="error indent">{error.surname}</span>)}
                    </div>
                    <div className="row">
                        <Input type={"text"}
                               name={"phone"}
                               value={userData.phone}
                               label={"Phone:"}
                               labelLocation="left"
                               onChange={handleInputChange}/>
                        {error.phone && (<span className="error indent">{error.phone}</span>)}
                    </div>
                    <div className="row">
                        <Input type={"text"}
                               name={"email"}
                               value={userData.email}
                               label={"Email:"}
                               labelLocation="left"
                               onChange={handleInputChange}/>
                        {error.email && (<span className="error indent">{error.email}</span>)}
                    </div>
                </div>
                <div className="button-section-right">
                    <DefaultButton text="Next" clickEvent={handleButtonClick}></DefaultButton>
                </div>
            </div>
        </div>
    );
};

export default TripReservationPage;