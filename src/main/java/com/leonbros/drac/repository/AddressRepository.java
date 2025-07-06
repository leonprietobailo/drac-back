package com.leonbros.drac.repository;

import com.leonbros.drac.entity.Address;
import com.leonbros.drac.entity.Totp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
