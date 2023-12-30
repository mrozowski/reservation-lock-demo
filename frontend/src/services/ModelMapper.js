import ReservationDetailsModel from "../models/ReservationDetailsModel";
import TextFormatter from "../utils/TextFormatter";
import CancellationDetailsModel from "../models/CancellationDetailsModel";

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
    },

    mapCancellationDetails: (data) => {
        console.log("mapping: ", data);
        return new CancellationDetailsModel(data.status, data.message);
    }
}

export default ModelMapper;