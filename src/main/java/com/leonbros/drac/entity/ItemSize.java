package com.leonbros.drac.entity;

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
@Table(name="ITEM_SIZE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSize {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  private String size;

  @OneToMany(mappedBy = "itemSize", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemAttribute> itemSizes;

}
