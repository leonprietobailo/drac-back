package com.leonbros.drac.repository;

import com.leonbros.drac.entity.user.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipientRepository extends JpaRepository<Recipient,Long> {
  List<Recipient> getRecipientsByUserCod_Email(String userCodEmail);

  Recipient findByCodAndUserCod_Email(Long cod, String userCodEmail);
}
