package com.leonbros.drac.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddressDto {
  private Long id;
  private String city;
  private String province;
  private String street;
  private String flat;
  private String zip;
  private Boolean starred;
}
