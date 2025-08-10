package com.leonbros.drac.service;

import com.leonbros.drac.entity.cart.Cart;
import com.leonbros.drac.repository.CartRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  private static final String CART_TOKEN = "CART_TOKEN";

  private final HttpSession httpSession;

  private final CartRepository cartRepository;

  @Autowired
  public CartService(HttpSession httpSession, CartRepository cartRepository) {
    this.httpSession = httpSession;
    this.cartRepository = cartRepository;
  }

  private Cart getOrCreateCart() {
    final Cart sessionCart =
        cartRepository.findCartByCod((Long) httpSession.getAttribute(CART_TOKEN)).orElse(null);
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Cart userCart = null;
    if (auth.isAuthenticated()) {
      userCart = cartRepository.findCartByUser_Email(auth.getName()).orElse(null);
    }



  }

}
