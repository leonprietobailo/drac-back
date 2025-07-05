package com.leonbros.drac.service;

import com.leonbros.drac.dto.request.UserRegistrationRequest;
import com.leonbros.drac.entity.Totp;
import com.leonbros.drac.entity.User;
import com.leonbros.drac.repository.TotpRepository;
import com.leonbros.drac.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final TotpRepository totpRepository;

  @Autowired
  public UserService(UserRepository userRepository, TotpRepository totpRepository) {
    this.userRepository = userRepository;
    this.totpRepository = totpRepository;
  }

  public boolean emailExists(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public User registerUser(UserRegistrationRequest user) {
    // VALIDATE TOTP
    final User userEntity = User.builder().email(user.getEmail()).password(user.getPassword())
        .firstName(user.getFirstName()).lastName(user.getLastName()).birthdate(user.getBirthdate())
        .telephone(user.getTelephone()).build();

    final User savedUser = userRepository.save(userEntity);
    savedUser.setPassword(user.getPassword());
    return savedUser;
  }

  public boolean requestTotp(UserRegistrationRequest request) {
    final Totp totp =
        Totp.builder().email(request.getEmail()).requestDate(new Date()).otp(9999).build();
    totpRepository.save(totp);
    return true;
  }
}
