Feature: Reservation API Scenarios

  Scenario: Search for trips departing from Warsaw
    Given the service is running
    When I call the search trips endpoint with departure from "Warsaw"
    Then the response should contain trips departing from "Warsaw"