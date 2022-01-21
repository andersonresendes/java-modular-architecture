package io.pismo.demo.controller.dto;

import io.pismo.demo.domain.Status;

public enum StatusResponseDto {
  WAITING,
  SENDING,
  SENT;

  public static StatusResponseDto findByStatus(final Status status) {
    if (status == null) {
      return WAITING;
    }
    return StatusResponseDto.valueOf(status.name());
  }

}
