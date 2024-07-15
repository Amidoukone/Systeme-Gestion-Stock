package com.groupe_4_ODK.RoyaleStock.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "categorie") @Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Categories {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String libelle;

  @OneToMany
  private List<Produits> produits;
}
