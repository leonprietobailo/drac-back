package com.leonbros.drac.dto.external.revolut.response;

import lombok.Getter;

@Getter
public class PaymentMethodResponse {

  public Type type;

  public enum Type {
    apple_pay, card, google_pay, revolut_pay_card, revolut_pay_account;
  }
}
