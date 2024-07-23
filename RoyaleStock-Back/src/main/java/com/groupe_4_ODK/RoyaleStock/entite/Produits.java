package com.groupe_4_ODK.RoyaleStock.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;


@Entity
@Table(name="produit") @Data @AllArgsConstructor @NoArgsConstructor
public class Produits {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long Id;

  private String nom;
  private String description;
  private Double prixAchats;
  private Double prixVente;
  private Date dateExpiration;
  private int quantite;
  private long createBy;

  @JsonIgnore
  @OneToMany
  private List<DetailsEntrees> detailsEntrees;

  @JsonIgnore
  @OneToMany
  private List<DetailsSorties> detailsSorties;

  //@JsonIgnore
  @ManyToOne
  @JoinColumn(name = "categorie_id")
  private Categories categories;

 // @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "entrepot_id")
  private Entrepots entrepots;
}
