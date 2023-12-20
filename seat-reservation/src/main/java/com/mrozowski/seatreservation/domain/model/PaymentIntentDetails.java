package com.mrozowski.seatreservation.domain.model;

public record PaymentIntentDetails(String clientSecret, int price, String productId) {
}
