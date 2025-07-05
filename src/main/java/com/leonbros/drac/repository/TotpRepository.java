package com.leonbros.drac.repository;

import com.leonbros.drac.entity.Totp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TotpRepository extends JpaRepository<Totp, Long> {
  boolean existsTotpByEmail(String email);
  Optional<Totp> findTopByEmail(String email);
}
