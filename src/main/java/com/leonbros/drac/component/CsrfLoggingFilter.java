package com.leonbros.drac.component;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Debugging class for CSRF.
 */
@Deprecated
public class CsrfLoggingFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    String csrfHeader = request.getHeader("X-XSRF-TOKEN");
    System.out.println("CSRF HEADER: " + csrfHeader);
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        System.out.println("COOKIE: " + cookie.getName() + " = " + cookie.getValue());
      }
    }
    filterChain.doFilter(request, response);
  }
}
