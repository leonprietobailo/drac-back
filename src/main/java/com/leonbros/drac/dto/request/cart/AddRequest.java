package com.leonbros.drac.dto.request.cart;

import lombok.Getter;

@Getter
public class AddRequest {
  private Long productId;
  private Long sizeId;
  private Long colorId;
  private Integer quantity;
}
