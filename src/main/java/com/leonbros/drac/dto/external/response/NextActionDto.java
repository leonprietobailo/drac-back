package com.leonbros.drac.dto.external.response;

public record NextActionDto(Type type, boolean mustRedirect, String redirectUrl) {

  public enum Type {
    CONFIRM, CHALLENGE, FRICTIONLESS_CHALLENGE, BIZUM_CHALLENGE, COMPLETE;
  }
}
