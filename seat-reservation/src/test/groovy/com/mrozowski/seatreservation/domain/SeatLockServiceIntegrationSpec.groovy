package com.mrozowski.seatreservation.domain

import com.mrozowski.seatreservation.SpringIntegrationSpecBase
import com.mrozowski.seatreservation.domain.model.TemporarySessionToken
import com.mrozowski.seatreservation.domain.model.TripSeatDetails.Seat
import com.mrozowski.seatreservation.domain.port.SeatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.util.concurrent.PollingConditions

import java.time.OffsetDateTime

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(
    webEnvironment = RANDOM_PORT,
    properties = ["reservation.seat-lock.scheduler=0/3 * * * * ?"]
)
@DirtiesContext
class SeatLockServiceIntegrationSpec extends SpringIntegrationSpecBase {

  @Autowired
  private SeatRepository seatRepository

  def "should release expired locks"() {
    given:
    def tripId = "TO140123G"
    def seatNumber = "20F"
    def expiredDate = OffsetDateTime.now().minusMinutes(1)
    def temporarySessionToken = new TemporarySessionToken(Fixtures.SESSION_TOKEN, expiredDate)
    def conditions = new PollingConditions(timeout: 10, initialDelay: 2, factor: 1.3)

    when:
    seatRepository.lockSeat(tripId, seatNumber, temporarySessionToken)

    then:
    conditions.eventually {
      def seat = getSeat(tripId, seatNumber)
      assert seat.status() == Seat.SeatStatus.AVAILABLE
    }
  }

  private Seat getSeat(String tripId, String seatNumber){
    return seatRepository.getSeatList(tripId).seats().stream()
        .filter { e -> (e.seatNumber() == seatNumber) }
        .findFirst()
        .orElseThrow()
  }
}
