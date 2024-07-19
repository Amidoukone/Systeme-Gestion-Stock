package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.repository.CategoriesRepository;
import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriesService {

  private final CategoriesRepository categoriesRepository;
  private final MethodeUtil methodeUtil;


  public Categories creerCategories(Categories categories) {
    Categories existingCategory = categoriesRepository.findByLibelle(categories.getLibelle());
    if (existingCategory != null) {
      return existingCategory;
    }

    Long currentUserId = methodeUtil.getCurrentUserId();
    if (currentUserId == null) {
      throw new RuntimeException("Utilisateur non trouvé ou non authentifié");
    }

    Entrepots entrepot = methodeUtil.getEntrepotByUserId(currentUserId);
    if (entrepot == null) {
      throw new RuntimeException("Utilisateur n'est associé à aucun entrepôt");
    }

    categories.setCreateBy(currentUserId);
    categories.setEntrepot(entrepot);
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

  public List<Categories> getCategoriesByEntrepot(Long entrepotId) {
    return categoriesRepository.findByEntrepotId(entrepotId);
  }

}
