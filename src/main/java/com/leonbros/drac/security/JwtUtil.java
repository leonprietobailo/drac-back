package com.leonbros.drac.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

  private final long jwtExpirationMs = 24 * 60 * 60 * 1000; // 24 hours

  private final SecretKey secretKey;
  private final JwtParser jwtParser;

  public JwtUtil(@Value("${jwt.secret}") String jwtSecret) {
    this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    this.jwtParser = Jwts.parser()
            .verifyWith(secretKey)
            .build();
  }

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(secretKey)
            .compact();
  }

  public String getUsernameFromToken(String token) {
    return jwtParser.parseSignedClaims(token).getPayload().getSubject();
  }

  public Date getExpirationDateFromToken(String token) {
    return jwtParser.parseSignedClaims(token).getPayload().getExpiration();
  }

  public boolean validateToken(String token) {
    try {
      jwtParser.parseSignedClaims(token); // Throws if invalid
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
