package io.pismo.demo.controller.handler;

import static org.assertj.core.api.Assertions.assertThat;

import io.pismo.demo.controller.dto.ErrorDto;
import io.pismo.demo.usecase.exception.MessageNotFoundException;
import org.junit.jupiter.api.Test;

class NotFoundExceptionMapperTest {

  @Test
  void whenReturnErrorThenCreateResponse() {
    var mapper = new NotFoundExceptionMapper();
    var response = mapper.toResponse(new MessageNotFoundException("Error"));
    assertThat(response.getStatus()).isEqualTo(404);
    assertThat(response.getEntity()).isEqualTo(ErrorDto.builder().message("Error").build());
  }

}