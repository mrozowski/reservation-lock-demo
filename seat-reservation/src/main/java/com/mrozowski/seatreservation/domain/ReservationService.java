package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.exception.InvalidTokenException;
import com.mrozowski.seatreservation.domain.exception.SessionExpiredException;
import com.mrozowski.seatreservation.domain.model.CancellationMessage;
import com.mrozowski.seatreservation.domain.model.ReservationConfirmation;
import com.mrozowski.seatreservation.domain.model.ReservationDetails;
import com.mrozowski.seatreservation.domain.model.ReservationRequestCommand;
import com.mrozowski.seatreservation.domain.port.ReservationRepository;
import com.mrozowski.seatreservation.domain.port.SeatRepository;
import com.mrozowski.seatreservation.domain.port.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class ReservationService {

  private final ReservationRepository reservationRepository;
  private final TripRepository tripRepository;
  private final SeatRepository seatRepository;
  private final BookingReferenceGenerator bookingReferenceGenerator;

  Optional<ReservationDetails> getReservationDetails(String reference, String customerName) {
    return reservationRepository.getReservationDetails(reference, customerName);
  }

  CancellationMessage cancelReservation(String reference, String name) {
    return reservationRepository.cancelReservation(reference, name);
  }

  public ReservationConfirmation process(ReservationRequestCommand reservationRequestCommand) {
    var userSessionConfirmation = seatRepository.confirmUserLockSeatSessionToken(
        reservationRequestCommand.tripId(), reservationRequestCommand.seatNumber(), reservationRequestCommand.token());

    if (userSessionConfirmation.isValid()) {
      var trip = tripRepository.getTripById(reservationRequestCommand.tripId());
      var reference = bookingReferenceGenerator.generate();
      var reservationId = reservationRepository.save(reservationRequestCommand, reference,
          userSessionConfirmation.seatId(), trip.price());
      return ReservationConfirmation.of(reference, reservationId, trip.price());
    } else if (userSessionConfirmation.isExpired()) {
      log.info("Failed to save reservation due to expired session token");
      throw new SessionExpiredException();
    } else {
      log.info("Failed to save reservation due to invalid session token: {}", reservationRequestCommand.token());
      throw new InvalidTokenException("Reservation could not be completed due to invalid session token");
    }
  }
}
