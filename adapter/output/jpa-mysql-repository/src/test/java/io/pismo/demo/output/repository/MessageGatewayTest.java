package io.pismo.demo.output.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.pismo.demo.domain.Chat;
import io.pismo.demo.domain.CommunicationChannel;
import io.pismo.demo.domain.Message;
import io.pismo.demo.domain.Recipient;
import io.pismo.demo.domain.Status;
import io.pismo.demo.domain.vo.MessageBody;
import io.pismo.demo.domain.vo.MessageId;
import io.pismo.demo.domain.vo.Phone;
import io.pismo.demo.output.repository.entity.CommunicationChannelEntity;
import io.pismo.demo.output.repository.entity.MessageEntity;
import io.pismo.demo.output.repository.entity.MessageEntityRepository;
import io.pismo.demo.output.repository.entity.RecipientEntity;
import io.pismo.demo.output.repository.mapper.MessageMapper;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageGatewayTest {

  public static final long ID = 25L;
  public static final MessageEntity MESSAGE_ENTITY = MessageEntity.builder()
      .id(ID)
      .channel(CommunicationChannelEntity.EMAIL)
      .recipient(RecipientEntity.builder()
          .phoneNumber("78924")
          .build())
      .body("Test").build();
  public static final Message MESSAGE = Message.builder()
      .id(MessageId.from(ID))
      .channel(CommunicationChannel.WHATSAPP)
      .recipient(Recipient.builder()
          .phone(Phone.from("78924", "1243243"))
          .build())
      .chats(List.of(Chat.builder()
          .status(Status.SENT)
          .date(ZonedDateTime.now())
          .build()))
      .body(MessageBody.from("Hello!")).build();

  @InjectMocks
  private MessageGateway messageGateway;
  @Mock
  private MessageEntityRepository repository;
  @Spy
  private MessageMapper messageMapper = new MessageMapper();

  @Test
  void wheDeleteThenCallRepository() {
    messageGateway.delete(MessageId.from(25L));
    verify(repository).deleteById(25L);
    verifyNoMoreInteractions(repository);
  }

  @Test
  void wheFindThenCallRepository() {
    when(repository.findById(ID)).thenReturn(Optional.of(MESSAGE_ENTITY));
    var result = messageGateway.find(MessageId.from(ID));
    assertThat(result).isNotEmpty()
        .get()
        .satisfies(r -> assertThat(r.getId()).isEqualTo(Optional.of(MessageId.from(ID))));
    verify(repository).findById(ID);
    verifyNoMoreInteractions(repository);
  }

  @Test
  void wheExistsThenCallRepository() {
    when(repository.existsById(ID)).thenReturn(true);
    var result = messageGateway.exists(MessageId.from(ID));
    assertThat(result).isTrue();
    verify(repository).existsById(ID);
    verifyNoMoreInteractions(repository);
  }

  @Test
  void wheDoNotExistsThenCallRepository() {
    when(repository.existsById(ID)).thenReturn(false);
    var result = messageGateway.exists(MessageId.from(ID));
    assertThat(result).isFalse();
    verify(repository).existsById(ID);
    verifyNoMoreInteractions(repository);
  }

  @Test
  void wheCreateThenCallRepository() {
    var result = messageGateway.create(MESSAGE);
    assertThat(result).isNotNull()
        .satisfies(r -> assertThat(r.getId()).isEqualTo(Optional.of(MessageId.from(ID))));
    ArgumentCaptor<MessageEntity> captor = ArgumentCaptor.forClass(MessageEntity.class);
    verify(repository).save(captor.capture());
    verifyNoMoreInteractions(repository);
    MessageEntity savedMessage = captor.getValue();
    assertThat(savedMessage.getChats()).hasSize(1).first().satisfies(
        c -> assertThat(c.getMessage()).isNotNull()
    );
  }

}