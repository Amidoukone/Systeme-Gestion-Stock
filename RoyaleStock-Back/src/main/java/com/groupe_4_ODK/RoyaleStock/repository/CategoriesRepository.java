package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
  Categories findByLibelle(String libelle);
  //Nombre de Catégories
  @Query("SELECT COUNT(c) FROM Categories c")
  long countCategories();
//Categories d'une Entrepot
@Query("SELECT COUNT(DISTINCT p.categories) FROM Produits p WHERE p.entrepots.id = :entrepotId")
long countCategoriesByEntrepotId(Long entrepotId);

}
