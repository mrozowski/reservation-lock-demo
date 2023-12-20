package com.mrozowski.seatreservation.domain

import spock.lang.Specification
import spock.lang.Subject

class PaymentServiceSpec extends Specification {

  def stripePayment = Mock(StripePayment)

  @Subject
  def underTest = new PaymentService()
}
