package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.command.TripFilterCommand;
import com.mrozowski.seatreservation.domain.model.CancellationMessage;
import com.mrozowski.seatreservation.domain.model.ReservationDetails;
import com.mrozowski.seatreservation.domain.model.Trip;
import com.mrozowski.seatreservation.domain.port.ReservationRepository;
import com.mrozowski.seatreservation.domain.port.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

  private final TripRepository tripRepository;
  private final ReservationRepository reservationRepository;

  public Page<Trip> getTripList(TripFilterCommand command) {
    return tripRepository.getTripList(command);
  }

  public Optional<ReservationDetails> getReservationDetails(String reference, String customerName) {
    return reservationRepository.getReservationDetails(reference, customerName);
  }

  public CancellationMessage cancelReservation(String reference, String name) {
    return reservationRepository.cancelReservation(reference, name);
  }
}
