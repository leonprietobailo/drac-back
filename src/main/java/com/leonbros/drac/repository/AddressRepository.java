package com.leonbros.drac.repository;

import com.leonbros.drac.dto.response.user.AddressDto;
import com.leonbros.drac.entity.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

  List<Address> getAddressesByUserCod_Email(String userCodEmail);

  Address findAddressByCodAndUserCod_Email(Long cod, String userCodEmail);
}
