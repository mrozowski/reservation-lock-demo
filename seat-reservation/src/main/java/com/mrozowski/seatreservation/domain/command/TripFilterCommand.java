package com.mrozowski.seatreservation.domain.command;

import lombok.Builder;

import java.util.List;

@Builder
public record TripFilterCommand(List<FilterCriteria> filters, int page, int pageSize) {
}
