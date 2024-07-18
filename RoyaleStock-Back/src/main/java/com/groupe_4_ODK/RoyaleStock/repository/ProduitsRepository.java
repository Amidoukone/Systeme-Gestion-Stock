package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitsRepository extends JpaRepository<Produits, Long> {

  Produits findByNom(String nom);

  @Query("SELECT p FROM Produits p WHERE p.categories.libelle = :categorie")
  List<Produits> findByCategorie(@Param("categorie") String categorie);
}
