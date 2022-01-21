package io.pismo.demo.usecase;


import io.pismo.demo.usecase.exception.MessageNotFoundException;
import io.pismo.demo.usecase.port.MessageRepository;
import io.pismo.demo.domain.Message;
import io.pismo.demo.domain.exception.NotificationException;
import io.pismo.demo.domain.vo.MessageId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class FindRequestNotification {

  private final MessageRepository repository;

  @Inject
  FindRequestNotification(final MessageRepository repository) {
    this.repository = repository;
  }

  public Message find(final MessageId id) throws NotificationException {
    return repository.find(id).orElseThrow(() ->
        new MessageNotFoundException("Message with Id " + id + " not found")
    );
  }

}
