package com.leonbros.drac.repository;

import com.leonbros.drac.entity.Address;
import com.leonbros.drac.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
