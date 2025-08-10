package com.leonbros.drac.repository;

import com.leonbros.drac.entity.item.ItemColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemColorRepository extends JpaRepository <ItemColor, Long> {
  ItemColor getByCod(Long cod);
}
