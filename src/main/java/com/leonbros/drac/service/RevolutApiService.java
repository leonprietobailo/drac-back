package com.leonbros.drac.service;

import com.leonbros.drac.dto.external.request.PaymentObjectRequest;
import com.leonbros.drac.dto.external.response.PaymentObjectResponse;
import com.leonbros.drac.dto.external.revolut.request.OrderBody;
import com.leonbros.drac.dto.external.revolut.response.OrderResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RevolutApiService {

  private final String authToken;
  private final RestTemplate restTemplate;

  public RevolutApiService(@Value("${revolut.secret}") String authToken, RestTemplate restTemplate) {
    this.authToken = authToken;
    this.restTemplate = restTemplate;
  }

  private static final String REVOLUT_URL = "https://sandbox-merchant.revolut.com/api/orders";

  public OrderResponse generatePaymentGateway(OrderBody payload)
      throws Non2xxException {
    // Headers
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(authToken);
    headers.set("Revolut-Api-Version", "2024-09-01");
    // Entity
    final HttpEntity<OrderBody> request = new HttpEntity<>(payload, headers);
    // Send Request
    final ResponseEntity<OrderResponse> response =
        restTemplate.postForEntity(REVOLUT_URL, request, OrderResponse.class);
    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new Non2xxException(response.getStatusCode(), response.getHeaders(),
          asStringSafe(response.getBody()));
    }
    return response.getBody();
  }

  public static String asStringSafe(OrderResponse body) {
    return body == null ? null : body.toString(); // or serialize to JSON
  }

  @Getter
  public static class Non2xxException extends Exception {
    private final HttpStatusCode status;
    private final HttpHeaders headers;
    private final String responseBody;

    public Non2xxException(HttpStatusCode status, HttpHeaders headers, String responseBody) {
      super("Non-2xx response: " + status);
      this.status = status;
      this.headers = headers;
      this.responseBody = responseBody;
    }

  }
}
