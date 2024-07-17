package com.groupe_4_ODK.RoyaleStock.entite;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class DetailsSorties {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  private int quantite;

  private Float prix_unitaire;

  private  Float prix_total;

  private Date dateSortie;

  @ManyToOne
  @JoinColumn(name = "bonSortie_id")
  @JsonBackReference
  private BonSorties bonSorties;

  @ManyToOne
  @JoinColumn(name = "produit_id")
  private Produits produits;
}
