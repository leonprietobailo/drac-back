package com.leonbros.drac.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BillingInfoDto {
  private Long id;
  private String entityName;
  private String email;
  private String taxId;
  private Boolean starred;
}
