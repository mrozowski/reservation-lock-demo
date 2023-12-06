package com.mrozowski.seatreservation

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import io.cucumber.java.Before
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.apache.commons.lang3.StringUtils

import java.text.SimpleDateFormat

import static com.mrozowski.seatreservation.RestApiClient.RequestMethod.GET

@Slf4j
class ReservationSearchTripsApiSteps extends SpringIntegrationSpecBase {

  private static final String SEARCH_TRIP_RESPONSE_KEY = "searchTripResponse"
  private static final int PAGE_SIZE = 5

  private ScenarioContext scenarioContext = new ScenarioContext()

  @Before
  void beforeScenario() {
    scenarioContext = new ScenarioContext()
  }

  @When("the user search trips")
  def theUserSearchTrips() {
    try (var restApiClient = RestApiClient.newConnection()
        .url("${baseUrl()}/v1/trips/search?size=${PAGE_SIZE}")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(SEARCH_TRIP_RESPONSE_KEY, restApiClient.responseAsText)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }


  @Then("the response should have available trips")
  def theResponseShouldHaveAvailableTrips() {
    def jsonBodyResponse = scenarioContext.get(SEARCH_TRIP_RESPONSE_KEY) as String
    log.info("Validating json response: {}", jsonBodyResponse)


    def response = new JsonSlurper().parseText(jsonBodyResponse)
    assert response.content.size() == PAGE_SIZE
    assert response.content.every {
      StringUtils.isNoneBlank(it.id)
      StringUtils.isNoneBlank(it.departure)
      StringUtils.isNoneBlank(it.destination)
      StringUtils.isNoneBlank(it.date)
      Double.valueOf(it.price) > 0
    }

    log.info("The response is valid")
  }

  @When("the user search trips with departure from {string}")
  def callTripSearchEndpointWithDeparture(String departure) {
    def encodedDeparture = URLEncoder.encode(departure, "UTF-8")
    try (var restApiClient = RestApiClient.newConnection()
        .url("${baseUrl()}/v1/trips/search?departure=${encodedDeparture}")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(SEARCH_TRIP_RESPONSE_KEY, restApiClient.responseAsText)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the response should include trips departing from {string}")
  def validateResponseTripDeparture(String departure) {
    def jsonBodyResponse = scenarioContext.get(SEARCH_TRIP_RESPONSE_KEY) as String
    log.info("Validating json response: {}", jsonBodyResponse)

    def response = new JsonSlurper().parseText(jsonBodyResponse)
    assert response.content.every { it.departure == departure }
    log.info("The response is valid")
  }

  @When("the user search trips with date {string}")
  def callTripSearchEndpointWithDate(String date) {
    try (var restApiClient = RestApiClient.newConnection()
        .url("${baseUrl()}/v1/trips/search?date=${date}")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(SEARCH_TRIP_RESPONSE_KEY, restApiClient.responseAsText)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the response should include trips for date {string}")
  def validateResponseTripDate(String date) {
    def jsonBodyResponse = scenarioContext.get(SEARCH_TRIP_RESPONSE_KEY) as String
    log.info("Validating json response: {}", jsonBodyResponse)

    def response = new JsonSlurper().parseText(jsonBodyResponse)
    def responseDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    def userDateFormat = new SimpleDateFormat("dd-MM-yyyy")

    response.content.every {
      def responseDate = responseDateFormat.parse(it.date as String)
      def formattedResponseDate = userDateFormat.format(responseDate)
      assert formattedResponseDate == date
    }

    log.info("The response is valid")
  }

  @When("the user search trips with departure from {string} on date {string}")
  def theUserSearchTripsWithDepartureFromOnDate(String departure, String date) {
    def encodedDeparture = URLEncoder.encode(departure, "UTF-8")
    try (var restApiClient = RestApiClient.newConnection()
        .url("${baseUrl()}/v1/trips/search?departure=${encodedDeparture}&date=${date}")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(SEARCH_TRIP_RESPONSE_KEY, restApiClient.responseAsText)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the response should include trips departing from {string} on date {string}")
  def theResponseShouldIncludeTripsDepartingFromOnDate(String departure, String date) {
    def jsonBodyResponse = scenarioContext.get(SEARCH_TRIP_RESPONSE_KEY) as String
    log.info("Validating json response: {}", jsonBodyResponse)

    def response = new JsonSlurper().parseText(jsonBodyResponse)
    def responseDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    def userDateFormat = new SimpleDateFormat("dd-MM-yyyy")

    response.content.every {
      def responseDate = responseDateFormat.parse(it.date as String)
      def formattedResponseDate = userDateFormat.format(responseDate)
      assert formattedResponseDate == date
      assert it.departure == departure
    }
  }

  @When("the user search trips with departure from {string} to {string} on date {string}")
  def searchTripsWithDepartureDestinationAndDate(String departure, String destination, String date) {
    def encodedDeparture = URLEncoder.encode(departure, "UTF-8")
    def encodedDestination = URLEncoder.encode(destination, "UTF-8")
    try (var restApiClient = RestApiClient.newConnection()
        .url("${baseUrl()}/v1/trips/search?departure=${encodedDeparture}&destination=${encodedDestination}&date=${date}")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(SEARCH_TRIP_RESPONSE_KEY, restApiClient.responseAsText)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the response should include trips departing from {string} to {string} on date {string}")
  def theResponseShouldIncludeTripsDepartingFromToOnDate(String departure, String destination, String date) {
    def jsonBodyResponse = scenarioContext.get(SEARCH_TRIP_RESPONSE_KEY) as String
    log.info("Validating json response: {}", jsonBodyResponse)

    def response = new JsonSlurper().parseText(jsonBodyResponse)
    def responseDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    def userDateFormat = new SimpleDateFormat("dd-MM-yyyy")

    response.content.every {
      def responseDate = responseDateFormat.parse(it.date as String)
      def formattedResponseDate = userDateFormat.format(responseDate)
      assert formattedResponseDate == date
      assert it.departure == departure
      assert it.destination == destination
    }
  }
}
