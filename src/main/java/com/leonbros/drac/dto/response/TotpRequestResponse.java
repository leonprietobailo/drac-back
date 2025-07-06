package com.leonbros.drac.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotpRequestResponse {

  public Status status;

  public enum Status {
    SUCCESS,
    TOO_MANY_TOTPS,
    UNEXPECTED_ERROR
  }

}
