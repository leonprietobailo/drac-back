package com.leonbros.drac.repository;

import com.leonbros.drac.entity.item.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Long> {
  Size getByCod(Long cod);

  Size findByCod(Long cod);

  Size findBySize(String size);
}
