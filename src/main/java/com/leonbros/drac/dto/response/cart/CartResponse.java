package com.leonbros.drac.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartResponse {

  private Status status;

  private CartDto cart;

  public enum Status {
    SUCCESS,
    UNEXPECTED_ERROR
  }

}
