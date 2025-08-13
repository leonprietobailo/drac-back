package com.leonbros.drac.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecipientResponse {

  private Status status;
  private RecipientDto recipient;

  public enum Status {
    SUCCESS, UNAUTHORIZED, UNEXPECTED_ERROR
  }
}
