package io.pismo.demo.domain.exception;

public class InvalidRecipientException extends NotificationException {

  public InvalidRecipientException(final String message) {
    super(message);
  }
}
