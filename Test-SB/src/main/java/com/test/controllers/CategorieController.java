package com.test.controllers;

import com.test.entities.Categorie;
import com.test.services.CategorieService;
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
                    Categorie updatedCategorie = categorieService.save(categorie);
                    return ResponseEntity.ok().body(updatedCategorie);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable int id) {
        categorieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
