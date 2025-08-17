package com.leonbros.drac.dto.response.checkout;

public record RequestPaymentResponse(Status status, String url) {

  public enum Status {
    UNEXPECTED_ERROR, NON_AUTHENTICATED, CART_NOT_FOUND, UNAUTHORIZED, EXTERNAL_API_ERROR, REDIRECT;
  }
}
