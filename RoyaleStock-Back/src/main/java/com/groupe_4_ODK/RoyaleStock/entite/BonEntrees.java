package com.groupe_4_ODK.RoyaleStock.entite;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
  private  Double prixTotal;
  private  String statut;

  @ManyToOne
  @JoinColumn(name = "fournisseur_id")
  private Fournisseurs fournisseurs;

  @OneToMany
  @JsonManagedReference
  private List<DetailsEntrees> detailsEntrees;
  @ManyToOne
  private Manager manager;
  @ManyToOne
  private Admin admin;


}
