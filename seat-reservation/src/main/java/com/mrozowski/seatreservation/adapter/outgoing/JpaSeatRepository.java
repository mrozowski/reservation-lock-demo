package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.domain.command.ResourceNotFoundException;
import com.mrozowski.seatreservation.domain.exception.SeatNotAvailableException;
import com.mrozowski.seatreservation.domain.model.TemporarySessionToken;
import com.mrozowski.seatreservation.domain.model.TripSeatDetails;
import com.mrozowski.seatreservation.domain.model.UserSeatSessionTokenConfirmation;
import com.mrozowski.seatreservation.domain.port.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
class JpaSeatRepository implements SeatRepository {

  private final CrudSeatRepository seatRepository;
  private final JpaEntityMapper mapper;

  @Override
  public TripSeatDetails getSeatList(String tripId) {
    var seatList = seatRepository.findAllByTripId(tripId).map(mapper::toSeat).toList();
    return TripSeatDetails.of(tripId, seatList);
  }

  @Override
  public void lockSeat(String tripId, String seatNumber, TemporarySessionToken sessionToken) {
    var seatEntity = getSeatEntity(tripId, seatNumber);
    if (seatEntity.isNotAvailable()) {
      throw new SeatNotAvailableException(tripId, seatNumber);
    }
    updateAvailableSeatWithLock(tripId, seatNumber, sessionToken);
  }

  @Override
  public UserSeatSessionTokenConfirmation confirmUserLockSeatSessionToken(String tripId, String seatNumber,
                                                                          String sessionToken) {
    var seatEntity = getSeatEntity(tripId, seatNumber);
    if (isSessionTokenValid(sessionToken, seatEntity)) {
      if (isSessionExpired(seatEntity)) {
        return UserSeatSessionTokenConfirmation.valid(seatEntity.getId());
      } else {
        return UserSeatSessionTokenConfirmation.expired(seatEntity.getId());
      }
    } else {
      return UserSeatSessionTokenConfirmation.invalid(seatEntity.getId());
    }
  }

  private static boolean isSessionExpired(SeatEntity seatEntity) {
    return OffsetDateTime.now().isBefore(seatEntity.getLockExpirationTime());
  }

  private static boolean isSessionTokenValid(String sessionToken, SeatEntity seatEntity) {
    return seatEntity.getLockSessionToken().equals(sessionToken);
  }

  private void updateAvailableSeatWithLock(String tripId, String seatNumber, TemporarySessionToken sessionToken) {
    int updatedRows = seatRepository.lockSeat(
        tripId, seatNumber, sessionToken.sessionToken(), sessionToken.expirationDateTime());
    if (updatedRows == 0) {
      throw new DataIntegrityViolationException("Seat update failed. The seat may not exist in the database.");
    }
  }

  private SeatEntity getSeatEntity(String tripId, String seatNumber) {
    return seatRepository.findFirstByTripIdAndSeatNumber(tripId, seatNumber)
        .orElseThrow(
            () -> new ResourceNotFoundException(String.format("Seat %s for trip %s not found", seatNumber, tripId)));
  }
}
