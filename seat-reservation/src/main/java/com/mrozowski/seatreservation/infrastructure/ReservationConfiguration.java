package com.mrozowski.seatreservation.infrastructure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
@EnableConfigurationProperties({ReservationDateProperties.class, ReservationSeatLockProperties.class})
class ReservationConfiguration {

  @Bean
  DateTimeFormatter reservationDateTimeFormatter(ReservationDateProperties properties) {
    return DateTimeFormatter.ofPattern(properties.dateFormat(), Locale.ENGLISH);
  }
}
