package com.leonbros.drac.service;

import com.leonbros.drac.dto.response.item.AttributeResponse;
import com.leonbros.drac.dto.response.item.ColorResponse;
import com.leonbros.drac.dto.response.item.ItemsResponse;
import com.leonbros.drac.dto.response.item.SizeResponse;
import com.leonbros.drac.dto.response.item.UrlResponse;
import com.leonbros.drac.entity.Item;
import com.leonbros.drac.entity.ItemAttribute;
import com.leonbros.drac.entity.ItemImage;
import com.leonbros.drac.repository.ItemRepository;
import jakarta.validation.constraints.Size;
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
          computeAttributes(item.getItemAttributes())));
    }
    return result;
  }

  private List<AttributeResponse> computeAttributes(List<ItemAttribute> attributes) {
    final List<AttributeResponse> result = new ArrayList<>();
    for (ItemAttribute attribute : attributes) {
      final SizeResponse sizeResponse = attribute.getItemSize() != null ?
          new SizeResponse(attribute.getItemSize().getSize()) :
          null;
      final ColorResponse colorResponse = attribute.getItemColor() != null ?
          new ColorResponse(attribute.getItemColor().getColor()) :
          null;
      result.add(new AttributeResponse(sizeResponse, colorResponse,
          computeUrls(attribute.getItemImages())));
    }
    return result;
  }

  private List<UrlResponse> computeUrls(List<ItemImage> itemImages) {
    final List<UrlResponse> result = new ArrayList<>();
    for (ItemImage itemImage : itemImages) {
      result.add(new UrlResponse(itemImage.getUrl()));
    }
    return result;
  }

}
