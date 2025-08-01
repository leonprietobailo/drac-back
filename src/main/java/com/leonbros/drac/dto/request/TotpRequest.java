package com.leonbros.drac.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TotpRequest {

  @NotBlank
  @Email
  private String email;
}
