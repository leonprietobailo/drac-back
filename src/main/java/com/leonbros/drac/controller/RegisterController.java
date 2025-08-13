package com.leonbros.drac.controller;

import com.leonbros.drac.dto.request.TotpRequest;
import com.leonbros.drac.dto.request.UserRegistrationRequest;
import com.leonbros.drac.dto.response.TotpRequestResponse;
import com.leonbros.drac.dto.response.UserRegistrationResponse;
import com.leonbros.drac.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users/register")
public class RegisterController {

  private final RegisterService registerService;

  @Autowired
  public RegisterController(RegisterService registerService) {
    this.registerService = registerService;
  }

  @GetMapping("email-registered/{email}")
  public boolean userExists(@PathVariable String email) {
    return registerService.emailExists(email);
  }

  @PostMapping(value = "persist", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserRegistrationResponse> register(
      @Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
    final UserRegistrationResponse response =
        new UserRegistrationResponse(registerService.registerUser(userRegistrationRequest));
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "totp", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TotpRequestResponse> requestTotp(@RequestBody TotpRequest request) {
    final TotpRequestResponse response = new TotpRequestResponse(registerService.requestTotp(request));
    return ResponseEntity.ok(response);
  }

}
