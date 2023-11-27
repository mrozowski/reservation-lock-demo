package com.mrozowski.seatreservation.domain.command;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Builder
public record TripFilterCommand(List<FilterCriteria> filters, int page, int pageSize) {
}
