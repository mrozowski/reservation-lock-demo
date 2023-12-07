package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.domain.model.ReservationDetails;
import com.mrozowski.seatreservation.domain.port.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class JpaReservationRepository implements ReservationRepository {

  private final CrudReservationRepository reservationRepository;

  @Override
  public Optional<ReservationDetails> getReservationDetails(String reference, String customerName) {
    return reservationRepository.findFirstByReferenceAndCustomerName(reference, customerName)
        .map(this::toReservationDetails);
  }

  private ReservationDetails toReservationDetails(ReservationEntity reservationEntity) {
    var tripEntity = reservationEntity.getTrip();
    var seatEntity = reservationEntity.getSeat();

    return ReservationDetails.builder()
        .reference(reservationEntity.getReference())
        .customerName(reservationEntity.getCustomerName())
        .offsetDateTime(reservationEntity.getCreatedAt())
        .status(ReservationDetails.ReservationStatus.valueOf(reservationEntity.getPaymentStatus()))
        .destination(tripEntity.getDestination())
        .departure(tripEntity.getDeparture())
        .seatNumber(seatEntity.getSeatNumber())
        .build();
  }
}
