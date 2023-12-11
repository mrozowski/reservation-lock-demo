package com.mrozowski.seatreservation.domain.command;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 7333488311539086890L;

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
