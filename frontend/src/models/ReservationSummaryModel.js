class ReservationSummaryModel {
    constructor({id, departure, destination, date, time, price, displayPrice},
                {name, surname, phone, email},
                customerName,
                seatNumber,) {
        this.tripId = id;
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.displayPrice = displayPrice;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    static of(trip, clientDetails) {
        return new ReservationSummaryModel(trip, clientDetails, clientDetails.name + " " + clientDetails.surname, '');
    }

}

export default ReservationSummaryModel;