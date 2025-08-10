package com.leonbros.drac.entity.user;

import com.leonbros.drac.entity.cart.Cart;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  private String email;

  private String password;

  private Boolean newsletter;

  private String firstName;

  private String lastName;

  private Date birthdate;

  private String telephone;

//  @OneToOne(mappedBy = "size", cascade = CascadeType.ALL, orphanRemoval = true)
//  private Cart cart;

}
