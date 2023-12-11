package com.mrozowski.seatreservation.domain


import com.mrozowski.seatreservation.domain.port.ReservationRepository
import spock.lang.Specification
import spock.lang.Subject

class ReservationServiceSpec extends Specification {

  def reservationRepository = Mock(ReservationRepository)

  @Subject
  def underTest = new ReservationService(reservationRepository)

  def "should return reservation details"() {
    given:
    reservationRepository.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME) >> Optional.of(Fixtures.RESERVATION_DETAILS)

    when:
    def result = underTest.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME)

    then:
    result.isPresent()
    result.get() == Fixtures.RESERVATION_DETAILS
  }

  def "should cancel reservation"() {
    given:
    reservationRepository.cancelReservation(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME) >> Fixtures.CANCELLATION_MESSAGE

    when:
    def result = underTest.cancelReservation(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME)

    then:
    result == Fixtures.CANCELLATION_MESSAGE
  }
}
