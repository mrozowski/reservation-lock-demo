package com.mrozowski.seatreservation.domain.model;

public record UserSeatSessionTokenConfirmation(long seatId, UserSessionTokenStatus sessionTokenStatus) {

  public boolean isValid() {
    return sessionTokenStatus == UserSessionTokenStatus.VALID;
  }

  public boolean isExpired() {
    return sessionTokenStatus == UserSessionTokenStatus.SESSION_EXPIRED;
  }

  public boolean isInvalid() {
    return sessionTokenStatus == UserSessionTokenStatus.INVALID;
  }

  public static UserSeatSessionTokenConfirmation valid(long seatId) {
    return new UserSeatSessionTokenConfirmation(seatId, UserSessionTokenStatus.VALID);
  }

  public static UserSeatSessionTokenConfirmation expired(long seatId) {
    return new UserSeatSessionTokenConfirmation(seatId, UserSessionTokenStatus.SESSION_EXPIRED);
  }

  public static UserSeatSessionTokenConfirmation invalid(long seatId) {
    return new UserSeatSessionTokenConfirmation(seatId, UserSessionTokenStatus.INVALID);
  }

  private enum UserSessionTokenStatus {
    VALID,
    SESSION_EXPIRED,
    INVALID
  }
}
