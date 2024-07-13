package com.groupe_4_ODK.RoyaleStock.Controllers;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.Services.CategoriesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoriesControllers {

  @Autowired
  private CategoriesServices categoriesService;

  @GetMapping
  public List<Categories> getAllCategories() {
    return categoriesService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Categories> getCategorieById(@PathVariable Long id) {
    Optional<Categories> categorie = categoriesService.findById(id);
    return categorie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Categories createCategorie(@RequestBody Categories categorie) {
    return categoriesService.save(categorie);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Categories> updateCategorie(@PathVariable Long id, @RequestBody Categories categorie) {
    Categories updatedCategorie = categoriesService.updateCategorie(id, categorie);
    return ResponseEntity.ok(updatedCategorie);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
    categoriesService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
