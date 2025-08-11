package com.leonbros.drac.dto.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ItemColorResponse {
  private Long id;
  private String color;
  private List<ItemImageResponse> images;
}
