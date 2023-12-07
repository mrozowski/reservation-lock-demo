package com.mrozowski.seatreservation.domain

import com.mrozowski.seatreservation.domain.command.TripFilterCommand
import com.mrozowski.seatreservation.domain.port.ReservationRepository
import com.mrozowski.seatreservation.domain.port.TripRepository
import spock.lang.Specification
import spock.lang.Subject

class ReservationFacadeSpec extends Specification {

  def tripRepository = Mock(TripRepository)
  def reservationRepository = Mock(ReservationRepository)
  @Subject
  def underTest = new ReservationFacade(tripRepository, reservationRepository)

  def "should call repository and return list of trips"() {
    given:
    def command = TripFilterCommand.builder()
        .page(Fixtures.PAGE_NUMBER)
        .pageSize(Fixtures.SIZE)
        .filters([])
        .build()
    tripRepository.getTripList(command) >> Fixtures.TRIP_PAGE

    when:
    def result = underTest.getTripList(command)

    then:
    result == Fixtures.TRIP_PAGE
  }

  def "should return reservation details"() {
    given:
    reservationRepository.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME) >> Optional.of(Fixtures.RESERVATION_DETAILS)

    when:
    def result = underTest.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME)

    then:
    result.isPresent()
    result.get() == Fixtures.RESERVATION_DETAILS
  }
}
