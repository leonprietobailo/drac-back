package com.leonbros.drac.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationResponse {

  public Status status;

  public enum Status {
    SUCCESS,
    WRONG_TOTP,
    VALIDATION_FAILED,
    TOTP_EXPIRED,
    UNEXPECTED_ERROR

  }

}
