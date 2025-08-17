package com.leonbros.drac.service;

import com.leonbros.drac.dto.external.request.PaymentObjectRequest;
import com.leonbros.drac.dto.external.response.PaymentObjectResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

  private final String authToken;
  private final RestTemplate restTemplate;

  public ExternalApiService(@Value("${monei.secret}") String authToken, RestTemplate restTemplate) {
    this.authToken = authToken;
    this.restTemplate = restTemplate;
  }

  private static final String MONEI_URL = "https://api.monei.com/v1/payments";

  public PaymentObjectResponse generatePaymentGateway(PaymentObjectRequest payload)
      throws Non2xxException {
    // Headers
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set(HttpHeaders.AUTHORIZATION, authToken);
    // Entity
    final HttpEntity<PaymentObjectRequest> request = new HttpEntity<>(payload, headers);
    // Send Request
    final ResponseEntity<PaymentObjectResponse> response =
        restTemplate.postForEntity(MONEI_URL, request, PaymentObjectResponse.class);
    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new Non2xxException(response.getStatusCode(), response.getHeaders(),
          asStringSafe(response.getBody()));
    }
    return response.getBody();
  }

  private static String asStringSafe(PaymentObjectResponse body) {
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
