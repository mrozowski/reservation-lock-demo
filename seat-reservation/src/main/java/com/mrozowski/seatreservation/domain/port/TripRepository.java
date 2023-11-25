package com.mrozowski.seatreservation.domain.port;

import com.mrozowski.seatreservation.domain.command.TripListFilterCommand;
import com.mrozowski.seatreservation.domain.model.Trip;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TripRepository {

  Page<Trip> getTripList(TripListFilterCommand command);
}
