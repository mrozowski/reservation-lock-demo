package com.mrozowski.seatreservation.adapter.outgoing

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.mrozowski.seatreservation.domain.model.TripSeatDetails.Seat

class JpaTripMapperSpec extends Specification {

  @Subject
  def underTest = new JpaTripMapper()

  def "should map TripEntity to Trip"() {
    when:
    def result = underTest.toTrip(Fixtures.TRIP_ENTITY)

    then:
    result.id() == Fixtures.TRIP_ID
    result.departure() == Fixtures.DEPARTURE
    result.destination() == Fixtures.DESTINATION
    result.price() == Fixtures.PRICE
    result.date() == Fixtures.DATETIME
  }

  @Unroll
  def "should map SeatEntity with #status and #seatNumber to #expectedResult"() {
    expect:
    def seatEntity = new SeatEntity(id: 1L, seatNumber: seatNumber, status: status, lockExpirationTime: null)
    def result = underTest.toSeat(seatEntity)
    result == expectedResult

    where:
    status                                | seatNumber | expectedResult
    SeatEntity.SeatEntityStatus.AVAILABLE | "A15"      | new Seat(seatNumber, Seat.SeatStatus.AVAILABLE)
    SeatEntity.SeatEntityStatus.LOCKED    | "A15"      | new Seat(seatNumber, Seat.SeatStatus.RESERVED)
    SeatEntity.SeatEntityStatus.RESERVED  | "A15"      | new Seat(seatNumber, Seat.SeatStatus.RESERVED)
  }
}
