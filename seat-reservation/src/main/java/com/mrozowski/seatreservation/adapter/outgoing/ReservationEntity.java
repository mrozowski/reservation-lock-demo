package com.mrozowski.seatreservation.adapter.outgoing;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "reservation")
class ReservationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "trip_id", referencedColumnName = "id")
  private TripEntity trip;

  @ManyToOne
  @JoinColumn(name = "seat_id", referencedColumnName = "id")
  private SeatEntity seat;

  private String reference;
  private String customerName;
  private OffsetDateTime createdAt;
  private String paymentStatus;
}
