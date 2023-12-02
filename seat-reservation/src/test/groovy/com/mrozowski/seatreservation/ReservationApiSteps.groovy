package com.mrozowski.seatreservation

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration

import static com.mrozowski.seatreservation.RestApiClient.RequestMethod.GET

@Slf4j
@CucumberContextConfiguration
class ReservationApiSteps extends SpringIntegrationSpec {

  public static final String BASE_URL = "http://localhost"
  public static final String SEARCH_TRIP_RESPONSE_KEY = "searchTripResponse"
  private ScenarioContext scenarioContext = new ScenarioContext()

  @Before
  void beforeScenario() {
    scenarioContext = new ScenarioContext()
  }

  @Given("the service is running")
  def givenServiceIsRunning() {
    print "Health Check is ok"
    //TODO: Add heath check
  }

  @When("I call the search trips endpoint with departure from {string}")
  def whenICallTheEndpoint(String departure) {
    try (var restApiClient = RestApiClient.newConnection()
        .url("${BASE_URL}:${port}/v1/trips/search?departure=${departure}")
        .requestMethod(GET)
        .connect()) {

      restApiClient.assertStatusCode(200)
      scenarioContext.put(SEARCH_TRIP_RESPONSE_KEY, restApiClient.responseAsText)
    } catch (Exception e) {
      print "There was an error during REST call: ${e.message}"
    }
  }

  @Then("the response should contain trips departing from {string}")
  def thenResponseShouldContainWarsawTrips(String departure) {
    def jsonBodyResponse = scenarioContext.get(SEARCH_TRIP_RESPONSE_KEY) as String
    log.info("Validating json response: {}", jsonBodyResponse)

    def response = new JsonSlurper().parseText(jsonBodyResponse)
    assert response.numberOfElements == 1
    assert response.content[0].departure == departure
    log.info("The response is valid")
  }

}
