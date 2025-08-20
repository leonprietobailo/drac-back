package com.leonbros.drac.entity.order;

import com.leonbros.drac.entity.item.Color;
import com.leonbros.drac.entity.item.Item;
import com.leonbros.drac.entity.item.ItemImage;
import com.leonbros.drac.entity.item.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderElement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  @ManyToOne
  private Order order;

  @ManyToOne
  private Item item;

  @ManyToOne
  private Size selectedSize;

  @ManyToOne
  private Color selectedColor;

  private Integer quantity;

}
