package com.leonbros.drac.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="ITEM_ATTRIBUTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemAttribute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  @ManyToOne
  @JoinColumn(name = "item_cod")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "item_color_cod")
  private ItemColor itemColor;

  @ManyToOne
  @JoinColumn(name = "item_size_cod")
  private ItemSize itemSize;

  @OneToMany(mappedBy = "itemAttribute", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemImage> itemImages;

}
