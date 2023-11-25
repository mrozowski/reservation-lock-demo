package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.domain.model.Trip;
import org.springframework.stereotype.Component;

@Component
class JdbcTripMapper {

  Trip toTrip(TripEntity entity){
    return Trip.builder()
        .id(entity.id())
        .departure(entity.departure())
        .destination(entity.destination())
        .price(entity.price())
        .date(entity.date())
        .build();
  }
}
