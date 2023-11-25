package com.mrozowski.seatreservation.domain.command;

import lombok.Builder;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Builder
public record TripListFilterCommand(String departure, String destination, LocalDate date, int page, int pageSize) {
}
