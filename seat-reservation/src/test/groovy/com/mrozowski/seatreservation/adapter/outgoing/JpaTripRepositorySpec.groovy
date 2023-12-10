package com.mrozowski.seatreservation.adapter.outgoing

import com.mrozowski.seatreservation.domain.command.TripFilterCommand
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import spock.lang.Subject

import java.util.stream.Stream

class JpaTripRepositorySpec extends Specification {
  def tripRepository = Mock(PaginationTripRepository)
  def seatRepository = Mock(CrudSeatRepository)
  def mapper = Mock(JpaTripMapper)

  @Subject
  def underTest = new JpaTripRepository(tripRepository, seatRepository, mapper)

  def "should return a list of trips based on filter criteria"() {
    given:
    def filters = [Fixtures.FILTER_DEPARTURE, Fixtures.FILTER_DATE]
    def command = TripFilterCommand.builder()
        .filters(filters)
        .page(0)
        .pageSize(10)
        .build()

    tripRepository.findAll(_, _) >> new PageImpl<TripEntity>([Fixtures.TRIP_ENTITY], PageRequest.of(0, command.pageSize()), command.pageSize())
    mapper.toTrip(Fixtures.TRIP_ENTITY) >> Fixtures.TRIP

    when:
    def result = underTest.getTripList(command)

    then:
    result.content.size() == 1
    with(result.content.first()) {
      it.departure() == Fixtures.DEPARTURE
      it.destination() == Fixtures.DESTINATION
    }
  }

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
}
