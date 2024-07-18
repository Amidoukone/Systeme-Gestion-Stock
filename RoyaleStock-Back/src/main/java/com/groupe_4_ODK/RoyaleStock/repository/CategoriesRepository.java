package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    String findByLibelle(String libelle);
}
