package com.groupe_4_ODK.RoyaleStock.entite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bonentree")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BonEntrees {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long Id;
  private Date dateCommande;
  private  int quantite;
  private  Double prixTotal;
  private  String statut;

  @ManyToOne
  private Fournisseurs fournisseurs;

  @OneToMany
  private List<DetailsEntrees> detailsEntrees;


}
