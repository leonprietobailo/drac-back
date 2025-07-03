package com.leonbros.drac.controller;

import com.leonbros.drac.dto.request.UserRegistrationRequest;
import com.leonbros.drac.dto.response.UserRegistrationResponse;
import com.leonbros.drac.entity.User;
import com.leonbros.drac.service.UserService;
import org.apache.coyote.Response;
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
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("exists/{email}")
  public boolean userExists(@PathVariable String email) {
    return userService.emailExists(email);
  }

  @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserRegistrationResponse> register(
      @RequestBody UserRegistrationRequest userRegistrationRequest) {
    final User user = userService.registerUser(userRegistrationRequest);
    final UserRegistrationResponse userRegistrationResponse =
        new UserRegistrationResponse(user.getEmail());
    return ResponseEntity.ok(userRegistrationResponse);
  }

}
