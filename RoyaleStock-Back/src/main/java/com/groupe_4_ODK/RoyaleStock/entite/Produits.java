package com.groupe_4_ODK.RoyaleStock.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Table(name="produit")
@Data @AllArgsConstructor
@NoArgsConstructor
public class Produits {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long Id;

  private String nom;
  private String description;
  private Double prixAchats;
  private Double prixVente;
  private int quantite;

  @OneToMany
  private List<DetailsEntrees> detailsEntrees;

  @OneToMany
  private List<DetailsSorties> detailsSorties;

  @ManyToOne
  private Categories categories;

  @ManyToOne
  private Entrepots entrepots;
}
