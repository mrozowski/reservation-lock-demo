package com.mrozowski.seatreservation.domain.port;

import com.mrozowski.seatreservation.domain.model.CancellationMessage;
import com.mrozowski.seatreservation.domain.model.ReservationDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ReservationRepository {

  Optional<ReservationDetails> getReservationDetails(String reference, String customerName);

  @Transactional
  CancellationMessage cancelReservation(String reference, String name);
}
