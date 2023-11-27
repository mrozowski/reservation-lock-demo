package com.mrozowski.seatreservation.adapter.incoming

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DateParserSpec extends Specification {

  @Unroll
  def "Should parse string to LocalDate"() {
    expect:
    def result = new DateParser(reservationDateTimeFormatter).toLocalDate(inputDate)
    result == expectedDate

    where:
    reservationDateTimeFormatter              | inputDate    | expectedDate
    DateTimeFormatter.ofPattern("yyyy-MM-dd") | "2023-11-28" | LocalDate.of(2023, 11, 28)
    DateTimeFormatter.ofPattern("MM/dd/yyyy") | "12/31/2023" | LocalDate.of(2023, 12, 31)
    DateTimeFormatter.ofPattern("dd-MM-yyyy") | "11-05-2024" | LocalDate.of(2024, 05, 11)
  }

  @Unroll
  def "Should throw DateTimeParseException"() {
    given:
    def dataTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    def dateParser = new DateParser(dataTimeFormatter)

    when:
    def result = dateParser.toLocalDate("2023.04.23232")

    then:
    thrown DateTimeParseException
  }
}
