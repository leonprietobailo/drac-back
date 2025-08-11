package com.leonbros.drac.dto.response.auth;

public record LogoutResponse(Status status) {

  public enum Status {
    SUCCESS,
    INVALID_TOKEN,
    UNEXPECTED_ERROR
  }
}
