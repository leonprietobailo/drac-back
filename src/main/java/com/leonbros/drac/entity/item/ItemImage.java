package com.leonbros.drac.entity.item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ITEM_IMAGE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  private String url;

  @ManyToOne
  @JoinColumn(name = "item_cod")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "color_cod")
  private Color color;

}
