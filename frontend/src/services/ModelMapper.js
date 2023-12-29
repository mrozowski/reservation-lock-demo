import ReservationDetailsModel from "../models/ReservationDetailsModel";
import TextFormatter from "../utils/TextFormatter";

const ModelMapper = {
    mapReservationDetails: (data) => {
        console.log("mapping: ", data);
        let extractedDateTime = TextFormatter.extractDateAndTime(data.offsetDateTime);
        return new ReservationDetailsModel(
            data.reference,
            data.departure,
            data.destination,
            extractedDateTime.formattedDate,
            extractedDateTime.formattedTime,
            data.seatNumber,
            data.customerName,
            data.status
        );
    }
}

export default ModelMapper;