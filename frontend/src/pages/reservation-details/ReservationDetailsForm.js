import React, {useState} from 'react';
import ApiService from '../../services/ApiService';
import DefaultButton from "../../components/button/DefaultButton";
import Input from "../../components/input/Input";
import './ReservationDetails.css'
import Navigator from "../../utils/Navigator";

const ReservationDetailsForm = () => {
    const {navigateToReservationDetailsPage} = Navigator();
    const [searchQuery, setSearchQuery] = useState({
        reference: '',
        clientName: ''
    });

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setSearchQuery((prevQuery) => ({
            ...prevQuery,
            [name]: value,
        }));
    };

    const handleFormSubmit = async () => {
        navigateToReservationDetailsPage(searchQuery.reference, searchQuery.clientName);
    };

    return (
        <div className="reservation-container ">
            <h1>Reservation Details</h1>
            <div className={"reservation-search-options"}>
                <Input type={"text"}
                       name={"reference"}
                       value={searchQuery.reference}
                       label={"Reference number"}
                       onChange={handleInputChange}></Input>
                <Input type={"text"}
                       name={"clientName"}
                       value={searchQuery.clientName}
                       label={"Name"}
                       onChange={handleInputChange}></Input>
                <div className={"button-wrapper"}>
                    <DefaultButton text="Search" clickEvent={handleFormSubmit}/>
                </div>
            </div>
        </div>
    );
};

export default ReservationDetailsForm;