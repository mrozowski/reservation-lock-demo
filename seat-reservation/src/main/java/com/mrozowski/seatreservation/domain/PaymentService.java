package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.command.InitializePaymentCommand;
import com.mrozowski.seatreservation.domain.command.InitializePaymentCommand.PaymentMethod;
import com.mrozowski.seatreservation.domain.exception.PaymentMethodNotSupportedException;
import com.mrozowski.seatreservation.domain.model.PaymentIntentDetails;
import com.mrozowski.seatreservation.domain.port.PaymentGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class PaymentService {

  private final PaymentGateway paymentGateway;

  public PaymentIntentDetails initializePayment(InitializePaymentCommand initializePaymentCommand) {
    if (isStripePayment(initializePaymentCommand)) {
      log.info("Sending request for PaymentIntent");
      return paymentGateway.createPaymentIntent(initializePaymentCommand);
    } else {
      log.error("Payment method not supported");
      throw new PaymentMethodNotSupportedException(initializePaymentCommand.method().toString());
    }
  }

  private static boolean isStripePayment(InitializePaymentCommand initializePaymentCommand) {
    return initializePaymentCommand.method() == PaymentMethod.STRIPE;
  }
}
