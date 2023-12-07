package com.mrozowski.seatreservation.adapter.outgoing

import com.mrozowski.seatreservation.domain.command.FilterCriteria
import com.mrozowski.seatreservation.domain.command.TripFilterCommand
import com.mrozowski.seatreservation.domain.model.Trip
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.OffsetDateTime

import static com.mrozowski.seatreservation.domain.command.FilterCriteria.FilterOperation.EQUAL

class JpaTripRepositorySpec extends Specification {
  def tripRepository = Mock(PaginationTripRepository)
  def mapper = Mock(JpaTripMapper)

  @Subject
  def underTest = new JpaTripRepository(tripRepository, mapper)

  def "should return a list of trips based on filter criteria"() {
    given:
    def filters = [new FilterCriteria("departure", "CityA", EQUAL),
                   new FilterCriteria("date", LocalDate.now(), EQUAL)]
    def pageRequest = PageRequest.of(0, 10, Sort.by("date"))
    def command = TripFilterCommand.builder()
        .filters(filters)
        .page(0)
        .pageSize(10)
        .build()

    def tripEntity = new TripEntity(
        id: "1",
        departure: "CityA",
        destination: "CityB",
        date: OffsetDateTime.now(),
        price: 100
    )

    def trip = new Trip("1", "CityA", "CityB", OffsetDateTime.now(), 100)
    tripRepository.findAll(_, _) >> new PageImpl<TripEntity>([tripEntity], PageRequest.of(0, command.pageSize()), command.pageSize())
    mapper.toTrip(tripEntity) >> trip

    when:
    def result = underTest.getTripList(command)

    then:
    result.content.size() == 1
    with(result.content.first()) {
      it.departure() == "CityA"
      it.destination() == "CityB"
    }
  }
}
