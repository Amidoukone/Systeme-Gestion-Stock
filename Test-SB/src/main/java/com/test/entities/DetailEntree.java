package com.test.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "details_entrees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailEntree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "quantite")
    private int quantite;

    @Column(name = "prix_total")
    private int prixTotal;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "bon_entree_id", nullable = false)
    private BonEntree bonEntree;
}
