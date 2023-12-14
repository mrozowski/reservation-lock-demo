package com.mrozowski.seatreservation.domain.exception;

import java.io.Serial;

public class InvalidTokenException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -941394196527326404L;

  public InvalidTokenException(String message) {
    super(message);
  }
}
