package com.leonbros.drac.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartItemResponse {
  private Long id;
  private Long itemId;
  private String url;
  private String title;
  private String selectedColor;
  private String selectedSize;
  private int quantity;
  private double price;
  private List<String> colors;
  private List<String> sizes;
}
