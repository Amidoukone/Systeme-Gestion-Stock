package com.test.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "fournisseurs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "fourn_name", nullable = false)
    private String fournName;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

}
