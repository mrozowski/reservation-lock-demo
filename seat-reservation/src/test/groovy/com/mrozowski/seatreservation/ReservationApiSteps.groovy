package com.mrozowski.seatreservation

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.apache.commons.lang3.StringUtils

import static com.mrozowski.seatreservation.RestApiClient.HTTPHeader
import static com.mrozowski.seatreservation.RestApiClient.RequestMethod.*
import static com.mrozowski.seatreservation.RestApiClient.newConnection

@Slf4j
@CucumberContextConfiguration
class ReservationApiSteps extends SpringIntegrationSpecBase {

  private static final String RESERVATION_DETAILS_RESPONSE_KEY = "reservationDetails"
  private static final String CANCEL_RESERVATION_RESPONSE_KEY = "cancellationResponse"
  private static final String REFERENCE_NUMBER_KEY = "referenceNumber"
  private static final String SESSION_TOKEN = "sessionToken"
  private static final String TRIP_ID_KEY = "tripId"
  private static final String SEAT_NUMBER_KEY = "seatNumber"
  private static final String RESERVATION_ID = "reservationId"
  public static final String PRICE = "price"
  private ScenarioContext scenarioContext = new ScenarioContext()

  @Before
  void beforeScenario() {
    scenarioContext = new ScenarioContext()
  }

