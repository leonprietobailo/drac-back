package com.leonbros.drac.entity.order;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  private String identifier;

  private Date createdAt;

  private String status;

  private String paymentId;

  private String paymentMethod;

  @ManyToOne
  @JoinColumn(name = "user_cod")
  private User user;

  @OneToOne(mappedBy = "paymentTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
  private Order order;
}
