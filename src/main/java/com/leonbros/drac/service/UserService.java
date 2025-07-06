package com.leonbros.drac.service;

import com.leonbros.drac.dto.request.TotpRequest;
import com.leonbros.drac.dto.request.UserRegistrationRequest;
import com.leonbros.drac.dto.response.TotpRequestResponse;
import com.leonbros.drac.entity.Totp;
import com.leonbros.drac.entity.User;
import com.leonbros.drac.repository.TotpRepository;
import com.leonbros.drac.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

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
        .telephone(user.getPhone()).build();

    final User savedUser = userRepository.save(userEntity);
    savedUser.setPassword(user.getPassword());
    return savedUser;
  }

  @Transactional
  public TotpRequestResponse.Status requestTotp(TotpRequest request) {
    // Get Existing TOTPs
    final List<Totp> existingTotps = totpRepository.findTotpsByEmail(request.getEmail());
    final Instant oneHourAgo = Instant.now().minus(Duration.ofHours(1));
    final List<Totp> lastHourTotps =
        existingTotps.stream().filter(totp -> totp.getRequestDate().toInstant().isAfter(oneHourAgo))
            .toList();
    if (lastHourTotps.size() >= 5) {
      return TotpRequestResponse.Status.TOO_MANY_TOTPS;
    }
    // Create new TOTP
    final Totp totp =
        Totp.builder().email(request.getEmail()).requestDate(new Date()).otp(9999).build();
    totpRepository.save(totp);
    // TODO: Generate Corresponding Email.
    return TotpRequestResponse.Status.SUCCESS;
  }
}
