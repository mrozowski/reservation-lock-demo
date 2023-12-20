package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.domain.model.CancellationMessage;
import com.mrozowski.seatreservation.domain.model.ReservationDetails;
import com.mrozowski.seatreservation.domain.model.ReservationRequestCommand;
import com.mrozowski.seatreservation.domain.port.ReservationRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class JpaReservationRepository implements ReservationRepository {

  private final CrudReservationRepository reservationRepository;
  private final EntityManager entityManager;

  @Override
  public Optional<ReservationDetails> getReservationDetails(String reference, String customerName) {
    log.info("Get reservation details for reference: {} and name: {}", reference, customerName);
    return reservationRepository.findFirstByReferenceAndCustomerName(reference, customerName)
        .map(this::toReservationDetails);
  }

  @Override
  public CancellationMessage cancelReservation(String reference, String customerName) {
    try {
      var updatedRows = reservationRepository.cancelReservation(reference, customerName);
      return updatedRows > 0 ? CancellationMessage.successful(reference) : CancellationMessage.notFound(reference);
    } catch (HibernateException e) {
      log.error("Error during reservation cancellation: {}", e.getMessage());
      return CancellationMessage.error(reference);
    }
  }

  @Override
  public long save(ReservationRequestCommand reservationRequestCommand, String reference, long seatId, int price) {
    var entity = new ReservationEntity();
    var seatEntityProxy = entityManager.getReference(SeatEntity.class, seatId);
    var tripEntityProxy = entityManager.getReference(TripEntity.class, reservationRequestCommand.tripId());

    entity.setReference(reference);
    entity.setCustomerName(reservationRequestCommand.name() + " " + reservationRequestCommand.surname());
    entity.setPrice(price);
    entity.setPaymentStatus(ReservationEntity.PaymentStatus.PENDING);
    entity.setSeat(seatEntityProxy);
    entity.setTrip(tripEntityProxy);
    entity.setCreatedAt(OffsetDateTime.now());
    var reservationEntity = reservationRepository.save(entity);
    return reservationEntity.getId();
  }

  private ReservationDetails toReservationDetails(ReservationEntity reservationEntity) {
    var tripEntity = reservationEntity.getTrip();
    var seatEntity = reservationEntity.getSeat();

    return ReservationDetails.builder()
        .reference(reservationEntity.getReference())
        .customerName(reservationEntity.getCustomerName())
        .offsetDateTime(reservationEntity.getCreatedAt())
        .status(ReservationDetails.ReservationStatus.valueOf(reservationEntity.getPaymentStatus().toString()))
        .destination(tripEntity.getDestination())
        .departure(tripEntity.getDeparture())
        .seatNumber(seatEntity.getSeatNumber())
        .build();
  }
}
