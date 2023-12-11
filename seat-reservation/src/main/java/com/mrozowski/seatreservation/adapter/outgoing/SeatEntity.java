package com.mrozowski.seatreservation.adapter.outgoing;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "seat")
class SeatEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String tripId;

  private String seatNumber;

  @Enumerated(EnumType.STRING)
  private SeatEntityStatus status;

  private OffsetDateTime lockExpirationTime;

  private String lockSessionToken;

  enum SeatEntityStatus {
    AVAILABLE,
    LOCKED,
    RESERVED
  }

  boolean isNotAvailable() {
    return status != SeatEntityStatus.AVAILABLE;
  }

  boolean isAvailable() {
    return status == SeatEntityStatus.AVAILABLE;
  }
}
