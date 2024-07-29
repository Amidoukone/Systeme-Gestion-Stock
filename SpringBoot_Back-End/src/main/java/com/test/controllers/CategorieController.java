package com.test.controllers;

import com.test.entities.Categorie;
import com.test.services.CategorieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping
    public List<Categorie> getAllCategories() {
        return categorieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable int id) {
        return categorieService.findById(id)
                .map(categorie -> ResponseEntity.ok().body(categorie))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Categorie createCategorie(@RequestBody Categorie categorie) {
        return categorieService.save(categorie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable int id, @RequestBody Categorie categorieDetails) {
        return categorieService.findById(id)
                .map(categorie -> {
                    categorie.setName(categorieDetails.getName());
                    categorie.setCreateBy(categorieDetails.getCreateBy());
                    Categorie updatedCategorie = categorieService.save(categorie);
                    return ResponseEntity.ok().body(updatedCategorie);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable int id) {
        categorieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /*@GetMapping("/entrepot/{entrepotId}")
    public ResponseEntity<List<Categorie>> getCategoriesByEntrepot(@PathVariable int entrepotId) {
        try {
            List<Categorie> categories = categorieService.getCategoriesByEntrepot(entrepotId);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{entrepotId}/nombre-categories")
    public ResponseEntity<Long> countCategoriesByEntrepotId(@PathVariable int entrepotId) {
        long count = categorieService.countCategoriesByEntrepotId(entrepotId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/nombre-categories")
    public ResponseEntity<Long> countCategories(){
        long count = categorieService.countCategories();
        return ResponseEntity.ok(count);
    }*/
}
