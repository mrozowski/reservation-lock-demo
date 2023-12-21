package com.mrozowski.seatreservation.adapter.outgoing

import com.mrozowski.seatreservation.domain.model.ReservationDetails
import jakarta.persistence.EntityManager
import org.hibernate.HibernateException
import spock.lang.Specification
import spock.lang.Subject

import java.time.OffsetDateTime

class JpaReservationRepositorySpec extends Specification {

  def repository = Mock(CrudReservationRepository)
  def entityManager = Mock(EntityManager)

  @Subject
  def underTest = new JpaReservationRepository(repository, entityManager)

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
        seat: new SeatEntity(id: 1L, tripId: "tripId", seatNumber: seatNumber, status: "RESERVED")
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

  def "should cancel reservation"() {
    given:
    def reference = "someReference"
    def name = "someName"
    repository.cancelReservation(reference, name) >> 1

    when:
    def result = underTest.cancelReservation(reference, name)

    then:
    result.status().toString() == "SUCCESS"
  }

  def "should cancel reservation"() {
    given:
    def reference = "someReference"
    def name = "someName"
    repository.cancelReservation(reference, name) >> 0

    when:
    def result = underTest.cancelReservation(reference, name)

    then:
    result.status().toString() == "NOT_FOUND"
  }

  def "should cancel reservation"() {
    given:
    def reference = "someReference"
    def name = "someName"
    repository.cancelReservation(reference, name) >> { throw new HibernateException("Some error", new Throwable()) }

    when:
    def result = underTest.cancelReservation(reference, name)

    then:
    result.status().toString() == "ERROR"
  }

  def "should update payment status"() {
    given:
    1 * repository.updatePaymentStatus(Fixtures.RESERVATION_ID, Fixtures.RESERVATION_CONFIRMED_STATUS) >> 1

    when:
    underTest.updatePayment(Fixtures.PAYMENT_CONFIRMATION_COMMAND)

    then:
    noExceptionThrown()
  }
}
