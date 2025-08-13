package com.leonbros.drac.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecipientDto {
  private Long id;
  private String name;
  private String surname;
  private String phone;
  private Boolean starred;
}
