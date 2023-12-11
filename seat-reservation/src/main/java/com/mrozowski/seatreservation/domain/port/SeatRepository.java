package com.mrozowski.seatreservation.domain.port;

import com.mrozowski.seatreservation.domain.model.TemporarySessionToken;
import com.mrozowski.seatreservation.domain.model.TripSeatDetails;
import org.springframework.transaction.annotation.Transactional;

public interface SeatRepository {

  @Transactional(readOnly = true)
  TripSeatDetails getSeatList(String tripId);

  @Transactional
  void lockSeat(String tripId, String seatNumber, TemporarySessionToken sessionToken);
}
