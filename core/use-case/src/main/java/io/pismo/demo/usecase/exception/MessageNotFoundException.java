package io.pismo.demo.usecase.exception;

import io.pismo.demo.domain.exception.NotificationException;

public class MessageNotFoundException extends NotificationException {

  public MessageNotFoundException(final String message) {
    super(message);
  }
}
