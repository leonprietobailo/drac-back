package com.leonbros.drac.entity.order;


import com.leonbros.drac.entity.user.Address;
import com.leonbros.drac.entity.user.BillingInfo;
import com.leonbros.drac.entity.user.Recipient;
import com.leonbros.drac.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "ORDERS")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  @OneToOne
  @JoinColumn(name = "payment_transaction_cod")
  private PaymentTransaction paymentTransaction;

  private String addressType;

  @ManyToOne
  @JoinColumn(name = "shipping_address_cod")
  private Address shippingAddress;

  @ManyToOne
  @JoinColumn(name = "recipient_cod")
  private Recipient recipient;

  @ManyToOne
  @JoinColumn(name = "billing_info_cod")
  private BillingInfo billingInfo;

  @ManyToOne
  @JoinColumn(name = "billing_address_cod")
  private Address billingAddress;

  private Integer invoiceYear;

  private Integer invoiceNumber;

  private Double subtotal;

  private Double shipment;



  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderElement> orderElements;

}
