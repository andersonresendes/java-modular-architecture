package io.pismo.demo.controller.mapper;

import io.pismo.demo.controller.dto.ChatResponseDto;
import io.pismo.demo.controller.dto.CommunicationChannelDto;
import io.pismo.demo.controller.dto.MessageCreateDto;
import io.pismo.demo.controller.dto.MessageResponseDto;
import io.pismo.demo.controller.dto.RecipientDto;
import io.pismo.demo.controller.dto.StatusResponseDto;
import io.pismo.demo.domain.Chat;
import io.pismo.demo.domain.CommunicationChannel;
import io.pismo.demo.domain.Message;
import io.pismo.demo.domain.Recipient;
import io.pismo.demo.domain.vo.MessageBody;
import io.pismo.demo.domain.vo.MessageId;
import io.pismo.demo.domain.vo.Phone;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ControllerMessageMapper {

  public MessageResponseDto toDto(final Message message) {
    return MessageResponseDto.builder()
        .id(message.getId().map(MessageId::value).orElse(null))
        .scheduleDate(message.getScheduleDate())
        .body(message.getBody().value())
        .channel(parse(message.getChannel()))
        .recipient(parse(message.getRecipient()))
        .chats(parse(message.getChats()))
        .build();
  }

  public Message toDomain(final MessageCreateDto message) {
    return Message.builder()
        .body(MessageBody.from(message.getBody()))
        .scheduleDate(message.getScheduleDate())
        .channel(parse(message.getChannel()))
        .recipient(parse(message.getRecipient()))
        .build();
  }

  public Collection<ChatResponseDto> parse(final Collection<Chat> chats) {
    return chats == null ? Collections.emptyList() : chats.stream()
        .map(c -> ChatResponseDto.builder()
            .date(c.getDate())
            .status(StatusResponseDto.findByStatus(c.getStatus()))
            .build())
        .collect(Collectors.toList());
  }

  private CommunicationChannel parse(final CommunicationChannelDto dto) {
    return dto == null ? null : CommunicationChannel.valueOf(dto.name());
  }

  private CommunicationChannelDto parse(final CommunicationChannel value) {
    return value == null ? null : CommunicationChannelDto.valueOf(value.name());
  }

  private Recipient parse(final RecipientDto recipient) {
    return Recipient.builder()
        .email(recipient.getEmail())
        .name(recipient.getName())
        .phone(Phone.from(recipient.getPhoneId(), recipient.getPhoneNumber()))
        .build();
  }

  private RecipientDto parse(final Recipient recipient) {
    return RecipientDto.builder()
        .email(recipient.getEmail())
        .name(recipient.getName())
        .phoneId(recipient.getPhone().getPhoneId().orElse(null))
        .phoneNumber(recipient.getPhone().getPhoneNumber())
        .build();
  }


}
