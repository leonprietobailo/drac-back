package com.leonbros.drac.repository;

import com.leonbros.drac.entity.payment.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentTransactionRepository
    extends JpaRepository<PaymentTransaction, String> {
}
