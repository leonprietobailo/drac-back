package com.leonbros.drac.repository;

import com.leonbros.drac.entity.item.Item;
import com.leonbros.drac.entity.item.ItemSize;
import com.leonbros.drac.entity.item.Size;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ItemSizeRepository  extends JpaRepository<ItemSize, Long> {
  ItemSize getByCod(Long sizeId);

}
