package com.groupe_4_ODK.RoyaleStock.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.groupe_4_ODK.RoyaleStock.enums.Statut;
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
  @Enumerated(EnumType.STRING)
  private Statut statut;

  @ManyToOne
  @JoinColumn(name = "fournisseur_id")
  private Fournisseurs fournisseurs;

  @JsonIgnore
  @OneToMany
  @JsonManagedReference
  private List<DetailsEntrees> detailsEntrees;
  @JsonIgnore
  @ManyToOne
  private Manager manager;
  @JsonIgnore
  @ManyToOne
  private Admin admin;

}
