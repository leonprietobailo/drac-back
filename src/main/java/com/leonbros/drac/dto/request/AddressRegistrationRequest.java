package com.leonbros.drac.dto.request;

import lombok.Getter;

@Getter
public class AddressRegistrationRequest {

  private String city;

  private String province;

  private String street;

  private String flat;

  private Integer postalCode;
}
