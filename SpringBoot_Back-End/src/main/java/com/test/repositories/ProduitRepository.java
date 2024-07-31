package com.test.repositories;

import com.test.dto.TopEntreeDTO;
import com.test.dto.TopVenduDTO;
import com.test.entities.Produit;
import com.test.entities.DetailSortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    @Query("SELECT p.categorie.name, COUNT(p) FROM Produit p GROUP BY p.categorie.name")
    List<Object[]> countByCategory();
    Produit findByproductName(String nom);
}
