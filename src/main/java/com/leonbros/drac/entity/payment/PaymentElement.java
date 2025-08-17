package com.leonbros.drac.entity.payment;

import com.leonbros.drac.entity.item.Color;
import com.leonbros.drac.entity.item.Item;
import com.leonbros.drac.entity.item.Size;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentElement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  @ManyToOne
  @JoinColumn(name = "payment_transaction_cod")
  private PaymentTransaction paymentTransaction;

  @ManyToOne
  @JoinColumn(name = "item_cod")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "size_cod")
  private Size selectedSize;

  @ManyToOne
  @JoinColumn(name = "color_cod")
  private Color selectedColor;

  private Integer quantity;
}
