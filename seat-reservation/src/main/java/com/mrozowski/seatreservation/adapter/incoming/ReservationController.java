package com.mrozowski.seatreservation.adapter.incoming;

import com.mrozowski.seatreservation.domain.ReservationFacade;
import com.mrozowski.seatreservation.domain.command.ResourceNotFoundException;
import com.mrozowski.seatreservation.domain.model.CancellationMessage;
import com.mrozowski.seatreservation.domain.model.ReservationConfirmation;
import com.mrozowski.seatreservation.domain.model.ReservationDetails;
import com.mrozowski.seatreservation.domain.model.ReservationRequestCommand;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("v1/reservations")
@RequiredArgsConstructor
class ReservationController {

  private final ReservationFacade reservationFacade;

  @GetMapping("/details")
  ResponseEntity<ReservationDetails> getDetails(@RequestParam String reference, @RequestParam String name) {
    log.info("Received request reservation details with reference={}", reference);
    return reservationFacade
        .getReservationDetails(reference, name)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Reservation with reference=" + reference + " not found"));
  }

  @DeleteMapping("/cancel")
  ResponseEntity<CancellationMessage> cancelReservation(@RequestParam String reference, @RequestParam String name) {
    log.debug("Received request to cancel reservation with reference={}", reference);
    return ResponseEntity.ok(reservationFacade.cancelReservation(reference, name));
  }

  @PostMapping(value = "/process")
  ResponseEntity<ReservationConfirmation> processReservation(
      @RequestBody ReservationRequest request,
      @RequestHeader("Authorization") String sessionToken) {
    log.info("Received request to process reservation={}", request);

    var command = ReservationRequestCommand.builder()
        .name(request.getName())
        .surname(request.getSurname())
        .phone(request.getPhone())
        .email(request.getEmail())
        .tripId(request.getTripId())
        .seatNumber(request.getSeatNumber())
        .price(request.getPrice())
        .token(sessionToken)
        .build();
    return ResponseEntity.ok(reservationFacade.process(command));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  ResponseEntity<ApiError> handleNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        ApiError.builder()
            .timestamp(LocalDateTime.now())
            .error("NOT_FOUND")
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .status(HttpStatus.NOT_FOUND)
            .build()
    );
  }
}
