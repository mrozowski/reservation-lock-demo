package com.mrozowski.seatreservation.domain.command;

public record InitializePaymentCommand(PaymentMethod method, int price, String productId, String currency) {

  public static InitializePaymentCommand stripe(int price, String productId, String currency){
    return new InitializePaymentCommand(PaymentMethod.STRIPE, price, productId, currency);
  }

  public enum PaymentMethod{
    STRIPE,
    PAYPAL
  }
}
