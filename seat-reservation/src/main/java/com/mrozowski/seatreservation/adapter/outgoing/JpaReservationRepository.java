package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.domain.command.PaymentConfirmationCommand;
import com.mrozowski.seatreservation.domain.command.ResourceNotFoundException;
import com.mrozowski.seatreservation.domain.exception.DataSourceException;
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
import java.util.List;
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

  @Override
  public void updatePayment(PaymentConfirmationCommand command) {
    try {
      var status = mapReservationStatus(command);
      var updatedRows = reservationRepository.updatePaymentStatus(command.productId(), status);
      if (updatedRows == 0) throw new ResourceNotFoundException("Reservation id " + command.productId() + " not found");
    } catch (HibernateException e) {
      log.error("Error during updating payment reservation with id: {}, message {}", command.productId(),
          e.getMessage(), e);
      throw new DataSourceException("Failed to update Reservation with id: " + command.productId(), e);
    }
  }

  @Override
  public void cancelPendingReservations(List<Long> seatIds) {
    var updated = reservationRepository.cancelPendingReservations(seatIds);
    log.info("Canceled {} reservations due to expired session time", updated);
  }

  private ReservationEntity.PaymentStatus mapReservationStatus(PaymentConfirmationCommand command) {
    return command.isSuccessful() ?
        ReservationEntity.PaymentStatus.CONFIRMED :
        ReservationEntity.PaymentStatus.CANCELED;
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
