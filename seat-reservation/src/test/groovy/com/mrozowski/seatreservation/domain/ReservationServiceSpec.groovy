package com.mrozowski.seatreservation.domain

import com.mrozowski.seatreservation.domain.exception.SessionExpiredException
import com.mrozowski.seatreservation.domain.model.UserSeatSessionTokenConfirmation
import com.mrozowski.seatreservation.domain.port.ReservationRepository
import com.mrozowski.seatreservation.domain.port.SeatRepository
import com.mrozowski.seatreservation.domain.port.TripRepository
import spock.lang.Specification
import spock.lang.Subject

class ReservationServiceSpec extends Specification {

  def seatRepository = Mock(SeatRepository)
  def tripRepository = Mock(TripRepository)
  def reservationRepository = Mock(ReservationRepository)
  def referenceGenerator = Mock(BookingReferenceGenerator)

  @Subject
  def underTest = new ReservationService(reservationRepository, tripRepository, seatRepository, referenceGenerator)

  def "should return reservation details"() {
    given:
    reservationRepository.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_FULL_NAME) >> Optional.of(Fixtures.RESERVATION_DETAILS)

    when:
    def result = underTest.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_FULL_NAME)

    then:
    result.isPresent()
    result.get() == Fixtures.RESERVATION_DETAILS
  }

  def "should cancel reservation"() {
    given:
    reservationRepository.cancelReservation(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_FULL_NAME) >> Fixtures.CANCELLATION_MESSAGE

    when:
    def result = underTest.cancelReservation(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_FULL_NAME)

    then:
    result == Fixtures.CANCELLATION_MESSAGE
  }

  def "should process reservation"() {
    given:
    def tokenConfirmation = UserSeatSessionTokenConfirmation.valid(Fixtures.SEAT_ID)
    1 * seatRepository.confirmUserLockSeatSessionToken(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, Fixtures.SESSION_TOKEN)
        >> tokenConfirmation
    1 * tripRepository.getTripById(Fixtures.TRIP_ID) >> Fixtures.TRIPS[0]
    1 * referenceGenerator.generate() >> Fixtures.REFERENCE_NUMBER
    1 * reservationRepository.save(Fixtures.RESERVATION_REQUEST_COMMAND, Fixtures.REFERENCE_NUMBER, tokenConfirmation.seatId(), Fixtures.PRICE)

    when:
    def result = underTest.process(Fixtures.RESERVATION_REQUEST_COMMAND)

    then:
    noExceptionThrown()
    result == Fixtures.RESERVATION_CONFIRMATION
  }

  def "should return session expired error when process reservation"() {
    given:
    def tokenExpired = UserSeatSessionTokenConfirmation.expired(Fixtures.SEAT_ID)
    1 * seatRepository.confirmUserLockSeatSessionToken(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, Fixtures.SESSION_TOKEN)
        >> tokenExpired
    0 * referenceGenerator.generate()
    0 * reservationRepository.save(Fixtures.RESERVATION_REQUEST_COMMAND, Fixtures.REFERENCE_NUMBER, tokenExpired.seatId(), Fixtures.PRICE)

    when:
    def result = underTest.process(Fixtures.RESERVATION_REQUEST_COMMAND)

    then:
    thrown SessionExpiredException
  }
}
