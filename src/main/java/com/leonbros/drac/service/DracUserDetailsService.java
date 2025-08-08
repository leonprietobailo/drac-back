package com.leonbros.drac.service;

import com.leonbros.drac.entity.user.User;
import com.leonbros.drac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DracUserDetailsService  implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public DracUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = userRepository.getUserByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
        .password(user.getPassword()).roles(UserRoles.USER.getRole()).build();
  }
}
