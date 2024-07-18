package com.groupe_4_ODK.RoyaleStock.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "entrepot") @Data @AllArgsConstructor @NoArgsConstructor
public class Entrepots {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  private String nom;

  private String adresse;
  private String logo;
  private String lieu;
  private String statut;

  private Date dateCreate;
  private Date debutAbonnement;
  private Date finAbonnement;
  @OneToMany
  private List<Utilisateur> utilisateurs;

  @OneToMany()
  private List<Produits> produits;
}
