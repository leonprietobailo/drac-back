package com.leonbros.drac.repository;

import com.leonbros.drac.entity.user.BillingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingInfoRepository extends JpaRepository<BillingInfo,Long> {
  List<BillingInfo> getBillingInfoByUserCod_Email(String userCodEmail);

  BillingInfo findBillingInfoByCodAndUserCod_Email(Long cod, String userCodEmail);
}
