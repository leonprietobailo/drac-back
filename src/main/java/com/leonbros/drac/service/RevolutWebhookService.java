package com.leonbros.drac.service;

import com.leonbros.drac.dto.request.webhook.RevolutRequest;
import com.leonbros.drac.entity.payment.PaymentTransaction;
import com.leonbros.drac.repository.PaymentTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RevolutWebhookService {

  private final PaymentTransactionRepository paymentTransactionRepository;

  @Autowired
  public RevolutWebhookService(PaymentTransactionRepository paymentTransactionRepository) {
    this.paymentTransactionRepository = paymentTransactionRepository;
  }

  @Async("webhookExecutor")
  public void updateRequest(RevolutRequest dto) {
    final Optional<PaymentTransaction> transaction =
        paymentTransactionRepository.findByIdentifier(dto.order_id());
    if (transaction.isEmpty()) {
      log.error("Transaction is not found.");
    }
    final PaymentTransaction paymentTransaction = transaction.get();
    paymentTransaction.setStatus(dto.event().toString());
    paymentTransactionRepository.save(paymentTransaction);

    // Generate Billing.

    // Generate Email.

  }




}
