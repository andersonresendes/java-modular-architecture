package io.pismo.demo.domain;

import io.pismo.demo.domain.vo.Phone;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class Recipient {

  private final String name;
  private final String email;
  private final Phone phone;

}
