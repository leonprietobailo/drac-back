package com.leonbros.drac.dto.response;

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
    private String  price;
    private List<ItemColorResponse> colors;


    @Getter
    @AllArgsConstructor
    public static class ItemColorResponse {
      private String color;
      private String url;
    }
  }


  public enum Status {
    SUCCESS, UNEXPECTED_ERROR
  }
}
