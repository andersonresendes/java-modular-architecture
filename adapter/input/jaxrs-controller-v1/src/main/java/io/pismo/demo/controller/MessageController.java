package io.pismo.demo.controller;

import io.pismo.demo.controller.dto.MessageCreateDto;
import io.pismo.demo.controller.dto.MessageResponseDto;
import io.pismo.demo.controller.mapper.ControllerMessageMapper;
import io.pismo.demo.domain.exception.NotificationException;
import io.pismo.demo.domain.vo.MessageId;
import io.pismo.demo.usecase.DeleteRequestNotification;
import io.pismo.demo.usecase.FindRequestNotification;
import io.pismo.demo.usecase.PushRequestNotification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/message")
@Produces(MediaType.APPLICATION_JSON)
public class MessageController {

  private final FindRequestNotification findRequestNotification;
  private final PushRequestNotification pushRequestNotification;
  private final DeleteRequestNotification deleteRequestNotification;
  private final ControllerMessageMapper mapper;

  @Inject
  MessageController(final FindRequestNotification findRequestNotification,
                           final PushRequestNotification pushRequestNotification,
                           final DeleteRequestNotification deleteRequestNotification,
                           final ControllerMessageMapper mapper) {
    this.findRequestNotification = findRequestNotification;
    this.pushRequestNotification = pushRequestNotification;
    this.deleteRequestNotification = deleteRequestNotification;
    this.mapper = mapper;
  }

  @Operation(summary = "Create a new message",
      responses = {
          @ApiResponse(description = "The message",
              content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = MessageResponseDto.class))))
      })
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(final MessageCreateDto messageDto) throws NotificationException {
    var entity = mapper.toDomain(messageDto);
    var message = pushRequestNotification.push(entity);
    return Response.status(Response.Status.CREATED)
        .entity(mapper.toDto(message))
        .build();
  }

  @Operation(summary = "Get message by id",
      responses = {
          @ApiResponse(description = "The message",
              content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = MessageResponseDto.class)))),
          @ApiResponse(responseCode = "404", description = "Order not found")})
  @GET
  @Path("/{id}")
  public Response find(@PathParam("id") final Long id) throws NotificationException {
    var message = findRequestNotification.find(MessageId.from(id));
    return Response.ok(mapper.toDto(message)).build();
  }

  @Operation(summary = "Delete message by id",
      responses = {
          @ApiResponse(description = "The message"),
          @ApiResponse(responseCode = "404", description = "Order not found")})
  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") final Long id) throws NotificationException {
    deleteRequestNotification.delete(MessageId.from(id));
    return Response.noContent().build();
  }


}
