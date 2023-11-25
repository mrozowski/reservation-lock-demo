package com.mrozowski.seatreservation.domain.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record Trip (String id, String departure, String destination, OffsetDateTime date, int price){

}

