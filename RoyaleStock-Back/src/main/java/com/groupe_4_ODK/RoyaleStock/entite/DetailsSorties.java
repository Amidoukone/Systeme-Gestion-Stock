package com.groupe_4_ODK.RoyaleStock.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="detailssorties")
@NoArgsConstructor
@AllArgsConstructor
@Data
class DetailsSorties {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  private  int quantite;

  private String motif;

  private Float prix_unitaire;

  private  Float prix_total;

  private Date dateSortie;

  @ManyToOne
  @JoinColumn(name = "bonSortie_id")
  private BonSorties bonSorties;

  @ManyToOne
  @JoinColumn(name = "produit_id")
  private Produits produits;
}
