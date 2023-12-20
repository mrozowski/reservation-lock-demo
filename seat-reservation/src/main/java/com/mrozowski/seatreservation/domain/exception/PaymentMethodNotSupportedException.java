package com.mrozowski.seatreservation.domain.exception;

import java.io.Serial;

public class PaymentMethodNotSupportedException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -4775217686864659053L;

  public PaymentMethodNotSupportedException(String method) {
    super("Payment method: " + method + " not supported");
  }
}
