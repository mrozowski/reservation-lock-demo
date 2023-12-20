package com.mrozowski.seatreservation.adapter.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class PaymentRequest {
  private int amount;
  private String currency;
  private String description;
  private String referenceNumber;
  private String productId;
  private Metadata metadata;

  @Data
  static class Metadata {
    private String customerName;
    private String email;
  }
}