package com.mrozowski.seatreservation.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "reservation.seat-lock")
public record ReservationSeatLockProperties(Duration expirationTime, String scheduler) {
}
