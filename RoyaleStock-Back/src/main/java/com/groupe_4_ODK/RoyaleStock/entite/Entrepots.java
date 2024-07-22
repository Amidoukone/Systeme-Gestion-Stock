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
  private Long id;

  private String nom;

  private String adresse;
  private String logo;
  @Temporal(TemporalType.DATE)
  @Column(name = "dateCreate", nullable = false, updatable = false)
  private Date dateCreate = new Date();
  @Temporal(TemporalType.DATE)
  private Date debutAbonnement;
  @Temporal(TemporalType.DATE)
  private Date finAbonnement;
  private Long createBy;

  @OneToMany(mappedBy = "entrepot", cascade = CascadeType.ALL)
  private List<Utilisateur> utilisateurs;

  @OneToMany()
  private List<Produits> produits;
}
