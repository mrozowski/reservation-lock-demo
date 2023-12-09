package com.mrozowski.seatreservation.adapter.incoming;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
record ApiError(HttpStatus status,
                String error,
                String message,
                String path,
                @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime timestamp
) { }
