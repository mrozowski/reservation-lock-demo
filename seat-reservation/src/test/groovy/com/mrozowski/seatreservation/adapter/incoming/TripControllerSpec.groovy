package com.mrozowski.seatreservation.adapter.incoming

import com.mrozowski.seatreservation.domain.ReservationFacade
import com.mrozowski.seatreservation.domain.command.ResourceNotFoundException
import com.mrozowski.seatreservation.domain.command.TripFilterCommand
import com.mrozowski.seatreservation.domain.exception.SeatNotAvailableException
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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

  def "should return seats for given trip"() {
    given:
    reservationFacade.getSeatList(Fixtures.TRIP_ID) >> Fixtures.TRIP_SEAT_DETAILS

    expect:
    mockMvc.perform(get("/v1/trips/${Fixtures.TRIP_ID}/seats"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath('$.tripId').value(Fixtures.TRIP_ID))
        .andExpect(jsonPath('$.seats').isArray())
        .andExpect(jsonPath('$.seats[0].seatNumber').value(Fixtures.SEAT_NUMBER))
        .andExpect(jsonPath('$.seats[0].status').value(Fixtures.SEAT_STATUS_AVAILABLE))
  }

  def "should lock seat"() {
    given:
    1 * reservationFacade.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER) >> Fixtures.TOKEN_OBJECT

    expect:
    mockMvc.perform(post("/v1/trips/${Fixtures.TRIP_ID}/seats/${Fixtures.SEAT_NUMBER}/lock"))
        .andExpect(status().isOk())
        .andExpect(header().string("Authorization", "Basic ${Fixtures.STRING_TOKEN}"))
        .andExpect(header().string("X-Session-Expiration", Fixtures.OFFSET_DATE_TIME.toString()))
  }

  def "should return error message when lock failed"() {
    given:
    1 * reservationFacade.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER) >>
        { throw new SeatNotAvailableException(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER) }

    expect:
    mockMvc.perform(post("/v1/trips/${Fixtures.TRIP_ID}/seats/${Fixtures.SEAT_NUMBER}/lock"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath('$.error').value("BAD_REQUEST"))
        .andExpect(jsonPath('$.message').isNotEmpty())
  }

  def "should return error message when lock failed"() {
    given:
    def message = "Seat ${Fixtures.SEAT_NUMBER} for trip ${Fixtures.TRIP_ID} not found"
    1 * reservationFacade.lockSeat(Fixtures.TRIP_ID, Fixtures.SEAT_NUMBER) >> { throw new ResourceNotFoundException(message) }

    expect:
    mockMvc.perform(post("/v1/trips/${Fixtures.TRIP_ID}/seats/${Fixtures.SEAT_NUMBER}/lock"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath('$.error').value("NOT_FOUND"))
        .andExpect(jsonPath('$.message').value(message.toString()))
  }
}
