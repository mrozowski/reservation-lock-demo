package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.model.CancellationMessage;
import com.mrozowski.seatreservation.domain.model.ReservationDetails;
import com.mrozowski.seatreservation.domain.port.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ReservationService {

  private final ReservationRepository reservationRepository;

  Optional<ReservationDetails> getReservationDetails(String reference, String customerName) {
    return reservationRepository.getReservationDetails(reference, customerName);
  }

  CancellationMessage cancelReservation(String reference, String name) {
    return reservationRepository.cancelReservation(reference, name);
  }
}
