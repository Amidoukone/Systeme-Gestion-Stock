package com.test.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "motif")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Motif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "title")
    private String title;

    private long createBy;

    @OneToMany(mappedBy = "motif", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BonSortie> bonSorties;

    public Motif(String title) {
        this.title = title;
    }
}
