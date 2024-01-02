import React, {useEffect, useState} from "react";
import DefaultButton from "../../components/button/DefaultButton";
import ApiService from "../../services/ApiService";
import {useLocation} from "react-router-dom";
import Navigator from "../../utils/Navigator";
import seatPlan from '../../assets/seat-plan-image.svg'
import MemoryService from "../../services/MemoryService";

const SeatSelectionPage = () => {
    const location = useLocation();
    const reservationSummary = location.state.reservationSummary;
    const {navigateToReservationSummaryPage} = Navigator();
    const [options, setOptions] = useState([]);
    const [selectedOption, setSelectedOption] = useState('');


    const fetchData = async () => {
        try {
            const seats = await ApiService.getSeats({tripId: reservationSummary.tripId});
            setOptions(seats);
        } catch (error) {
            console.log("error ", error);
            // Handle error
        }
    };

    const handleButtonClick = () => {
        if (!selectedOption) return;
        ApiService.lockSeat(reservationSummary.tripId, selectedOption)
            .then(response => {
                MemoryService.storeSessionAuth(response);
                reservationSummary.seatNumber = selectedOption;
                navigateToReservationSummaryPage(reservationSummary);
            })
            .catch(error => console.log("error ", error));
    }

    useEffect(() => {
        fetchData();
    }, []);


    const handleDropdownChange = (event) => {
        setSelectedOption(event.target.value);
    };

    return (
        <div className="main-container">
            <div className="main-card-page main-card-background">
                <div className="title">Choose seat</div>
                <div className="columns indent">
                    <div className="column">
                        <div>
                            <p className="row-name">Seat Number</p>
                            <select id="dropdown" value={selectedOption} onChange={handleDropdownChange}>
                                <option value="" disabled>Select an option</option>
                                {options && options.seats && (options.seats.map((option, key) => (
                                    <option key={key} value={option.seatNumber}>
                                        {option.seatNumber + " - " + option.status}
                                    </option>
                                )))}
                            </select>
                        </div>
                    </div>
                    <div className="column right-side">
                        <img src={seatPlan} alt="seat plan image"/>
                    </div>
                </div>

                <div className="right-side section">
                    <DefaultButton text="Next" clickEvent={handleButtonClick}></DefaultButton>
                </div>
            </div>
        </div>
    );
};

export default SeatSelectionPage;