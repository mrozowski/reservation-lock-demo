package com.mrozowski.seatreservation.domain.model;

public record ReservationConfirmation(String reference, long reservationId, int price) {

  public static ReservationConfirmation of(String reference, long reservationId, int price) {
    return new ReservationConfirmation(reference, reservationId, price);
  }
}