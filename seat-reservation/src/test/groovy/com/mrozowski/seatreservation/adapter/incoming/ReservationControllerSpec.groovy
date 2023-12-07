package com.mrozowski.seatreservation.adapter.incoming

import com.mrozowski.seatreservation.domain.ReservationFacade
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.format.DateTimeFormatter

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ReservationControllerSpec extends Specification {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
  private MockMvc mockMvc
  def reservationFacade = Mock(ReservationFacade)
  def underTest = new ReservationController(reservationFacade)

  def setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(underTest).build()
  }

  def "should return reservation details"() {
    given:
    reservationFacade.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME) >> Optional.of(Fixtures.RESERVATION_DETAILS)

    expect:
    mockMvc.perform(get("/v1/reservations/details")
        .param(Fixtures.REFERENCE_KEY, Fixtures.REFERENCE_NUMBER)
        .param(Fixtures.CUSTOMER_NAME_KEY, Fixtures.CUSTOMER_NAME))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.reference').value(Fixtures.REFERENCE_NUMBER))
        .andExpect(jsonPath('$.departure').value(Fixtures.DEPARTURE))
        .andExpect(jsonPath('$.destination').value(Fixtures.DESTINATION))
        .andExpect(jsonPath('$.offsetDateTime').value(Fixtures.OFFSET_DATE_TIME.format(FORMATTER)))
        .andExpect(jsonPath('$.seatNumber').value(Fixtures.SEAT_NUMBER))
        .andExpect(jsonPath('$.customerName').value(Fixtures.CUSTOMER_NAME))
        .andExpect(jsonPath('$.status').value(Fixtures.RESERVATION_STATUS))
  }

  def "should return error when reservation details not found"() {
    given:
    reservationFacade.getReservationDetails(Fixtures.REFERENCE_NUMBER, Fixtures.CUSTOMER_NAME) >> Optional.empty()

    expect:
    mockMvc.perform(get("/v1/reservations/details")
        .param(Fixtures.REFERENCE_KEY, Fixtures.REFERENCE_NUMBER)
        .param(Fixtures.CUSTOMER_NAME_KEY, Fixtures.CUSTOMER_NAME))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.status').value(Fixtures.NOT_FOUND_ERROR))
        .andExpect(jsonPath('$.error').value(Fixtures.NOT_FOUND_ERROR))
        .andExpect(jsonPath('$.message').isNotEmpty())
        .andExpect(jsonPath('$.timestamp').isNotEmpty())
        .andExpect(jsonPath('$.path').value("/v1/reservations/details"))
  }
}
