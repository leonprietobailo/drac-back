package com.leonbros.drac.dto.response.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ItemsResponse {

  private Status status;

  private List<ItemResponse> items;


  @Getter
  @Builder
  @AllArgsConstructor
  public static class ItemResponse {
    private Long id;
    private String title;
    private String description;
    private String  price;
    private List<AttributeResponse> attributes;
  }


  public enum Status {
    SUCCESS, UNEXPECTED_ERROR
  }
}
