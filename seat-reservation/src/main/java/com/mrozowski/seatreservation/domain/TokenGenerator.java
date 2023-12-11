package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.model.TemporarySessionToken;
import com.mrozowski.seatreservation.infrastructure.ReservationSeatLockProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class TokenGenerator {

  private final ReservationSeatLockProperties lockProperties;

  TemporarySessionToken generate() {
    var sessionToken = generateToken();
    var expirationDate = generateExpirationDate();
    return new TemporarySessionToken(sessionToken, expirationDate);
  }

  private OffsetDateTime generateExpirationDate() {
    return OffsetDateTime.now().plus(lockProperties.expirationTime());
  }

  private String generateToken() {
    return UUID.randomUUID().toString();
  }
}
