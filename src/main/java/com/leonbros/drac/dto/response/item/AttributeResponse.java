package com.leonbros.drac.dto.response.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AttributeResponse {

  private SizeResponse size;
  private ColorResponse color;
  private List<UrlResponse> urls;
}
