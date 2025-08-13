package com.leonbros.drac.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddressResponse {

  private Status status;
  private AddressDto address;

  public enum Status {
    SUCCESS, UNAUTHORIZED, UNEXPECTED_ERROR
  }
}
