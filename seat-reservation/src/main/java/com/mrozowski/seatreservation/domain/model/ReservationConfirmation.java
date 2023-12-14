package com.mrozowski.seatreservation.domain.model;

public record ReservationConfirmation(String reference, int price) {

  public static ReservationConfirmation of(String reference, int price) {
    return new ReservationConfirmation(reference, price);
  }
}