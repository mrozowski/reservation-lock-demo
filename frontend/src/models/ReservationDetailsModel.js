class ReservationDetailsModel{
    constructor(reference, departure, destination, date, time, seatNumber, customerName, status) {
        this.reference = reference;
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.status = status;
    }
}

export default ReservationDetailsModel;