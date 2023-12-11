package com.mrozowski.seatreservation.domain.exception;

import java.io.Serial;

public class SeatNotAvailableException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -6665459788479346L;

  public SeatNotAvailableException(String tripId, String seatNumber) {
    super(String.format("Seat %s from trip %s is not available", seatNumber, tripId));
  }
}
