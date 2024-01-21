package com.mrozowski.seatreservation.adapter.outgoing;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrozowski.seatreservation.domain.command.InitializePaymentCommand;
import com.mrozowski.seatreservation.domain.model.PaymentIntentDetails;
import com.mrozowski.seatreservation.domain.port.PaymentGateway;
import com.mrozowski.seatreservation.infrastructure.ReservationPaymentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@Profile("prod")
@RequiredArgsConstructor
class StripePaymentGateway implements PaymentGateway {

  private final RestTemplate restTemplate = new RestTemplate();
  private final ReservationPaymentProperties paymentProperties;
  private final ObjectMapper objectMapper;

  @Override
  public PaymentIntentDetails createPaymentIntent(InitializePaymentCommand command) {
    log.info("Calling stripe gateway");
    var writer = objectMapper.writer().withDefaultPrettyPrinter();
    try {
      var jsonMessage = writer.writeValueAsString(command);
      var respond = restTemplate.postForEntity(paymentProperties.getStripe().url(), jsonMessage, String.class);

      log.info("Received Payment Intend with client secret");
      return objectMapper.readValue(respond.getBody(), PaymentIntentDetails.class);
    } catch (JsonProcessingException e) {
      log.error("Error during parsing json message", e);
      throw new RuntimeException(e);
    } catch (Exception e){
      log.error("Error during calling payment service", e);
      throw new RuntimeException(e);
    }
  }
}
