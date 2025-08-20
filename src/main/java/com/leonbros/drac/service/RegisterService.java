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
import com.leonbros.drac.port.EmailGateway;
import com.leonbros.drac.repository.AddressRepository;
import com.leonbros.drac.repository.TotpRepository;
import com.leonbros.drac.repository.UserRepository;
import com.leonbros.drac.utils.ResourceUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class RegisterService {

  private static final String VERIFICATION_EMAIL_TEMPLATE = "verification_email.ftl";

  private final Configuration freeMarkerConfiguration;

  private final UserRepository userRepository;
  private final TotpRepository totpRepository;
  private final AddressRepository addressRepository;

  private final PasswordEncoder passwordEncoder;

  private final EmailGateway emailGateway;

  @Autowired
  public RegisterService(Configuration freeMarkerConfiguration,
      UserRepository userRepository, TotpRepository totpRepository,
      AddressRepository addressRepository, PasswordEncoder passwordEncoder,
      EmailGateway emailGateway) {
    this.freeMarkerConfiguration = freeMarkerConfiguration;
    this.userRepository = userRepository;
    this.totpRepository = totpRepository;
    this.addressRepository = addressRepository;
    this.passwordEncoder = passwordEncoder;
    this.emailGateway = emailGateway;
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
    final Totp lastTotp = totps.getLast();
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
    final Random random = new Random();
    final Totp totp = Totp.builder().email(request.getEmail()).requestDate(new Date())
        .otp(String.format("%04d", random.nextInt(10000))).build();
    totpRepository.save(totp);

    // Generate email.

    try {
      final Template template = freeMarkerConfiguration.getTemplate(VERIFICATION_EMAIL_TEMPLATE);
      final Map<String, Object> dataModel = new HashMap<>();
      dataModel.put("user", request.getFirstName());
      dataModel.put("totp", totp.getOtp());
      final StringWriter sw = new StringWriter();
      template.process(dataModel, sw);
      emailGateway.sendHtmlMail(totp.getEmail(), "Codi de verificació.", sw.toString());
    } catch (IOException e) {
      log.error("Failed to retrieve template. : {}. User Name: {} Totp: {} Exception Message: {}",
          VERIFICATION_EMAIL_TEMPLATE, request.getFirstName(), totp.getOtp(), e.getMessage(), e);
      return TotpRequestResponse.Status.UNEXPECTED_ERROR;
    } catch (TemplateException e) {
      log.error("Exception while parsing template. Template: {}. Exception Message: {}",
          VERIFICATION_EMAIL_TEMPLATE, e.getMessage(), e);
      return TotpRequestResponse.Status.UNEXPECTED_ERROR;
    }

    return TotpRequestResponse.Status.SUCCESS;
  }
}
