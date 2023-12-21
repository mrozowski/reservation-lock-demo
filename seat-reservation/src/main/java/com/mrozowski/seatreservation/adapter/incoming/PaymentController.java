package com.mrozowski.seatreservation.adapter.incoming;

import com.mrozowski.seatreservation.domain.ReservationFacade;
import com.mrozowski.seatreservation.domain.command.InitializePaymentCommand;
import com.mrozowski.seatreservation.domain.command.PaymentConfirmationCommand;
import com.mrozowski.seatreservation.domain.model.PaymentIntentDetails;
import com.mrozowski.seatreservation.infrastructure.ReservationPaymentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("v1/payment")
@RequiredArgsConstructor
class PaymentController {

  private final ReservationPaymentProperties paymentProperties;
  private final ReservationFacade reservationFacade;

  @PostMapping(value = "/stripe/create-intent")
  ResponseEntity<PaymentIntentDetails> createIntent(@RequestBody PaymentRequest request) {
    log.info("Received payment request for reservationId: {}", request.getProductId());

    var command = InitializePaymentCommand.stripe(request.getAmount(), request.getProductId(), request.getCurrency());
    return ResponseEntity.ok(reservationFacade.initializePayment(command));
  }

  @PostMapping(value = "/stripe/webhook")
  ResponseEntity<Void> stripeWebhook(@RequestBody StripePaymentConfirmationRequest request) {
    log.info("Received payment confirmation for productId: {}", request.getProductId());

    PaymentConfirmationCommand command;
    if (isPaymentSuccessful(request.getStatus())){
      command = PaymentConfirmationCommand.success(request.getId(), request.getProductId(), request.getAmount());
    } else {
      command = PaymentConfirmationCommand.failed(request.getId(), request.getProductId(), request.getAmount());
    }

    reservationFacade.updatePayment(command);
    return ResponseEntity.ok().build();
  }

  private boolean isPaymentSuccessful(String status) {
    return paymentProperties.getStripe().successfulPaymentStatus().equals(status);
  }
}
