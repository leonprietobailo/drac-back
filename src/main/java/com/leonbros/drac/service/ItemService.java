package com.leonbros.drac.service;

import com.leonbros.drac.dto.response.item.ColorResponse;
import com.leonbros.drac.dto.response.item.ItemColorResponse;
import com.leonbros.drac.dto.response.item.ItemSizeResponse;
import com.leonbros.drac.dto.response.item.ItemsResponse;
import com.leonbros.drac.dto.response.item.SizeResponse;
import com.leonbros.drac.dto.response.item.ItemImageResponse;
import com.leonbros.drac.entity.item.Item;
import com.leonbros.drac.entity.item.ItemColor;
import com.leonbros.drac.entity.item.ItemImage;
import com.leonbros.drac.entity.item.ItemSize;
import com.leonbros.drac.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ItemService {

  private final ItemRepository itemRepository;

  @Autowired
  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public ItemsResponse getItems() {
    final List<Item> items = itemRepository.findAll();
    return new ItemsResponse(ItemsResponse.Status.SUCCESS, computeItems(items));
  }

  private List<ItemsResponse.ItemResponse> computeItems(List<Item> items) {
    final List<ItemsResponse.ItemResponse> result = new ArrayList<>();
    for (Item item : items) {
      result.add(new ItemsResponse.ItemResponse(item.getItemPosition(), item.getTitle(),
          item.getDescription(), String.format("%.2f", item.getPrice()).replace(".", ","),
          computeColors(item.getItemColors()), computeSizes(item.getItemSizes()),
          computeImages(item.getItemImages())));
    }
    return result;
  }

  private static List<ItemColorResponse> computeColors(List<ItemColor> itemColors) {
    return itemColors.stream().map(
        itemColor -> new ItemColorResponse(itemColor.getColor().getColor(),
            computeImages(itemColor.getColor().getItemImages()))).toList();
  }

  private static List<ItemSizeResponse> computeSizes(List<ItemSize> itemSizes) {
    return itemSizes.stream().map(itemSize -> new ItemSizeResponse(itemSize.getSize().getSize()))
        .toList();
  }

  private static List<ItemImageResponse> computeImages(List<ItemImage> itemImages) {
    return itemImages.stream().map(itemImage -> new ItemImageResponse(itemImage.getUrl())).toList();
  }

}
