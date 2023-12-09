package com.mrozowski.seatreservation.adapter.outgoing

import spock.lang.Specification
import spock.lang.Subject

import java.time.OffsetDateTime

class JpaTripMapperSpec extends Specification {

  @Subject
  def underTest = new JpaTripMapper()

  def "should map TripEntity to Trip"() {
    def datetime = OffsetDateTime.now()
    def price = 100
    def destination = "CityB"
    def departure = "CityA"
    def id = "someId"
    given:
    def entity = new TripEntity(id: id, departure: departure, destination: destination, price: price, date: datetime)

    when:
    def result = underTest.toTrip(entity)

    then:
    result.id() == id
    result.departure() == departure
    result.destination() == destination
    result.price() == price
    result.date() == datetime
  }
}
