package com.leonbros.drac.service;

import com.leonbros.drac.dto.response.ItemsResponse;
import com.leonbros.drac.entity.Item;
import com.leonbros.drac.entity.ItemColor;
import com.leonbros.drac.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ItemService {

  private static final String DEFAULT = "DEFAULT";

  private final ItemRepository itemRepository;

  @Autowired
  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public ItemsResponse getItems() {
    final List<Item> items = itemRepository.findAll();
    final List<ItemsResponse.ItemResponse> itemsResponse = new ArrayList<>();
    for (Item item : items) {
      itemsResponse.add(new ItemsResponse.ItemResponse(item.getItemPosition(), item.getTitle(),
          String.format("%.2f", item.getPrice()).replace(".", ","), item.getColors().stream().map(
              color -> new ItemsResponse.ItemResponse.ItemColorResponse(
                  color.getColor().equals(DEFAULT) ? "#000000" : color.getColor(), color.getUrl()))
          .toList()));
    }
    return new ItemsResponse(ItemsResponse.Status.SUCCESS, itemsResponse);
  }

}
