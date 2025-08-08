package com.leonbros.drac.service;

import com.leonbros.drac.dto.request.AddressRegistrationRequest;
import com.leonbros.drac.dto.request.TotpRequest;
import com.leonbros.drac.dto.request.UserRegistrationRequest;
import com.leonbros.drac.dto.response.TotpRequestResponse;
import com.leonbros.drac.dto.response.UserRegistrationResponse;
import com.leonbros.drac.dto.validation.AddressGroupValidation;
import com.leonbros.drac.entity.user.Address;
import com.leonbros.drac.entity.user.Totp;
import com.leonbros.drac.entity.user.User;
import com.leonbros.drac.repository.AddressRepository;
import com.leonbros.drac.repository.TotpRepository;
import com.leonbros.drac.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final TotpRepository totpRepository;

  private final AddressRepository addressRepository;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, TotpRepository totpRepository,
      AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.totpRepository = totpRepository;
    this.addressRepository = addressRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean emailExists(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public UserRegistrationResponse.Status registerUser(UserRegistrationRequest user) {
    // VALIDATE TOTP
    final List<Totp> totps = totpRepository.findTotpsByEmailOrderByRequestDate(user.getEmail());
    if (totps.isEmpty()) {
      return UserRegistrationResponse.Status.UNEXPECTED_ERROR;
    }
    final Totp lastTotp = totps.getFirst();
    final Instant twentyMinutesAgo = Instant.now().minus(Duration.ofMinutes(20));
    if (lastTotp.getRequestDate().toInstant().isBefore(twentyMinutesAgo)) {
      return UserRegistrationResponse.Status.TOTP_EXPIRED;
    }
    if (!lastTotp.getOtp().equals(user.getTotp())) {
      return UserRegistrationResponse.Status.WRONG_TOTP;
    }
    // Persist User
    final User userEntity =
        new User(null, user.getEmail(), passwordEncoder.encode(user.getPassword()),
            user.getNewsletter(), user.getFirstName(), user.getLastName(), user.getBirthdate(),
            user.getPhone());
    final User persistedUser = userRepository.save(userEntity);

    // Address being present.
    final AddressRegistrationRequest address = user.getAddress();
    if (!AddressGroupValidation.isEmptyAddress(address)) {
      final Address addressEntity =
          new Address(null, persistedUser, address.getCity(), address.getProvince(),
              address.getStreetNumber(), address.getBlockFlat(), address.getPostalCode());
      addressRepository.save(addressEntity);
    }
    return UserRegistrationResponse.Status.SUCCESS;
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
        Totp.builder().email(request.getEmail()).requestDate(new Date()).otp("9999").build();
    totpRepository.save(totp);
    // TODO: Generate Corresponding Email.
    return TotpRequestResponse.Status.SUCCESS;
  }
}
