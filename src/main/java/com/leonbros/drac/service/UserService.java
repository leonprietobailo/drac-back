package com.leonbros.drac.service;

import com.leonbros.drac.dto.request.UserRegistrationRequest;
import com.leonbros.drac.entity.User;
import com.leonbros.drac.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
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
}
