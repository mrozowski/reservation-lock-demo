package com.mrozowski.seatreservation.adapter.incoming

import com.mrozowski.seatreservation.domain.model.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

import static com.mrozowski.seatreservation.domain.model.ReservationDetails.ReservationStatus.CONFIRMED

class Fixtures {

  static final String DATE_KEY = "date"
  static final String DEPARTURE_KEY = "departure"
  static final String DESTINATION_KEY = "destination"
  static final String PAGE_KEY = "page"
  static final String SIZE_KEY = "size"

  static final String DATE = "2024-01-01"
  static final String DEPARTURE = "CityA"
  static final String DESTINATION = "CityB"
  static final String PAGE_NUMBER = "1"
  static final String SIZE = "30"
  static final String TRIP_ID = "AB123T"

  static final String NOT_FOUND_ERROR = "NOT_FOUND"
  static final String SUCCESS = "SUCCESS"

  static final String REFERENCE_KEY = "reference"
  static final String CUSTOMER_NAME_KEY = "name"
  static final String REFERENCE_NUMBER = "WLCS24"
  static final String CUSTOMER_NAME = "John Doe"
  static final String SEAT_NUMBER = "C15"
  static final String SEAT_STATUS_AVAILABLE = "AVAILABLE"
  static final String RESERVATION_STATUS = CONFIRMED.toString()
  static final OffsetDateTime OFFSET_DATE_TIME = OffsetDateTime.of(2024, 5, 7, 10, 00, 00, 0, ZoneOffset.UTC)

  static final int PRICE = 100
  static final LocalDate LOCAL_DATE_VALUE = LocalDate.of(2023, 1, 1)
  static final String STRING_TOKEN = "someSessionToken123"

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

  static CancellationMessage CANCELLATION_MESSAGE = CancellationMessage.successful(Fixtures.REFERENCE_NUMBER)

  static TripSeatDetails.Seat SEAT = new TripSeatDetails.Seat(SEAT_NUMBER, TripSeatDetails.Seat.SeatStatus.AVAILABLE)
  static TripSeatDetails TRIP_SEAT_DETAILS = TripSeatDetails.of(TRIP_ID, [SEAT])
  static TemporarySessionToken TOKEN_OBJECT = new TemporarySessionToken(STRING_TOKEN, OFFSET_DATE_TIME)
}
