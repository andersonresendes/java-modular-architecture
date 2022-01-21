package io.pismo.demo.output.repository.mapper;


import io.pismo.demo.domain.Chat;
import io.pismo.demo.domain.CommunicationChannel;
import io.pismo.demo.domain.Message;
import io.pismo.demo.domain.Recipient;
import io.pismo.demo.domain.Status;
import io.pismo.demo.domain.vo.MessageBody;
import io.pismo.demo.domain.vo.MessageId;
import io.pismo.demo.domain.vo.Phone;
import io.pismo.demo.output.repository.entity.ChatEntity;
import io.pismo.demo.output.repository.entity.CommunicationChannelEntity;
import io.pismo.demo.output.repository.entity.MessageEntity;
import io.pismo.demo.output.repository.entity.RecipientEntity;
import io.pismo.demo.output.repository.entity.StatusEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MessageMapper {

  public MessageEntity toEntity(final Message message) {
    return MessageEntity.builder()
        .id(message.getId().map(MessageId::value).orElse(null))
        .body(message.getBody().value())
        .channel(CommunicationChannelEntity.valueOf(message.getChannel().name()))
        .chats(toChatEntity(message.getChats()))
        .scheduleDate(message.getScheduleDate())
        .recipient(RecipientEntity.builder()
            .email(message.getRecipient().getEmail())
            .name(message.getRecipient().getName())
            .phoneId(message.getRecipient().getPhone().getPhoneId().orElse(null))
            .phoneNumber(message.getRecipient().getPhone().getPhoneNumber())
            .build())
        .build();
  }

  public Message toDomain(final MessageEntity message) {
    return Message.builder()
        .id(MessageId.from(message.getId()))
        .body(MessageBody.from(message.getBody()))
        .channel(CommunicationChannel.valueOf(message.getChannel().name()))
        .chats(toChatDomain(message.getChats()))
        .scheduleDate(message.getScheduleDate())
        .recipient(Recipient.builder()
            .email(message.getRecipient().getEmail())
            .name(message.getRecipient().getName())
            .phone(Phone.from(message.getRecipient().getPhoneId(), message.getRecipient().getPhoneNumber()))
            .build())
        .build();
  }

  private List<Chat> toChatDomain(final List<ChatEntity> chats) {
    return chats != null ? chats.stream()
        .map(c -> Chat.builder()
            .id(c.getId())
            .status(Status.valueOf(c.getStatus().name()))
            .date(c.getDate())
            .build())
        .collect(Collectors.toList()) : Collections.emptyList();
  }


  private List<ChatEntity> toChatEntity(final Collection<Chat> chats) {
    return chats != null ? chats.stream()
        .map(c -> ChatEntity.builder()
            .id(c.getId())
            .status(StatusEntity.valueOf(c.getStatus().name()))
            .date(c.getDate())
            .build())
        .collect(Collectors.toList()) : Collections.emptyList();
  }
}
