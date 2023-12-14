package com.mrozowski.seatreservation.adapter.incoming

import com.mrozowski.seatreservation.domain.ReservationFacade
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.format.DateTimeFormatter

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ReservationControllerSpec extends Specification {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
  private MockMvc mockMvc
  private ReservationFacade reservationFacade = Mock()
  def underTest = new ReservationController(reservationFacade)

  def setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(underTest).build()
  }

  def "should return reservation details"() {
    given:
    reservationFacade.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_FULL_NAME) >> Optional.of(Fixtures.RESERVATION_DETAILS)

    expect:
    mockMvc.perform(get("/v1/reservations/details")
        .param(Fixtures.REFERENCE_KEY, Fixtures.REFERENCE_NUMBER)
        .param(Fixtures.CUSTOMER_NAME_KEY, Fixtures.CUSTOMER_FULL_NAME))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.reference').value(Fixtures.REFERENCE_NUMBER))
        .andExpect(jsonPath('$.departure').value(Fixtures.DEPARTURE))
        .andExpect(jsonPath('$.destination').value(Fixtures.DESTINATION))
        .andExpect(jsonPath('$.offsetDateTime').value(Fixtures.OFFSET_DATE_TIME.format(FORMATTER)))
        .andExpect(jsonPath('$.seatNumber').value(Fixtures.SEAT_NUMBER))
        .andExpect(jsonPath('$.customerName').value(Fixtures.CUSTOMER_FULL_NAME))
        .andExpect(jsonPath('$.status').value(Fixtures.RESERVATION_STATUS))
  }

  def "should return error when reservation details not found"() {
    given:
    reservationFacade.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_FULL_NAME) >> Optional.empty()

    expect:
    mockMvc.perform(get("/v1/reservations/details")
        .param(Fixtures.REFERENCE_KEY, Fixtures.REFERENCE_NUMBER)
        .param(Fixtures.CUSTOMER_NAME_KEY, Fixtures.CUSTOMER_FULL_NAME))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.status').value(Fixtures.NOT_FOUND_ERROR))
        .andExpect(jsonPath('$.error').value(Fixtures.NOT_FOUND_ERROR))
        .andExpect(jsonPath('$.message').isNotEmpty())
        .andExpect(jsonPath('$.timestamp').isNotEmpty())
        .andExpect(jsonPath('$.path').value("/v1/reservations/details"))
  }

  def "should cancel reservation and return CancellationMessage"() {
    given:
    reservationFacade.cancelReservation(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_FULL_NAME) >> Fixtures.CANCELLATION_MESSAGE

    expect:
    mockMvc.perform(delete("/v1/reservations/cancel")
        .param(Fixtures.REFERENCE_KEY, Fixtures.REFERENCE_NUMBER)
        .param(Fixtures.CUSTOMER_NAME_KEY, Fixtures.CUSTOMER_FULL_NAME))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.status').value(Fixtures.SUCCESS))
        .andExpect(jsonPath('$.message').isNotEmpty())
  }

  def "should process reservation and return details"() {
    given:
    var jsonBody = getClass().getResourceAsStream("/json/reservation.json").text
    reservationFacade.process(Fixtures.RESERVATION_REQUEST_COMMAND) >> Fixtures.RESERVATION_CONFIRMATION

    expect:
    mockMvc.perform(post("/v1/reservations/process")
        .content(jsonBody)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", Fixtures.SESSION_TOKEN))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.price').isNotEmpty())
        .andExpect(jsonPath('$.reference').isNotEmpty())
  }
}
