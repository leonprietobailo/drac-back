package com.leonbros.drac.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
public class UserRegistrationRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

  @NotNull
  private Boolean newsletter;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotNull
  private Date birthdate;

  @NotBlank
  private String phone;

  @NotBlank
  @Length(min = 4, max = 4)
  private String totp;

  private AddressRegistrationRequest address;

}