  @When("the user ask for reservation details using reference number {string} and name {string}")
  def userAskForReservationDetails(String reference, String clientName) {
    def encodedClientName = URLEncoder.encode(clientName, "UTF-8")
    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/reservations/details?reference=${reference}&name=${encodedClientName}")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(RESERVATION_DETAILS_RESPONSE_KEY, restApiClient.responseAsText)
      scenarioContext.put(REFERENCE_NUMBER_KEY, reference)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the response should have reservation details")
  def theResponseShouldHaveAvailableTrips() {
    def jsonBodyResponse = scenarioContext.get(RESERVATION_DETAILS_RESPONSE_KEY) as String
    def reference = scenarioContext.get(REFERENCE_NUMBER_KEY) as String
    log.info("Validating json response: {}", jsonBodyResponse)

    def response = new JsonSlurper().parseText(jsonBodyResponse)
    // assert StringUtils.isNoneBlank(response)
    // TODO: Implement other assertions

    log.info("The response is valid")
  }

  @When("the user cancel reservation using reference number {string} and name {string}")
  def userCancelReservation(String reference, String clientName) {
    def encodedClientName = URLEncoder.encode(clientName, "UTF-8")
    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/reservations/cancel?reference=${reference}&name=${encodedClientName}")
        .requestMethod(DELETE)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(CANCEL_RESERVATION_RESPONSE_KEY, restApiClient.responseAsText)
      scenarioContext.put(REFERENCE_NUMBER_KEY, reference)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the response should have information about successful cancellation")
  def theResponseShouldBeSuccessCancellation() {
    def reference = scenarioContext.get(REFERENCE_NUMBER_KEY) as String
    def jsonBodyResponse = scenarioContext.get(CANCEL_RESERVATION_RESPONSE_KEY) as String
    log.info("Validating json response: {}", jsonBodyResponse)

    def response = new JsonSlurper().parseText(jsonBodyResponse)
    assert response.status == "SUCCESS"
    assert response.message == "Reservation with referenceNr: '${reference}' cancelled successfully"

    log.info("The response is valid")
  }

  @Given("the user has chosen a trip")
  def theUserHasChosenTrip() {
    scenarioContext.put(TRIP_ID_KEY, "WJ311823F")
  }

  @When("the user searches for available seats")
  def theUserSearchesForAvailableSeats() {
    def tripId = scenarioContext.get(TRIP_ID_KEY) as String
    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/trips/${tripId}/seats")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      def jsonResponse = restApiClient.responseAsText
      assert StringUtils.isNotBlank(jsonResponse)
      // TODO: When json structure is ready can add more assertions
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the user chooses an available seat with number {string}")
  def theUserChoosesAnAvailableSeatWithNumber(String seatNumber) {
    def tripId = scenarioContext.get(TRIP_ID_KEY) as String
    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/trips/${tripId}/seats/${seatNumber}/lock")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(SESSION_TOKEN, restApiClient.responseAsText) // TODO: retrieve token from json
      scenarioContext.put(SEAT_NUMBER_KEY, seatNumber)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @And("the chosen seat is locked")
  def theChosenSeatIsLocked() {
    def tripId = scenarioContext.get(TRIP_ID_KEY) as String
    def seatNumber = scenarioContext.get(SEAT_NUMBER_KEY) as String

    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/trips/${tripId}/seats")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      def jsonResponse = restApiClient.responseAsText
      // TODO: check if $seatNumber is unavailable
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the user confirms reservation")
  def theUserConfirmsReservationAndMakesPayment() {
    def sessionToken = scenarioContext.get(SESSION_TOKEN) as String
    def reservationJsonBodyTest = getClass().getResourceAsStream("json/reservation.json").text
    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/reservation/process")
        .addJsonBody(reservationJsonBodyTest)
        .addHeader(HTTPHeader.sessionToken(sessionToken))
        .requestMethod(POST)
        .connect()) {

      restApiClient.assertStatusCode(200)
      def reservationRespond = new JsonSlurper().parseText(restApiClient.responseAsText)
      scenarioContext.put(RESERVATION_ID, reservationRespond.reservationId)
      scenarioContext.put(PRICE, reservationRespond.price)
      scenarioContext.put(REFERENCE_NUMBER_KEY, reservationRespond.bookingReference)

    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @And("the user makes payment")
  def theUserMakesPayment() {
    def clientSecret
    def price = scenarioContext.get(PRICE) as String
    def sessionToken = scenarioContext.get(SESSION_TOKEN) as String
    def reservationId = scenarioContext.get(RESERVATION_ID) as String

    def intentRequestTemplate = getClass().getResourceAsStream("json/stripIntentTemplate.json").text
    def intentRequest = JsonTemplate.newTemplate(intentRequestTemplate)
        .binding(["reservation": reservationId, "price": price])
        .generate()

    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/payment/create-intent")
        .addJsonBody(intentRequest)
        .addHeader(HTTPHeader.sessionToken(sessionToken))
        .requestMethod(POST)
        .connect()) {

      restApiClient.assertStatusCode(200)
      def intentResponse = new JsonSlurper().parseText(restApiClient.responseAsText)
      clientSecret = intentResponse.clientSecret as String

    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
      return
    }

    def paymentConfirmationTemplate = getClass().getResourceAsStream("json/stripeConfirmationTemplate.json").text
    def createdAt = System.currentTimeMillis() / 1000
    def webhookRequest = JsonTemplate.newTemplate(paymentConfirmationTemplate)
        .binding(["clientSecret": clientSecret,
                  "price"       : price,
                  "createdAt"   : createdAt,
                  "paymentId"   : UUID.randomUUID().toString()])
        .generate()

    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/payment/stripe/webhook")
        .addJsonBody(webhookRequest)
        .requestMethod(POST)
        .connect()) {

      restApiClient.assertStatusCode(200)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @And("the user receives confirmation details")
  def userReceivesConfirmationDetails() {
    def reference = scenarioContext.get(REFERENCE_NUMBER_KEY) as String
    def encodedClientName = URLEncoder.encode("John Doe", "UTF-8")
    try (var restApiClient = newConnection()
        .url("${baseUrl()}/v1/reservations/details?reference=${reference}&name=${encodedClientName}")
        .requestMethod(DELETE)
        .connect()) {

      restApiClient.assertStatusCode(200)
      def response = new JsonSlurper().parseText(restApiClient.responseAsText)
      // assert StringUtils.isNoneBlank(response)
      // TODO: Implement assertions
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }
}