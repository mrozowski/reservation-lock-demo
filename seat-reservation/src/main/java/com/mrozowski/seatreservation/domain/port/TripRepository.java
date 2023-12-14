package com.mrozowski.seatreservation.domain.port;

import com.mrozowski.seatreservation.domain.command.TripFilterCommand;
import com.mrozowski.seatreservation.domain.model.Trip;
import org.springframework.data.domain.Page;

public interface TripRepository {

  Page<Trip> getTripList(TripFilterCommand command);

  Trip getTripById(String tripId);
}
