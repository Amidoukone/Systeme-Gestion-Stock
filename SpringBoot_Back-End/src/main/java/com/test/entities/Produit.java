package com.test.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "produits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "seuil")
    private int seuil;

    private long createBy;

    @ManyToOne
    @JoinColumn(name = "categories_id", nullable = false)
    private Categorie categorie;

<<<<<<< Updated upstream


    /*@OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
=======
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
>>>>>>> Stashed changes
    @JsonManagedReference
    private List<DetailSortie> detailsSorties;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetailEntree> detailsEntrees;
}
