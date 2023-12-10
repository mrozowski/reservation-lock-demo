package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.adapter.outgoing.SeatEntity.SeatEntityStatus;
import com.mrozowski.seatreservation.domain.model.Trip;
import com.mrozowski.seatreservation.domain.model.TripSeatDetails.Seat;
import org.springframework.stereotype.Component;

@Component
class JpaTripMapper {


  Seat toSeat(SeatEntity entity) {
    var status = entity.getStatus() == SeatEntityStatus.AVAILABLE ? Seat.SeatStatus.AVAILABLE :
        Seat.SeatStatus.RESERVED;
    return new Seat(entity.getSeatNumber(), status);
  }

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
