package com.mrozowski.seatreservation.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ReservationDetails(String reference,
                                 String departure,
                                 String destination,
                                 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") OffsetDateTime offsetDateTime,
                                 String seatNumber,
                                 String customerName,
                                 ReservationStatus status) {


  public enum ReservationStatus {
    PENDING,
    CANCELED,
    CONFIRMED
  }
}
