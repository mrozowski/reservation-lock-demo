package com.mrozowski.seatreservation.domain.model;

import java.time.OffsetDateTime;

public record TemporarySessionToken(String sessionToken, OffsetDateTime expirationDateTime) {
}
