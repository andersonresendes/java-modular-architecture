package io.pismo.demo.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.pismo.demo.domain.Message;
import io.pismo.demo.domain.Recipient;
import io.pismo.demo.domain.Status;
import io.pismo.demo.domain.vo.MessageBody;
import io.pismo.demo.domain.vo.MessageId;
import io.pismo.demo.domain.vo.Phone;
import io.pismo.demo.usecase.port.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PushRequestNotificationTest {

  @Mock
  private MessageRepository repository;

  @InjectMocks
  private PushRequestNotification deleteRequestNotification;

  @Test
  void shouldBeCreateMessageWithSuccess() {
    final var message = fakeBuilder().build();
    final var requiredResponse = mock(Message.class);

    when(repository.create(message))
        .then(invocationOnMock -> {
          Message messageInCreate = invocationOnMock.getArgument(0);
          assertThat(messageInCreate.getChats())
              .isNotNull()
              .allMatch(x -> x.getStatus().equals(Status.WAITING))
              .hasSize(1);
          return requiredResponse;
        });

    final var response = deleteRequestNotification.push(message);

    assertThat(response)
        .isEqualTo(requiredResponse);
  }

  private Message.MessageBuilder fakeBuilder() {
    return Message.builder().body(MessageBody.from("empty"))
        .recipient(Recipient.builder()
            .phone(Phone.newNumber("123123"))
            .build())
        .id(MessageId.from(1L));
  }
}