package com.mrozowski.seatreservation.adapter.incoming;

class ResourceNotFoundException extends RuntimeException {

  ResourceNotFoundException(String message) {
    super(message);
  }
}
