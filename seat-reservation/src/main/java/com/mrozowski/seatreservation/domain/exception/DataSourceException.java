package com.mrozowski.seatreservation.domain.exception;

import java.io.Serial;

public class DataSourceException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = -8950933702407491748L;

  public DataSourceException(String message, Throwable throwable){
    super(message, throwable);
  }
}
