package com.mrozowski.seatreservation.adapter.outgoing

import com.mrozowski.seatreservation.domain.command.TripFilterCommand
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import spock.lang.Subject

class JpaTripRepositorySpec extends Specification {
  def tripRepository = Mock(PaginationTripRepository)
  def mapper = Mock(JpaEntityMapper)

  @Subject
  def underTest = new JpaTripRepository(tripRepository, mapper)

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

  def "should get trip by id"(){
    given:
    1 * tripRepository.findFirstById(Fixtures.TRIP_ID) >> Optional.of(Fixtures.TRIP_ENTITY)
    1 * mapper.toTrip(Fixtures.TRIP_ENTITY) >> Fixtures.TRIP

    when:
    def result = underTest.getTripById(Fixtures.TRIP_ID)

    then:
    noExceptionThrown()
    result == Fixtures.TRIP
  }
}
