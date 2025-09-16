package com.leonbros.drac.dto.external.revolut.response;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponse {
  private String id;
  private String token;
  private String type;
  private String state;
  private String createdAt;
  private String updatedAt;
  private Integer amount;
  private String currency;
  private String outstandingAmount;
  private String captureMode;
  private String checkout_url;
  private String enforceChallenge;
  private List<PaymentResponse> payments;
}
