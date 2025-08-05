package com.leonbros.drac.entity.item;

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
@Table(name="ITEM_SIZE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSize {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  @ManyToOne
  @JoinColumn(name = "size_cod")
  private Size size;

  @ManyToOne
  @JoinColumn(name = "item_cod")
  private Item item;

}
