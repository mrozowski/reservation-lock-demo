package com.mrozowski.seatreservation.domain;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
class BookingReferenceGenerator {

  private static final SecureRandom RANDOM = new SecureRandom();
  private static final int STRING_LENGTH = 6;
  private static final String CHARACTER_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  String generate() {
    StringBuilder randomStringBuilder = new StringBuilder(STRING_LENGTH);

    for (int i = 0; i < STRING_LENGTH; i++) {
      int randomIndex = RANDOM.nextInt(CHARACTER_SET.length());
      randomStringBuilder.append(CHARACTER_SET.charAt(randomIndex));
    }

    return randomStringBuilder.toString();
  }
}
