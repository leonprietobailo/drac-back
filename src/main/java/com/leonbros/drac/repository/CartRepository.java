package com.leonbros.drac.repository;

import com.leonbros.drac.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
  Optional<Cart> findCartByCod(Long cod);

  Optional<Cart> findCartByUser_Email(String userEmail);

  Cart findCartByToken(String token);
}
