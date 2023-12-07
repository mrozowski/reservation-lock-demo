package com.mrozowski.seatreservation.adapter.outgoing

import com.mrozowski.seatreservation.domain.model.ReservationDetails
import spock.lang.Specification
import spock.lang.Subject

import java.time.OffsetDateTime

class JpaReservationRepositorySpec extends Specification {

  def repository = Mock(CrudReservationRepository)
  @Subject
  def underTest = new JpaReservationRepository(repository)

  def "should call crud repository and return reservation details"() {
    given:
    def reference = "someReference"
    def name = "someName"
    def date = OffsetDateTime.now()
    def seatNumber = "A15"
    def departure = "departureA"
    def destination = "destinationB"
    def entity = new ReservationEntity(
        id: 1L,
        createdAt: date,
        customerName: name,
        reference: reference,
        paymentStatus: "CONFIRMED",
        trip: new TripEntity(id: "ABC123", departure: departure, destination: destination, date: date),
        seat: new SeatEntity(id: 1L, trip: new TripEntity(), seatNumber: seatNumber, status: "RESERVED")
    )

    repository.findFirstByReferenceAndCustomerName(reference, name) >> Optional.of(entity)

    when:
    def result = underTest.getReservationDetails(reference, name)

    then:
    result.isPresent()
    with(result.get() as ReservationDetails) {
      it.reference() == reference
      it.seatNumber() == seatNumber
      it.customerName() == name
      it.destination() == destination
      it.departure() == departure
    }
  }
}
