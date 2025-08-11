package com.leonbros.drac.service;

import com.leonbros.drac.dto.response.auth.LogoutResponse;
import com.leonbros.drac.entity.security.BlacklistedJwt;
import com.leonbros.drac.repository.BlacklistedJwtRepository;
import com.leonbros.drac.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

  private final JwtUtil jwtUtil;

  private final BlacklistedJwtRepository blacklistedJwtRepository;

  @Autowired
  public LoginService(JwtUtil jwtUtil, BlacklistedJwtRepository blacklistedJwtRepository) {
    this.jwtUtil = jwtUtil;
    this.blacklistedJwtRepository = blacklistedJwtRepository;
  }

  @Transactional
  public LogoutResponse logout(HttpServletRequest request) {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return new LogoutResponse(LogoutResponse.Status.INVALID_TOKEN);
    }
    final String token = authHeader.substring(7);
    blacklistedJwtRepository.save(
        new BlacklistedJwt(null, token, jwtUtil.getExpirationDateFromToken(token)));
    return new LogoutResponse(LogoutResponse.Status.SUCCESS);
  }
}
