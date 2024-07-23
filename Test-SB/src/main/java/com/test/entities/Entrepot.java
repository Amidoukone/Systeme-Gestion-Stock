package com.test.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entrepots")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrepot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "entrepot_name")
    private String entrepotName;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "statut")
    private String statut;
}
