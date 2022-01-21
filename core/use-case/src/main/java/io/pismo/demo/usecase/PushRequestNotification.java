package io.pismo.demo.usecase;

import io.pismo.demo.usecase.port.MessageRepository;
import io.pismo.demo.domain.Chat;
import io.pismo.demo.domain.Message;
import io.pismo.demo.domain.Status;
import io.pismo.demo.domain.exception.NotificationException;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class PushRequestNotification {

  private final MessageRepository repository;

  @Inject
  PushRequestNotification(final MessageRepository repository) {
    this.repository = repository;
  }

  public Message push(final Message message) throws NotificationException {
    return repository.create(message.toBuilder()
        .chats(getSingleListWithCurrentTimesAndWaitingStatus())
        .build());
  }

  private List<Chat> getSingleListWithCurrentTimesAndWaitingStatus() {
    return List.of(Chat.builder()
        .date(ZonedDateTime.now())
        .status(Status.WAITING)
        .build());
  }

}
