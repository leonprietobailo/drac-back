package com.leonbros.drac.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ShippingResponse {

  private Status status;

  private List<AddressDto> addresses;
  private List<BillingInfoDto> billingInfos;
  private List<RecipientDto> recipients;

  public enum Status {
    SUCCESS, UNAUTHORIZED, UNEXPECTED_ERROR
  }

}
