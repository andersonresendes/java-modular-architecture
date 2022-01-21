package io.pismo.demo.domain.exception;

public class NotificationException extends RuntimeException {

  public NotificationException(final String message) {
    super(message);
  }
}
