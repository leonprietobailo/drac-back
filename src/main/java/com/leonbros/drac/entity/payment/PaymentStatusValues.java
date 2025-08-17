package com.leonbros.drac.entity.payment;

import lombok.Getter;

@Getter
public enum PaymentStatusValues {
  PENDING("P"),
  CANCELLED("C"),
  SUCCEEDED("S");

  private final String status;

  PaymentStatusValues(String status) {
    this.status = status;
  }
}
