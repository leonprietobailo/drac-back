package com.leonbros.drac.service;


import lombok.Getter;

@Getter
public enum UserRoles {

  USER("USER");

  private final String role;

  UserRoles(String role) {
    this.role = role;
  }

}
