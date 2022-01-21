package io.pismo.demo.domain.exception;

public class InvalidMessageException extends NotificationException {

  public InvalidMessageException(String message) {
    super(message);
  }
}
