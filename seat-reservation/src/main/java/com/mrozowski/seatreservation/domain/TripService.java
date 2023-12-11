package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.command.TripFilterCommand;
import com.mrozowski.seatreservation.domain.model.TemporarySessionToken;
import com.mrozowski.seatreservation.domain.model.Trip;
import com.mrozowski.seatreservation.domain.model.TripSeatDetails;
import com.mrozowski.seatreservation.domain.port.SeatRepository;
import com.mrozowski.seatreservation.domain.port.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TripService {

  private final TripRepository tripRepository;
  private final SeatRepository seatRepository;
  private final TokenGenerator tokenGenerator;

  Page<Trip> getTripList(TripFilterCommand command) {
    return tripRepository.getTripList(command);
  }

  TripSeatDetails getSeatList(String tripId) {
    return seatRepository.getSeatList(tripId);
  }

  TemporarySessionToken lockSeat(String tripId, String seatNumber) {
    var sessionToken = tokenGenerator.generate();
    seatRepository.lockSeat(tripId, seatNumber, sessionToken);
    return sessionToken;
  }
}
