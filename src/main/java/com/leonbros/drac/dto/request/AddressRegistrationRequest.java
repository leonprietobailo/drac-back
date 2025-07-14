package com.leonbros.drac.dto.request;

import com.leonbros.drac.dto.validation.annotations.AddressGroup;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@AddressGroup
public class AddressRegistrationRequest {

  private String city;

  private String province;

  private String streetNumber;

  private String blockFlat;

  private String postalCode;
}
