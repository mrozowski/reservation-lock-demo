package com.mrozowski.seatreservation.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record Trip(String id,
                   String departure,
                   String destination,
                   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") OffsetDateTime date,
                   int price) {

}

