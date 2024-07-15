package com.groupe_4_ODK.RoyaleStock.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Table(name = "fournisseur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fournisseurs {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String nom;

  private String adresse;

  private String Telephone;

  @OneToMany
  private List<BonEntrees> bonEntrees;


}
