package com.mrozowski.seatreservation.domain.port;

import com.mrozowski.seatreservation.domain.command.TripFilterCommand;
import com.mrozowski.seatreservation.domain.model.Trip;
import com.mrozowski.seatreservation.domain.model.TripSeatDetails;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface TripRepository {

  Page<Trip> getTripList(TripFilterCommand command);

  @Transactional(readOnly = true)
  TripSeatDetails getSeatList(String tripId);
}
