package com.leonbros.drac.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartDto {
  private double subtotal;
  private double shipment;
  private double total;
  private double totalNoVat;
  private List<CartItemResponse> items;
}
