package com.groupe_4_ODK.RoyaleStock.Services;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.Repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServices {

  @Autowired
  private CategoriesRepository categoriesRepository;

  public List<Categories> findAll() {
    return categoriesRepository.findAll();
  }

  public Optional<Categories> findById(Long id) {
    return categoriesRepository.findById(id);
  }

  public Categories save(Categories categorie) {
    return categoriesRepository.save(categorie);
  }

  public void deleteById(Long id) {
    categoriesRepository.deleteById(id);
  }

  public Categories updateCategorie(Long id, Categories updatedCategorie) {
    return categoriesRepository.findById(id).map(categorie -> {
      categorie.setLibelle(updatedCategorie.getLibelle());
      return categoriesRepository.save(categorie);
    }).orElseGet(() -> {
      updatedCategorie.setId(id);
      return categoriesRepository.save(updatedCategorie);
    });
  }
}
