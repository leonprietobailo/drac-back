package com.leonbros.drac.service;

import com.leonbros.drac.dto.request.webhook.RevolutRequest;
import com.leonbros.drac.entity.order.PaymentTransaction;
import com.leonbros.drac.repository.PaymentTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RevolutWebhookService {

  private final PaymentTransactionRepository paymentTransactionRepository;

  private final EmailOrderConfirmationService emailOrderConfirmationService;

  @Autowired
  public RevolutWebhookService(PaymentTransactionRepository paymentTransactionRepository,
      EmailOrderConfirmationService emailOrderConfirmationService) {
    this.paymentTransactionRepository = paymentTransactionRepository;
    this.emailOrderConfirmationService = emailOrderConfirmationService;
  }

  @Async("webhookExecutor")
  public void updateRequest(RevolutRequest dto) {
    final Optional<PaymentTransaction> transaction =
        paymentTransactionRepository.findByIdentifier(dto.order_id());
    if (transaction.isEmpty()) {
      log.error("Transaction is not found.");
      return;
    }
    final PaymentTransaction paymentTransaction = transaction.get();
    paymentTransaction.setStatus(dto.event().toString());
    paymentTransactionRepository.save(paymentTransaction);

    // Generate Billing.

    // Generate Email.
    emailOrderConfirmationService.sendOrderConfirmationEmail(transaction.get().getIdentifier());
  }




}
