package com.mrozowski.seatreservation.domain

import com.mrozowski.seatreservation.domain.command.TripFilterCommand
import com.mrozowski.seatreservation.domain.model.TemporarySessionToken
import com.mrozowski.seatreservation.domain.port.SeatRepository
import com.mrozowski.seatreservation.domain.port.TripRepository
import spock.lang.Specification
import spock.lang.Subject

class TripServiceSpec extends Specification {

  def tripRepository = Mock(TripRepository)
  def seatRepository = Mock(SeatRepository)
  def tokenGenerator = Mock(TokenGenerator)

  @Subject
  def underTest = new TripService(tripRepository, seatRepository, tokenGenerator)

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

  def "should return list of seat"() {
    given:
    1 * seatRepository.getSeatList(Fixtures.TRIP_ID) >> Fixtures.TRIP_SEAT_DETAILS

    when:
    def result = underTest.getSeatList(Fixtures.TRIP_ID)

    then:
    result == Fixtures.TRIP_SEAT_DETAILS
  }

  def "should lock seat and return new session token"() {
    given:
    def sessionToken = "some-session-token-0"
    def token = new TemporarySessionToken(sessionToken, Fixtures.OFFSET_DATE_TIME)
    1 * tokenGenerator.generate() >> token
    1 * seatRepository.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, token)

    when:
    def result = underTest.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER)

    then:
    result == token
  }
}
