package com.mrozowski.seatreservation.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "reservation.payment")
public record ReservationPaymentProperties(Map<PaymentMethodTypes, PaymentMethod> types) {


  public PaymentMethod getStripe() {
    return types.get(PaymentMethodTypes.STRIPE);
  }

  public record PaymentMethod(String successfulPaymentStatus) {
  }

  enum PaymentMethodTypes {
    STRIPE("stripe");

    PaymentMethodTypes(String name) {

    }
  }
}
