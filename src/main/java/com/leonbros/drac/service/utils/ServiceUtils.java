package com.leonbros.drac.service.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
public class ServiceUtils {

  public static Optional<String> readCookie(HttpServletRequest req, String name) {
    Cookie[] cookies = req.getCookies();
    if (cookies == null) return Optional.empty();
    for (Cookie c : cookies) {
      if (name.equals(c.getName())) {
        String v = c.getValue();
        if (v != null && !v.isBlank()) return Optional.of(v.trim());
      }
    }
    return Optional.empty();
  }
}
