package com.leonbros.drac.dto.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserRegistrationRequest {

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private Date birthdate;

  private String phone;

  private AddressRegistrationRequest address;

  private Integer totp;
}
