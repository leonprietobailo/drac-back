package com.leonbros.drac.entity.item;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="COLOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Color {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  private String color;

  @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemColor> itemColors;

  @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemImage> itemImages;

}
