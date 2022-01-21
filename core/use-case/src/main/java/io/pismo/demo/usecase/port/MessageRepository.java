package io.pismo.demo.usecase.port;

import io.pismo.demo.domain.Message;
import io.pismo.demo.domain.vo.MessageId;
import java.util.Optional;

public interface MessageRepository {

  Message create(Message message);

  void delete(MessageId id);

  Optional<Message> find(MessageId id);

  boolean exists(MessageId id);

}
