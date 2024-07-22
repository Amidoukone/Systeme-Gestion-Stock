package com.groupe_4_ODK.RoyaleStock.entite;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

  private  int quantite;
  private double prixUnitaire;

  @ManyToOne
  @JoinColumn(name = "bonEntre_id")
  @JsonBackReference
  private BonEntrees bonEntrees;

  @ManyToOne
  @JoinColumn(name = "produit_id")
  private Produits produits;

}
