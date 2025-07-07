package com.leonbros.drac.entity;


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

import java.util.Date;

@Entity
@Table(name = "TOTP")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Totp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cod;

  private String email;

  private String otp;

  private Date requestDate;

}
