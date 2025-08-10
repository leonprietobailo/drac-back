package com.leonbros.drac.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddResponse {

  private  Status status;

  public enum Status {
    SUCCESS,
    MERGED,
    UNEXPECTED_ERROR
  }
}
