package com.mrozowski.seatreservation.domain.command;

public record FilterCriteria(String name, Object value, FilterOperation operation) {

  public static enum FilterOperation {
    EQUAL,
    GREATER_THAN,
    LESS_THAN
  }
}
