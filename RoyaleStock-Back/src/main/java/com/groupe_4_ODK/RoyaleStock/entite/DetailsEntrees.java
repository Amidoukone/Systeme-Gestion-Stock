package com.groupe_4_ODK.RoyaleStock.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="detailsentrees")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailsEntrees {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  private Date dateExpiration;

  @ManyToOne
  private BonEntrees bonEntrees;

  @ManyToOne
  private Produits produits;

}
