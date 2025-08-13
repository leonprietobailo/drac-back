package com.leonbros.drac.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthUtils {

  public static boolean isUserAuthenticated(Authentication auth) {
    return (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken));
  }
}
