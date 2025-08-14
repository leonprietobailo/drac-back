package com.leonbros.drac.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BillingInfoDto {
  private Long id;
  private String entityName;
  private String email;
  private String taxId;
  private Boolean starred;
}
