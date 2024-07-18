package com.groupe_4_ODK.RoyaleStock.entite;

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

  private Date dateSortie;
  private double prixTotal;
  @OneToMany
  @JsonManagedReference
  private List<DetailsSorties> detailsSorties;
  @ManyToOne
  private Manager manager;
  @ManyToOne
  private Admin admin;
  @ManyToOne
  private Motif motif;
  @ManyToOne
  private Vendeur vendeur;

}
