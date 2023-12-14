package com.mrozowski.seatreservation.adapter.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ReservationRequest {
  private String name;
  private String surname;
  private String phone;
  private String email;
  private String tripId;
  private String seatNumber;
  private int price;
}
