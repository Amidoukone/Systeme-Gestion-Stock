package com.groupe_4_ODK.RoyaleStock.entite;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notification") @Data
@Setter @AllArgsConstructor @NoArgsConstructor
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private  Long Id;

  private String Contenu;

  private Date dateNotification;

  @OneToMany
  private List<Utilisateur> utilisateurs;


}
