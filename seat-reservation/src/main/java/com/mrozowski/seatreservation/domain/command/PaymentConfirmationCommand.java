package com.mrozowski.seatreservation.domain.command;

public record PaymentConfirmationCommand(String paymentId, String productId, int price, Status status) {

  public static PaymentConfirmationCommand success(String paymentId, String productId, int price){
    return new PaymentConfirmationCommand(paymentId, productId, price, Status.CONFIRMED);
  }

  public static PaymentConfirmationCommand failed(String paymentId, String productId, int price){
    return new PaymentConfirmationCommand(paymentId, productId, price, Status.CONFIRMED);
  }

  public boolean isSuccessful(){
    return status == Status.CONFIRMED;
  }

  private enum Status{
    CONFIRMED,
    CANCELED
  }
}
