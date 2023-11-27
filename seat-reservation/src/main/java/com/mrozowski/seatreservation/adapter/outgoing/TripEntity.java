package com.mrozowski.seatreservation.adapter.outgoing;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "trip")
class TripEntity {

  @Id
  private String id;
  private String departure;
  private String destination;
  private OffsetDateTime date;
  private int price;
}