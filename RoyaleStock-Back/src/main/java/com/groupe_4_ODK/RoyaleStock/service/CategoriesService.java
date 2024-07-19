package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.repository.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriesService {

  private final CategoriesRepository categoriesRepository;

  public Categories creerCategories(Categories categories) {
    return categoriesRepository.save(categories);
  }

  public List<Categories> lireCategories() {

    return categoriesRepository.findAll();
  }

  public Categories categories(Long id) {
    return categoriesRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Categorie non trouvée !"));
  }

  public Categories categories(String libelle) {
    return categoriesRepository.findByLibelle(libelle);
  }

  public Categories modifierCategories(Long id, Categories categories) {
    return categoriesRepository.findById(id)
      .map(ct-> {
        ct.setLibelle(categories.getLibelle());
        return categoriesRepository.save(ct);
      }).orElseThrow(() -> new RuntimeException("Categorie non trouvé !"));
  }

  public String supprimerCategorie(Long id) {
    categoriesRepository.deleteById(id);
    return "Categorie Supprimé !";
  }
  //Nombre de categories
  public long countCategories() {
    return categoriesRepository.countCategories();
  }
  //Catgories d'une entrepot
  public long countCategoriesByEntrepotId(Long entrepotId) {
    return categoriesRepository.countCategoriesByEntrepotId(entrepotId);
  }
}
