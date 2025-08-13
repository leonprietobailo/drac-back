package com.leonbros.drac.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  @ManyToOne
  @JoinColumn(name = "user_cod")
  private User userCod;

  private String firstname;

  private String surname;

  private String telephone;

  @Builder.Default
  private Boolean starred = false;

}
