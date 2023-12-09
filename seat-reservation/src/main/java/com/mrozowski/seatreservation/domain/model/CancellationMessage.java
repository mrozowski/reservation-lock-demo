package com.mrozowski.seatreservation.domain.model;

import static com.mrozowski.seatreservation.domain.model.CancellationMessage.Status.*;

public record CancellationMessage(Status status, String message) {

  public static CancellationMessage successful(String referenceNr) {
    return new CancellationMessage(SUCCESS, String.format("Reservation with referenceNr: '%s' cancelled successfully"
        , referenceNr));
  }

  public static CancellationMessage error(String referenceNr) {
    return new CancellationMessage(ERROR, String.format("Unexpected Error occurred during cancelling reservation with" +
        " referenceNr: '%s'. Please try again later", referenceNr));
  }

  public static CancellationMessage notFound(String referenceNr) {
    return new CancellationMessage(NOT_FOUND, String.format("No reservation found with referenceNr: '%s'",
        referenceNr));
  }

  static enum Status {
    SUCCESS,
    ERROR,
    NOT_FOUND
  }
}
