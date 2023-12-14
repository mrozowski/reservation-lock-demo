package com.mrozowski.seatreservation.adapter.outgoing

import com.mrozowski.seatreservation.domain.command.ResourceNotFoundException
import com.mrozowski.seatreservation.domain.exception.SeatNotAvailableException
import com.mrozowski.seatreservation.domain.model.TemporarySessionToken
import spock.lang.Specification
import spock.lang.Subject

import java.util.stream.Stream

class JpaSeatRepositorySpec extends Specification {

  def seatRepository = Mock(CrudSeatRepository)
  def mapper = Mock(JpaEntityMapper)

  @Subject
  def underTest = new JpaSeatRepository(seatRepository, mapper)

  def "should return seat details based on tripId"() {
    given:
    seatRepository.findAllByTripId(Fixtures.TRIP_ID) >> Stream.of(Fixtures.SEAT_ENTITY_AVAILABLE)
    mapper.toSeat(Fixtures.SEAT_ENTITY_AVAILABLE) >> Fixtures.SEAT_AVAILABLE

    when:
    def result = underTest.getSeatList(Fixtures.TRIP_ID)

    then:
    result.tripId() == Fixtures.TRIP_ID
    result.seats() == [Fixtures.SEAT_AVAILABLE]
  }

  def "should lock seat when seat is available"() {
    given:
    def token = new TemporarySessionToken(Fixtures.SESSION_TOKEN, Fixtures.DATETIME)
    1 * seatRepository.findFirstByTripIdAndSeatNumber(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER) >> Optional.of(Fixtures.SEAT_ENTITY_AVAILABLE)
    1 * seatRepository.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, Fixtures.SESSION_TOKEN, Fixtures.DATETIME) >> 1

    when:
    underTest.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, token)

    then:
    noExceptionThrown()
  }

  def "should return exception when seat is not available"() {
    given:
    def token = new TemporarySessionToken(Fixtures.SESSION_TOKEN, Fixtures.DATETIME)
    1 * seatRepository.findFirstByTripIdAndSeatNumber(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER) >> Optional.of(Fixtures.SEAT_ENTITY_RESERVED)
    0 * seatRepository.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, Fixtures.SESSION_TOKEN, Fixtures.DATETIME)

    when:
    underTest.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, token)

    then:
    thrown SeatNotAvailableException
  }

  def "should return exception when seat is not found"() {
    given:
    def token = new TemporarySessionToken(Fixtures.SESSION_TOKEN, Fixtures.DATETIME)
    1 * seatRepository.findFirstByTripIdAndSeatNumber(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER) >> Optional.empty()
    0 * seatRepository.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, Fixtures.SESSION_TOKEN, Fixtures.DATETIME)

    when:
    underTest.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, token)

    then:
    thrown ResourceNotFoundException
  }

  def "should confirm seat is Locked by valid SessionToken" (){
    given:
    1 * seatRepository.findFirstByTripIdAndSeatNumber(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER) >> Optional.of(Fixtures.SEAT_ENTITY_LOCKED)

    when:
    def result = underTest.confirmUserLockSeatSessionToken(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER, Fixtures.SESSION_TOKEN)

    then:
    noExceptionThrown()
    result == Fixtures.USER_SESSION_TOKEN_CONFIRMATION
  }
}
