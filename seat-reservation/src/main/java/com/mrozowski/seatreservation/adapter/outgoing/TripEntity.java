package com.mrozowski.seatreservation.adapter.outgoing;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("trip")
record TripEntity(@Id String id, String departure, String destination, OffsetDateTime date, int price) {

}
