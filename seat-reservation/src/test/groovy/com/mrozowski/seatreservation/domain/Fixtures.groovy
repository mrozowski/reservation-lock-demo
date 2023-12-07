package com.mrozowski.seatreservation.domain

import com.mrozowski.seatreservation.domain.model.ReservationDetails
import com.mrozowski.seatreservation.domain.model.Trip
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

import java.time.OffsetDateTime
import java.time.ZoneOffset

import static com.mrozowski.seatreservation.domain.model.ReservationDetails.ReservationStatus.CONFIRMED

class Fixtures {

  static final String DATE = "2024-01-01"
  static final String DEPARTURE = "CityA"
  static final String DESTINATION = "CityB"
  static final int PAGE_NUMBER = 1
  static final int SIZE = 30
  static final String TRIP_ID = "AB123T"
  static final int PRICE = 100
  static final String REFERENCE_NUMBER = "WLCS24"
  static final String CUSTOMER_NAME = "John Doe"
  static final String SEAT_NUMBER = "C15"
  static final OffsetDateTime OFFSET_DATE_TIME = OffsetDateTime.of(2024, 5, 7, 10, 00, 00, 0, ZoneOffset.UTC)


  static List<Trip> TRIPS =
      [Trip.builder()
           .date(OffsetDateTime.now())
           .departure(Fixtures.DEPARTURE)
           .destination(Fixtures.DESTINATION)
           .price(Fixtures.PRICE)
           .id(Fixtures.TRIP_ID)
           .build()]

  static Page<Trip> TRIP_PAGE =
      new PageImpl<>(TRIPS, PageRequest.of(0, TRIPS.size()), TRIPS.size())

  static ReservationDetails RESERVATION_DETAILS = ReservationDetails.builder()
      .reference(REFERENCE_NUMBER)
      .departure(DEPARTURE)
      .destination(DESTINATION)
      .seatNumber(SEAT_NUMBER)
      .customerName(CUSTOMER_NAME)
      .offsetDateTime(OFFSET_DATE_TIME)
      .status(CONFIRMED)
      .build()
}
