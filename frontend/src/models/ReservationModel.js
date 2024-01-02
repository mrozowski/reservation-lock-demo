class ReservationModel {
    constructor({id, price, name, surname, phone, email, seatNumber}) {
        this.tripId = id;
        this.price = price;
        this.seatNumber = seatNumber;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }
}

export default ReservationModel;