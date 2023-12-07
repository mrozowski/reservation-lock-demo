package com.mrozowski.seatreservation.adapter.incoming;

import com.mrozowski.seatreservation.domain.ReservationFacade;
import com.mrozowski.seatreservation.domain.command.FilterCriteria;
import com.mrozowski.seatreservation.domain.command.FilterCriteria.FilterOperation;
import com.mrozowski.seatreservation.domain.command.TripFilterCommand;
import com.mrozowski.seatreservation.domain.model.Trip;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.mrozowski.seatreservation.domain.command.FilterCriteria.FilterOperation.EQUAL;

@Slf4j
@RestController
@RequestMapping("v1/trips")
@RequiredArgsConstructor
class TripController {

  private static final String DEPARTURE = "departure";
  private static final String DESTINATION = "destination";
  private static final String DATE = "date";

  private final DateParser dateParser;
  private final ReservationFacade reservationFacade;

  @GetMapping("/search")
  ResponseEntity<Page<Trip>> isAvailable(
      @RequestParam(defaultValue = "") String date,
      @RequestParam(defaultValue = "") String departure,
      @RequestParam(defaultValue = "") String destination,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "30") int size) {
    log.info("Received request for trips with date={}, departure={}, destination={}", date, departure, destination);

    var filters = new ArrayList<FilterCriteria>();
    addFilterIfNotBlank(filters, DEPARTURE, departure, EQUAL);
    addFilterIfNotBlank(filters, DESTINATION, destination, EQUAL);
    addDateFilterIfNotBlank(filters, DATE, date, EQUAL);

    var command = buildFilterCommand(filters, page, size);
    return ResponseEntity.ok(reservationFacade.getTripList(command));
  }

  private TripFilterCommand buildFilterCommand(List<FilterCriteria> filters, int page, int size) {
    return TripFilterCommand.builder()
        .page(page - 1)
        .pageSize(size)
        .filters(filters)
        .build();
  }

  private void addFilterIfNotBlank(List<FilterCriteria> filters, String paramName, String paramValue,
                                   FilterOperation operation) {
    if (StringUtils.isNotBlank(paramValue)) {
      filters.add(new FilterCriteria(paramName, paramValue, operation));
    }
  }

  private void addDateFilterIfNotBlank(List<FilterCriteria> filters, String paramName, String date,
                                       FilterOperation operation) {
    if (StringUtils.isNotBlank(date)) {
      var parsedDate = dateParser.toLocalDate(date);
      filters.add(new FilterCriteria(paramName, parsedDate, operation));
    }
  }
}



