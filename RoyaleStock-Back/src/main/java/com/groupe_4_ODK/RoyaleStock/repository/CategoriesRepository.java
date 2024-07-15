package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
  Categories findByLibelle(String libelle);
}
