package com.leonbros.drac.controller;

import com.leonbros.drac.dto.request.auth.LoginRequest;
import com.leonbros.drac.dto.response.auth.LoginResponse;
import com.leonbros.drac.dto.response.auth.LogoutResponse;
import com.leonbros.drac.security.JwtUtil;
import com.leonbros.drac.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class LoginController {

  private final AuthenticationManager authenticationManager;

  private final JwtUtil jwtUtil;

  private final LoginService loginService;

  @Autowired
  public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
      LoginService loginService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
    return ResponseEntity.ok(new LoginResponse(token));
  }

  @GetMapping("/logout")
  public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
    return ResponseEntity.ok(loginService.logout(request));
  }

  @GetMapping("/verify")
  public ResponseEntity<?> verify(Principal principal) {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
