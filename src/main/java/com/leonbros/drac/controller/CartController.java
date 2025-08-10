package com.leonbros.drac.controller;

import com.leonbros.drac.dto.request.cart.AddRequest;
import com.leonbros.drac.dto.response.cart.AddResponse;
import com.leonbros.drac.dto.response.cart.CartResponse;
import com.leonbros.drac.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

  private final CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CartResponse> addCart() {

  }

  @PostMapping(value = "add-item", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AddResponse> addItem(HttpServletRequest req, HttpServletResponse resp,
      AddRequest add) {
    return ResponseEntity.ok(cartService.addToCart(req, resp, add));
  }

}
