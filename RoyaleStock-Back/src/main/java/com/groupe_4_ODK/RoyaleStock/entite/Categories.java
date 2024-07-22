package com.groupe_4_ODK.RoyaleStock.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
  private long createBy;

  @JsonIgnore
  @ManyToOne
  private Entrepots entrepot;

  @OneToMany
  private List<Produits> produits;

}
