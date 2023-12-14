package com.mrozowski.seatreservation.domain.exception;

import java.io.Serial;

public class SessionExpiredException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 7674434475781072973L;

  public SessionExpiredException() {
    super("Session for given token expired");
  }
}
