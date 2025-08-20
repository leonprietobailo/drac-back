package com.leonbros.drac.entity.order;

import lombok.Getter;

@Getter
public enum PaymentStatusValues {
  PENDING("PENDING");

  private final String status;

  PaymentStatusValues(String status) {
    this.status = status;
  }
}
