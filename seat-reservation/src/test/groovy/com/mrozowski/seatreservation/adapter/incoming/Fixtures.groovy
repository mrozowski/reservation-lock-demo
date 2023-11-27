package com.mrozowski.seatreservation.adapter.incoming

import com.mrozowski.seatreservation.domain.model.Trip
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

import java.time.LocalDate
import java.time.OffsetDateTime

class Fixtures {

  static final String DATE = "date"
  static final String DEPARTURE = "departure"
  static final String DESTINATION = "destination"
  static final String PAGE = "page"
  static final String SIZE = "size"

  static final String DATE_VALUE = "2024-01-01"
  static final String DEPARTURE_VALUE = "CityA"
  static final String DESTINATION_VALUE = "CityB"
  static final String PAGE_NUMBER = "1"
  static final String SIZE_VALUE = "30"
  static final String TRIP_ID = "AB123T"

  static final int PRICE = 100
  static final LocalDate LOCAL_DATE_VALUE = LocalDate.of(2023, 1, 1)

  static List<Trip> TRIPS =
      [Trip.builder()
           .date(OffsetDateTime.now())
           .departure(Fixtures.DEPARTURE_VALUE)
           .destination(Fixtures.DESTINATION_VALUE)
           .price(Fixtures.PRICE)
           .id(Fixtures.TRIP_ID)
           .build()]

  static Page<Trip> TRIP_PAGE =
      new PageImpl<>(TRIPS, PageRequest.of(0, TRIPS.size()), TRIPS.size())
}
