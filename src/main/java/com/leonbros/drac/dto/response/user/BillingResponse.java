package com.leonbros.drac.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BillingResponse {

  private Status status;
  private BillingInfoDto billing;

  public enum Status {
    SUCCESS, UNAUTHORIZED, UNEXPECTED_ERROR
  }
}
