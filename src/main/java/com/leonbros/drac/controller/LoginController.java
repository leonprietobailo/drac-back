package com.leonbros.drac.controller;

import com.leonbros.drac.dto.request.auth.LoginRequest;
import com.leonbros.drac.dto.response.auth.LoginResponse;
import com.leonbros.drac.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

  private final AuthenticationManager authenticationManager;

  private final JwtUtil jwtUtil;

  private final UserDetailsService userDetailsService;

  @Autowired
  public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
      UserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
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
}
