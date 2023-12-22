package com.mrozowski.seatreservation.domain.model;

import java.util.List;

public record TripSeatDetails(String tripId, List<Seat> seats, int availableSeats) {

  public static TripSeatDetails of(String tripId, List<Seat> seats) {
    return new TripSeatDetails(
        tripId, seats, (int) seats.stream().filter(e -> e.status == Seat.SeatStatus.AVAILABLE).count());
  }

  public record Seat(String seatNumber, SeatStatus status) {

    public enum SeatStatus {
      AVAILABLE,
      RESERVED
    }
  }
}
