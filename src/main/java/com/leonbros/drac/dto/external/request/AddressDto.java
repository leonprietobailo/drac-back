package com.leonbros.drac.dto.external.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressDto {
  private String country;
  private String city;
  private String line1;
  private String zip;
}
