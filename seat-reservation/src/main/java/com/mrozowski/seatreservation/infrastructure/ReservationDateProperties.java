package com.mrozowski.seatreservation.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "reservation.date")
public record ReservationDateProperties(String dateFormat) {
}
