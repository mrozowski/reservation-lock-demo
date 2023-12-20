package com.mrozowski.seatreservation.adapter.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class StripePaymentConfirmationRequest {

  private String id;
  private int amount;
  private String clientSecret;
  private String productId;
  private String created;
  private String currency;
  private String description;
  private List<String> paymentMethodTypes;
  private String status;
}
