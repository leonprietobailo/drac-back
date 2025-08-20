package com.leonbros.drac.repository;

import com.leonbros.drac.entity.order.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface PaymentTransactionRepository
    extends JpaRepository<PaymentTransaction, String> {
  Optional<PaymentTransaction> findByIdentifier(String identifier);
}
