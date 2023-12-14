package com.mrozowski.seatreservation.domain

import com.mrozowski.seatreservation.domain.model.*
import com.mrozowski.seatreservation.domain.model.TripSeatDetails.Seat
import com.mrozowski.seatreservation.domain.model.TripSeatDetails.Seat.SeatStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

import java.time.OffsetDateTime
import java.time.ZoneOffset

class Fixtures {

  static final String DATE = "2024-01-01"
  static final String DEPARTURE = "CityA"
  static final String DESTINATION = "CityB"
  static final int PAGE_NUMBER = 1
  static final int SIZE = 30
  static final String TRIP_ID = "AB123T"
  static final int PRICE = 100
  static final String REFERENCE_NUMBER = "WLCS24"
  static final String CUSTOMER_FULL_NAME = "John Doe"
  static final String SEAT_NUMBER = "C15"
  static final long SEAT_ID = 10L
  static final String SESSION_TOKEN = "Basic session-token-0"
  static final ReservationDetails.ReservationStatus RESERVATION_CONFIRMED = ReservationDetails.ReservationStatus.CONFIRMED
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
      .customerName(CUSTOMER_FULL_NAME)
      .offsetDateTime(OFFSET_DATE_TIME)
      .status(RESERVATION_CONFIRMED)
      .build()

  static final String CUSTOMER_NAME = "Joe"
  static final String CUSTOMER_SURNAME = "Doe"
  static final String CUSTOMER_PHONE = "700123567"
  static final String CUSTOMER_EMAIL = "Doe@gmail.com"
  static final ReservationConfirmation RESERVATION_CONFIRMATION = ReservationConfirmation.of(REFERENCE_NUMBER, PRICE)
  static final ReservationRequestCommand RESERVATION_REQUEST_COMMAND = ReservationRequestCommand.builder()
      .name(CUSTOMER_NAME)
      .surname(CUSTOMER_SURNAME)
      .phone(CUSTOMER_PHONE)
      .email(CUSTOMER_EMAIL)
      .tripId(TRIP_ID)
      .seatNumber(SEAT_NUMBER)
      .price(PRICE)
      .token(SESSION_TOKEN)
      .build()

  static CancellationMessage CANCELLATION_MESSAGE = CancellationMessage.successful(Fixtures.REFERENCE_NUMBER)
  static SeatStatus SEAT_STATUS_AVAILABLE = SeatStatus.AVAILABLE
  static Seat SEAT_AVAILABLE = new Seat(SEAT_NUMBER, SEAT_STATUS_AVAILABLE)
  static TripSeatDetails TRIP_SEAT_DETAILS = TripSeatDetails.of(TRIP_ID, [SEAT_AVAILABLE])
  static TemporarySessionToken TEMPORARY_SESSION_TOKEN = new TemporarySessionToken(SESSION_TOKEN, OFFSET_DATE_TIME)
}
