import React, {useState} from 'react';
import apiService from '../../services/apiService';
import DefaultButton from "../../components/button/DefaultButton";
import Input from "../../components/input/Input";
import './ReservationDetails.css'

const ReservationDetailsForm = ({onSubmit}) => {
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
        try {
            const tripsData = await apiService.getReservationDetails({
                reference: searchQuery.reference,
                clientName: searchQuery.clientName
            });
            console.log(tripsData)
            onSubmit(tripsData)
        } catch (error) {
            // Handle error
            console.log("error: " + error)
        }
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