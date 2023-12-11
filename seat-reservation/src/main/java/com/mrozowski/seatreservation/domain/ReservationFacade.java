package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.command.TripFilterCommand;
import com.mrozowski.seatreservation.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

  private final TripService tripService;
  private final ReservationService reservationService;

  public Page<Trip> getTripList(TripFilterCommand command) {
    return tripService.getTripList(command);
  }

  public Optional<ReservationDetails> getReservationDetails(String reference, String customerName) {
    return reservationService.getReservationDetails(reference, customerName);
  }

  public CancellationMessage cancelReservation(String reference, String name) {
    return reservationService.cancelReservation(reference, name);
  }

  public TripSeatDetails getSeatList(String tripId) {
    return tripService.getSeatList(tripId);
  }

  public TemporarySessionToken lockSeat(String tripId, String seatNumber) {
    return tripService.lockSeat(tripId, seatNumber);
  }
}
