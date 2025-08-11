package com.leonbros.drac.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteResponse {

  private  Status status;

  public enum Status {
    SUCCESS,
    UNEXPECTED_ERROR
  }
}
