package com.leonbros.drac.dto.external.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
