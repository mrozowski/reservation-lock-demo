package com.mrozowski.seatreservation.adapter.incoming;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
class DateParser {

  private final DateTimeFormatter reservationDateTimeFormatter;

  LocalDate toLocalDate(String date) {
    return LocalDate.parse(date, reservationDateTimeFormatter);
  }
}
