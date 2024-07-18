package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoriesController {

  private final CategoriesService categoriesService;

  @PostMapping(value = "/creer", consumes = APPLICATION_JSON_VALUE)
  public Categories creerCategories(@RequestBody Categories categories){
    return categoriesService.creerCategories(categories);
  }

  @GetMapping
  public List<Categories> lireCategories(){
    return categoriesService.lireCategories();
  }

  @PutMapping(value = "/modifier/{id}", consumes = APPLICATION_JSON_VALUE)
  public Categories modifierCategories(@PathVariable Long id, @RequestBody Categories categories){
    return categoriesService.modifierCategories(id, categories);
  }

  @DeleteMapping(value = "/supprimer/{id}")
  public String supprimerCategories(@PathVariable Long id){
    return categoriesService.supprimerCategorie(id);
  }
}
