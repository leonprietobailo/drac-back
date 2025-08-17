package com.leonbros.drac.repository;

import com.leonbros.drac.entity.item.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Integer> {
  Color getByCod(Long cod);

  Color findByColor(String color);
}
