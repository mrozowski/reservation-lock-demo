package com.mrozowski.seatreservation.domain.port;

import com.mrozowski.seatreservation.domain.command.InitializePaymentCommand;
import com.mrozowski.seatreservation.domain.model.PaymentIntentDetails;

public interface PaymentGateway {

  PaymentIntentDetails createPaymentIntent(InitializePaymentCommand command);
}
