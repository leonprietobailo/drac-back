package com.leonbros.drac.dto.external.revolut.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderBody(Integer amount, String currency, String description, String email,
                        Date dateOfBirth, EnforceChallengeValues enforceChallenge) {

  @Getter
  public enum EnforceChallengeValues {
    AUTOMATIC("automatic"), FORCED("forced");

    private final String value;

    EnforceChallengeValues(String value) {
      this.value = value;
    }
  }

}
