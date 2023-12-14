package com.mrozowski.seatreservation.domain.model;

import lombok.Builder;

@Builder
public record ReservationRequestCommand(String name,
                                        String surname,
                                        String phone,
                                        String email,
                                        String tripId,
                                        String seatNumber,
                                        int price,
                                        String token) {

}
