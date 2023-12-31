package com.mrozowski.seatreservation.adapter.outgoing

import com.mrozowski.seatreservation.domain.command.FilterCriteria
import com.mrozowski.seatreservation.domain.command.PaymentConfirmationCommand
import com.mrozowski.seatreservation.domain.model.Trip
import com.mrozowski.seatreservation.domain.model.TripSeatDetails
import com.mrozowski.seatreservation.domain.model.UserSeatSessionTokenConfirmation

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

class Fixtures {

  static final OffsetDateTime DATETIME = OffsetDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
  static final LocalDate LOCAL_DATE = LocalDate.of(2024, 1, 1)
  static final int PRICE = 100
  static final String DESTINATION = "CityB"
  static final String DEPARTURE = "CityA"
  static final String TRIP_ID = "WL1234"

  static final long SEAT_ID = 1L
  static final String SEAT_NUMBER = "A15"
  static final String SEAT_ENTITY_STATUS_AVAILABLE = SeatEntity.SeatEntityStatus.AVAILABLE
  static final String SEAT_ENTITY_STATUS_LOCKED = SeatEntity.SeatEntityStatus.LOCKED
  static final String SEAT_ENTITY_STATUS_RESERVED = SeatEntity.SeatEntityStatus.RESERVED
  static final String SESSION_TOKEN = "Basic 63fc4f00-2538-42b7-b4eb-e61c9acc751f"
  static final String PAYMENT_ID = "e0cadd1c-0cdf-4320-abe6-c2a5f6cbdedf"
  static final String RESERVATION_ID = "1"

  static final TripEntity TRIP_ENTITY = new TripEntity(id: TRIP_ID, departure: DEPARTURE, destination: DESTINATION, price: PRICE, date: DATETIME)
  static final Trip TRIP = new Trip(TRIP_ID, DEPARTURE, DESTINATION, DATETIME, PRICE)

  static final SeatEntity SEAT_ENTITY_AVAILABLE = new SeatEntity(id: SEAT_ID, seatNumber: SEAT_NUMBER, status: SEAT_ENTITY_STATUS_AVAILABLE, lockExpirationTime: null)
  static final SeatEntity SEAT_ENTITY_RESERVED = new SeatEntity(id: SEAT_ID, seatNumber: SEAT_NUMBER, status: SEAT_ENTITY_STATUS_RESERVED, lockExpirationTime: null)
  static final SeatEntity SEAT_ENTITY_LOCKED = new SeatEntity(id: SEAT_ID, seatNumber: SEAT_NUMBER, status: SEAT_ENTITY_STATUS_LOCKED, lockExpirationTime: OffsetDateTime.now().plusMinutes(20), lockSessionToken: SESSION_TOKEN)
  static final TripSeatDetails.Seat SEAT_AVAILABLE = new TripSeatDetails.Seat(SEAT_NUMBER, TripSeatDetails.Seat.SeatStatus.AVAILABLE)

  static final FilterCriteria FILTER_DEPARTURE = new FilterCriteria("departure", "CityA", FilterCriteria.FilterOperation.EQUAL)
  static final FilterCriteria FILTER_DATE = new FilterCriteria("date", LOCAL_DATE, FilterCriteria.FilterOperation.EQUAL)
  static final UserSeatSessionTokenConfirmation USER_SESSION_TOKEN_CONFIRMATION = UserSeatSessionTokenConfirmation.valid(Fixtures.SEAT_ID)
  static final PaymentConfirmationCommand PAYMENT_CONFIRMATION_COMMAND = PaymentConfirmationCommand.success(PAYMENT_ID, RESERVATION_ID, PRICE)
  static final ReservationEntity.PaymentStatus RESERVATION_CONFIRMED_STATUS = ReservationEntity.PaymentStatus.CONFIRMED
}
