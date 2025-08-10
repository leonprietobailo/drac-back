package com.leonbros.drac.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This class sets the {@link SecurityContextHolder} authentication based on a header containing a
 * JWT. If no valid JWT is present, the request proceeds as unauthorized. User being authorized or
 * not must be verified at endpoint level.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  @Autowired
  public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String token = authHeader.substring(7);
    try {
      final String username =
          jwtUtil.getUsernameFromToken(token); // make sure this doesn't throw except we catch it
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        // Prefer a validate method that returns boolean (no throws)
        if (jwtUtil.validateToken(token)) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(userDetails, null,
                  userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    } catch (ExpiredJwtException e) {
      // token expired -> proceed as non auth user.
    } catch (JwtException | IllegalArgumentException e) {
      // malformed/invalid token → ignore and proceed as anonymous
    }
    filterChain.doFilter(request, response);
  }

}
