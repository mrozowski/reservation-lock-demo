package com.mrozowski.seatreservation.domain

import com.mrozowski.seatreservation.domain.command.TripFilterCommand
import spock.lang.Specification
import spock.lang.Subject

class ReservationFacadeSpec extends Specification {

  def tripService = Mock(TripService)
  def reservationService = Mock(ReservationService)
  @Subject
  def underTest = new ReservationFacade(tripService, reservationService)

  def "should call repository and return list of trips"() {
    given:
    def command = TripFilterCommand.builder()
        .page(Fixtures.PAGE_NUMBER)
        .pageSize(Fixtures.SIZE)
        .filters([])
        .build()
    tripService.getTripList(command) >> Fixtures.TRIP_PAGE

    when:
    def result = underTest.getTripList(command)

    then:
    result == Fixtures.TRIP_PAGE
  }

  def "should return reservation details"() {
    given:
    reservationService.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME) >> Optional.of(Fixtures.RESERVATION_DETAILS)

    when:
    def result = underTest.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME)

    then:
    result.isPresent()
    result.get() == Fixtures.RESERVATION_DETAILS
  }

  def "should cancel reservation"() {
    given:
    reservationService.cancelReservation(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME) >> Fixtures.CANCELLATION_MESSAGE

    when:
    def result = underTest.cancelReservation(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME)

    then:
    result == Fixtures.CANCELLATION_MESSAGE
  }
}
