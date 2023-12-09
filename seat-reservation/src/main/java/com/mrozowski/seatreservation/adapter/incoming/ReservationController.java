package com.mrozowski.seatreservation.adapter.incoming;

import com.mrozowski.seatreservation.domain.ReservationFacade;
import com.mrozowski.seatreservation.domain.model.CancellationMessage;
import com.mrozowski.seatreservation.domain.model.ReservationDetails;
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
    log.debug("Received request reservation details with reference={}", reference);
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

  @ExceptionHandler(ResourceNotFoundException.class)
  ResponseEntity<ApiError> handleIDateTimeParseException(ResourceNotFoundException ex, HttpServletRequest request) {
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
