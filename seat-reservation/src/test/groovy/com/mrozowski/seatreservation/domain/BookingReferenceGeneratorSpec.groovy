package com.mrozowski.seatreservation.domain

import spock.lang.Specification
import spock.lang.Subject

class BookingReferenceGeneratorSpec extends Specification {

  @Subject
  def underTest = new BookingReferenceGenerator()

  def "should generate 6 character long reference value"(){
    when:
    def result = underTest.generate()

    then:
    noExceptionThrown()
    result.length() == 6
  }
}
