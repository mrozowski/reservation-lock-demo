package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.domain.model.Trip;
import org.springframework.stereotype.Component;

@Component
class JpaTripMapper {

  Trip toTrip(TripEntity entity) {
    return Trip.builder()
        .id(entity.getId())
        .departure(entity.getDeparture())
        .destination(entity.getDestination())
        .price(entity.getPrice())
        .date(entity.getDate())
        .build();
  }
}
