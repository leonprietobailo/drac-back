package com.leonbros.drac.dto.external.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentObjectRequest {
  private Integer amount;
  private String currency;
  private String orderId;
  private String description;
  private CustomerDto customer;
  private BillingDetailsDto billingDetails;
  private String callbackUrl;
  private String completeUrl;
  private String cancelUrl;
  private String messageLanguage;
}
