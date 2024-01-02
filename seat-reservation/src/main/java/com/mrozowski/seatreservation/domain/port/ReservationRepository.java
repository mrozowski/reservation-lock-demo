package com.mrozowski.seatreservation.domain.port;

import com.mrozowski.seatreservation.domain.command.PaymentConfirmationCommand;
import com.mrozowski.seatreservation.domain.model.CancellationMessage;
import com.mrozowski.seatreservation.domain.model.ReservationDetails;
import com.mrozowski.seatreservation.domain.model.ReservationRequestCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

  Optional<ReservationDetails> getReservationDetails(String reference, String customerName);

  @Transactional
  CancellationMessage cancelReservation(String reference, String name);

  long save(ReservationRequestCommand reservationRequestCommand, String reference, long seatId, int price);

  @Transactional
  void updatePayment(PaymentConfirmationCommand command);

  void cancelPendingReservations(List<Long> seatIds);
}
