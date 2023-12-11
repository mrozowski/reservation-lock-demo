Feature: Reservation API Scenarios

  Scenario: User searches for all trips
    When the user search trips
    Then the response should have available trips

  Scenario: User searches for trips departing from Warsaw
    When the user search trips with departure from "Warsaw"
    Then the response should include trips departing from "Warsaw"

  Scenario: User searches for trips on a specific date
    When the user search trips with date "12-03-2024"
    Then the response should include trips for date "12-03-2024"

  Scenario: User searches for trips on a specific date and departure
    When the user search trips with departure from "New York" on date "23-04-2024"
    Then the response should include trips departing from "New York" on date "23-04-2024"

  Scenario: User searches for trips on a specific date, departure and destination
    When the user search trips with departure from "Berlin" to "Rio de Janeiro" on date "04-12-2024"
    Then the response should include trips departing from "Berlin" to "Rio de Janeiro" on date "04-12-2024"

  Scenario: User ask for his reservation details
    When the user ask for reservation details using reference number "ATJT09" and name "John Doe"
    Then the response should have reservation details

  Scenario: User cancels reservation
    When the user cancel reservation using reference number "ATJT09" and name "John Doe"
    Then the response should have information about successful cancellation

  Scenario: User makes a reservation for a trip
    Given the user has chosen a trip
    When the user searches for available seats
    Then the user chooses an available seat with number "15A"
    And the chosen seat is locked
    Then the user confirms reservation
    And the user makes payment
    And the user receives confirmation details
