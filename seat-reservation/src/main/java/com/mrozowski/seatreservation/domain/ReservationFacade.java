package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.command.TripFilterCommand;
import com.mrozowski.seatreservation.domain.model.Trip;
import com.mrozowski.seatreservation.domain.port.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

  private final TripRepository tripRepository;

  public Page<Trip> getTripList(TripFilterCommand command) {
    return tripRepository.getTripList(command);
  }
}
