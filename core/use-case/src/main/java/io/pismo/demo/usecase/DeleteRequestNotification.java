package io.pismo.demo.usecase;

import io.pismo.demo.usecase.exception.MessageNotFoundException;
import io.pismo.demo.usecase.port.MessageRepository;
import io.pismo.demo.domain.exception.NotificationException;
import io.pismo.demo.domain.vo.MessageId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class DeleteRequestNotification {

  private final MessageRepository repository;

  @Inject
  DeleteRequestNotification(final MessageRepository repository) {
    this.repository = repository;
  }

  public void delete(final MessageId id) throws NotificationException {
    if (repository.exists(id)) {
      repository.delete(id);
    } else {
      throw new MessageNotFoundException(
          "Message id: " + id + " dont exists");
    }
  }

}
