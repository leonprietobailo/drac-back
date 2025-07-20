package com.leonbros.drac.controller;

import com.leonbros.drac.dto.response.ItemsResponse;
import com.leonbros.drac.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemsController {

  private final ItemService itemService;

  @Autowired
  public ItemsController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ItemsResponse getItems() {
    return itemService.getItems();
  }

}
