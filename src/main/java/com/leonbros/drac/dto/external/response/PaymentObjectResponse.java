package com.leonbros.drac.dto.external.response;

import com.leonbros.drac.dto.external.request.BillingDetailsDto;
import com.leonbros.drac.dto.external.request.CustomerDto;
import com.leonbros.drac.dto.external.request.PaymentObjectRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PaymentObjectResponse {
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
  private NextActionDto nextAction;
}
