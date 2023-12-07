package com.mrozowski.seatreservation.domain.port;

import com.mrozowski.seatreservation.domain.model.ReservationDetails;

import java.util.Optional;

public interface ReservationRepository {

  Optional<ReservationDetails> getReservationDetails(String reference, String customerName);
}
