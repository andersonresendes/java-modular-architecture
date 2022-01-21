package io.pismo.demo.output.repository;

import io.pismo.demo.domain.Message;
import io.pismo.demo.domain.vo.MessageId;
import io.pismo.demo.output.repository.entity.MessageEntityRepository;
import io.pismo.demo.output.repository.mapper.MessageMapper;
import io.pismo.demo.usecase.port.MessageRepository;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
class MessageGateway implements MessageRepository {

  private final MessageEntityRepository repository;
  private final MessageMapper messageMapper;

  @Inject
  MessageGateway(final MessageEntityRepository repository,
                 final MessageMapper messageMapper) {
    this.repository = repository;
    this.messageMapper = messageMapper;
  }

  @Override
  public Message create(final Message message) {
    var entity = messageMapper.toEntity(message);
    entity.getChats().forEach(c -> c.setMessage(entity));
    repository.save(entity);
    return messageMapper.toDomain(entity);
  }

  @Override
  public void delete(final MessageId id) {
    repository.deleteById(id.value());
  }

  @Override
  public Optional<Message> find(final MessageId id) {
    return repository.findById(id.value()).map(messageMapper::toDomain);
  }

  @Override
  public boolean exists(final MessageId id) {
    return repository.existsById(id.value());
  }

}
