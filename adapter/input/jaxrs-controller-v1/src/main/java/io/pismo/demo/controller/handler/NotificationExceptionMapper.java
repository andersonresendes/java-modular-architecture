package io.pismo.demo.controller.handler;

import io.pismo.demo.controller.dto.ErrorDto;
import io.pismo.demo.domain.exception.NotificationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotificationExceptionMapper implements ExceptionMapper<NotificationException> {

  @Override
  public Response toResponse(final NotificationException e) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(ErrorDto.builder().message(e.getMessage()).build())
        .build();
  }

}
