package com.leonbros.drac.repository;

import com.leonbros.drac.entity.security.BlacklistedJwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistedJwtRepository extends JpaRepository<BlacklistedJwt, Long> {
  Optional<BlacklistedJwt> getBlacklistedJwtByJwt(String jwt);
}
