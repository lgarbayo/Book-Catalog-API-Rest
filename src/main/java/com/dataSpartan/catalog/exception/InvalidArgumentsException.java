package com.dataSpartan.catalog.exception;

public class InvalidArgumentsException extends RuntimeException {

  private static final long serialVersionUID = -5400412368947993168L;

  public InvalidArgumentsException() {
    super();
  }

  public InvalidArgumentsException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public InvalidArgumentsException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidArgumentsException(String message) {
    super(message);
  }

  public InvalidArgumentsException(Throwable cause) {
    super(cause);
  }

}
