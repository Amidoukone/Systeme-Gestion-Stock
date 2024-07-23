package com.groupe_4_ODK.RoyaleStock.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Table(name = "bonsortie") @Entity @AllArgsConstructor @NoArgsConstructor @Data
public class BonSorties {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  @Temporal(TemporalType.DATE)
  private Date dateSortie = new Date();
  private double prixTotal;

  @JsonIgnore
  @OneToMany
  @JsonManagedReference
  private List<DetailsSorties> detailsSorties;
  @JsonIgnore
  @ManyToOne
  private Motif motif;
  @JsonIgnore
  @ManyToOne
  private Utilisateur utilisateur;

}
