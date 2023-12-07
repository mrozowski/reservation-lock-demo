package com.mrozowski.seatreservation.adapter.incoming

import com.mrozowski.seatreservation.domain.ReservationFacade
import com.mrozowski.seatreservation.domain.command.TripFilterCommand
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TripControllerSpec extends Specification {

  private MockMvc mockMvc
  def dateParser = Mock(DateParser)
  def reservationFacade = Mock(ReservationFacade)
  def underTest = new TripController(dateParser, reservationFacade)

  def setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(underTest).build()
  }

  def "should return trips with given filters"() {
    given:
    dateParser.toLocalDate(_ as String) >> Fixtures.LOCAL_DATE_VALUE
    reservationFacade.getTripList(_ as TripFilterCommand) >> Fixtures.TRIP_PAGE

    expect:
    mockMvc.perform(get("/v1/trips/search")
        .param(Fixtures.DATE_KEY, Fixtures.DATE)
        .param(Fixtures.DEPARTURE_KEY, Fixtures.DEPARTURE)
        .param(Fixtures.DESTINATION_KEY, Fixtures.DESTINATION)
        .param(Fixtures.PAGE_KEY, Fixtures.PAGE_NUMBER)
        .param(Fixtures.SIZE_KEY, Fixtures.SIZE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
  }
}
