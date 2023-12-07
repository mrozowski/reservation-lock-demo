package com.mrozowski.seatreservation.adapter.outgoing;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "seat")
public class SeatEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "trip_id", referencedColumnName = "id")
  private TripEntity trip;

  private String seatNumber;

  private String status;

  private OffsetDateTime lockExpirationTime;
}
