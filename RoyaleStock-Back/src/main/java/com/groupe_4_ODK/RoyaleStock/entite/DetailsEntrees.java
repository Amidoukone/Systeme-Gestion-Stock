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
class DetailsEntrees {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  private Date dateExpiration;

  @ManyToOne
  @JoinColumn(name = "bonEntre_id")
  private BonEntrees bonEntrees;

  @ManyToOne
  @JoinColumn(name = "produit_id")
  private Produits produits;

}
