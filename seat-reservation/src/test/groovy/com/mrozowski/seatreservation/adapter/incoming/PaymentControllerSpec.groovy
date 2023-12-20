package com.mrozowski.seatreservation.adapter.incoming

import com.mrozowski.seatreservation.domain.ReservationFacade
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class PaymentControllerSpec extends Specification {

  private MockMvc mockMvc
  private ReservationFacade reservationFacade = Mock()
  def underTest = new PaymentController(reservationFacade)

  def setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(underTest).build()
  }

  def "should create intent for payment"() {
    given:
    var jsonBody = getClass().getResourceAsStream("/json/stripIntentExample.json").text
    reservationFacade.initializePayment(Fixtures.INIT_PAYMENT_COMMAND) >> Fixtures.PAYMENT_INTENT_DETAILS

    expect:
    mockMvc.perform(post("/v1/payment/stripe/create-intent")
        .content(jsonBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.clientSecret').isNotEmpty())
  }
}
