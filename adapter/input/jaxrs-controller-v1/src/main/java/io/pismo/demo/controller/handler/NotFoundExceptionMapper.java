package io.pismo.demo.controller.handler;

import io.pismo.demo.controller.dto.ErrorDto;
import io.pismo.demo.usecase.exception.MessageNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<MessageNotFoundException> {

  @Override
  public Response toResponse(final MessageNotFoundException e) {
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ErrorDto.builder().message(e.getMessage()).build())
        .build();
  }

}
