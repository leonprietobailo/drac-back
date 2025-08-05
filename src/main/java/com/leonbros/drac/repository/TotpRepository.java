package com.leonbros.drac.repository;

import com.leonbros.drac.entity.user.Totp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TotpRepository extends JpaRepository<Totp, Long> {
  List<Totp> findTotpsByEmail(String email);
  List<Totp> findTotpsByEmailOrderByRequestDate(String email);
}
