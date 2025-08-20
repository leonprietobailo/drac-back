package com.leonbros.drac.controller;

import com.leonbros.drac.dto.request.webhook.RevolutRequest;
import com.leonbros.drac.service.RevolutWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

  private final RevolutWebhookService revolutWebhookService;

  @Autowired
  public WebhookController(RevolutWebhookService revolutWebhookService) {
    this.revolutWebhookService = revolutWebhookService;
  }

  @PostMapping("revolut")
  public ResponseEntity<Void> postString(@RequestBody RevolutRequest dto) {
    revolutWebhookService.updateRequest(dto);
    return ResponseEntity.ok().build();
  }

}
