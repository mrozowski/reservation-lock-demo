package com.mrozowski.seatreservation.domain

import com.mrozowski.seatreservation.infrastructure.ReservationSeatLockProperties
import spock.lang.Specification
import spock.lang.Subject

import java.time.Duration
import java.time.OffsetDateTime

class BasicTokenGeneratorSpec extends Specification {

  def duration = Duration.ofMinutes(20)
  def properties = new ReservationSeatLockProperties(duration)

  @Subject
  def underTest = new BasicTokenGenerator(properties)

  def "should generate token"() {
    when:
    def result = underTest.generate()

    then:
    result.sessionToken() ==~ /Basic [a-zA-Z0-9-]+/
    result.expirationDateTime().isAfter(OffsetDateTime.now().plusMinutes(18))
    result.expirationDateTime().isBefore(OffsetDateTime.now().plusMinutes(21))
  }
}
