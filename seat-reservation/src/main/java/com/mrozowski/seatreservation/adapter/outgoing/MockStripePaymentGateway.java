package com.mrozowski.seatreservation.adapter.outgoing;


import com.mrozowski.seatreservation.domain.command.InitializePaymentCommand;
import com.mrozowski.seatreservation.domain.model.PaymentIntentDetails;
import com.mrozowski.seatreservation.domain.port.PaymentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
//@Profile("payment-mock")
class MockStripePaymentGateway implements PaymentGateway {

  @Override
  public PaymentIntentDetails createPaymentIntent(InitializePaymentCommand command) {
    log.info("StripeMock creating PaymentIntent");
    return new PaymentIntentDetails(generateClientSecret(), command.price(), command.productId());
  }

  private String generateClientSecret() {
    return UUID.randomUUID().toString();
  }
}
