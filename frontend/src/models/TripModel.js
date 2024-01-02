class TripModel{
    constructor(id, departure, destination, date, time, price, displayPrice) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.displayPrice = displayPrice;
    }
}

export default TripModel;