package com.test.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "details_sorties")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailSortie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "prix_total")
    private int prixTotal;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @ManyToOne
    @JoinColumn(name = "bon_sortie_id", nullable = false)
    private BonSortie bonSortie;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
}
