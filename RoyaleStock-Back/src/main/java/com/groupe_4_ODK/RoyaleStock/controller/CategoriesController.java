package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoriesController {

  private final CategoriesService categoriesService;

  @PostMapping("/create")
  public ResponseEntity<Categories> createCategory(@RequestBody Categories categories) {
    try {
      return ResponseEntity.ok(categoriesService.creerCategories(categories));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @GetMapping("/entrepot/{entrepotId}")
  public ResponseEntity<List<Categories>> getCategoriesByEntrepot(@PathVariable Long entrepotId) {
    try {
      List<Categories> categories = categoriesService.getCategoriesByEntrepot(entrepotId);
      return ResponseEntity.ok(categories);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
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

  @GetMapping("/{entrepotId}/nombre-categories")
  public ResponseEntity<Long> countCategoriesByEntrepotId(@PathVariable Long entrepotId) {
    long count = categoriesService.countCategoriesByEntrepotId(entrepotId);
    return ResponseEntity.ok(count);
  }

  @GetMapping("/nombre-categories")
  public ResponseEntity<Long> countCategories(){
    long count = categoriesService.countCategories();
    return ResponseEntity.ok(count);
  }
}
