package com.leonbros.drac.repository;

import com.leonbros.drac.entity.cart.Cart;
import com.leonbros.drac.entity.cart.CartItem;
import com.leonbros.drac.entity.item.Color;
import com.leonbros.drac.entity.item.Item;
import com.leonbros.drac.entity.item.ItemColor;
import com.leonbros.drac.entity.item.ItemSize;
import com.leonbros.drac.entity.item.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  CartItem getByCartAndItemAndItemSizeAndItemColor(Cart cart, Item item, ItemSize itemSize, ItemColor itemColor);

  CartItem getByCartAndItemAndSizeAndColor(Cart cart, Item item, Size size, Color color);
}
