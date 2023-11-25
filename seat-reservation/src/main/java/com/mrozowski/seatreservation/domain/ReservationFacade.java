package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.command.TripListFilterCommand;
import com.mrozowski.seatreservation.domain.model.Trip;
import com.mrozowski.seatreservation.domain.port.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

  private TripRepository tripRepository;
  public Page<Trip> getTripList(TripListFilterCommand command){
    return tripRepository.getTripList(command);
  }

}
