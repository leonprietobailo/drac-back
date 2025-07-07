package com.leonbros.drac.dto.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserRegistrationRequest {

  private String email;

  private String password;

  private Boolean newsletter;

  private String firstName;

  private String lastName;

  private Date birthdate;

  private String phone;

  private String totp;

  private AddressRegistrationRequest address;

}
