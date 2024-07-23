package com.test.controllers;

import com.test.entities.Produit;
import com.test.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping
    public List<Produit> getAllProduits() {
        return produitService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable int id) {
        return produitService.findById(id)
                .map(produit -> ResponseEntity.ok().body(produit))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        try {
            // Log du produit
            System.out.println("Produit à enregistrer: " + produit);

            // s'assure que la categorie
            if (produit.getCategorie() == null || produit.getCategorie().getId() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Produit savedProduit = produitService.save(produit);
            System.out.println("Produit enregistré avec succès: " + savedProduit);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduit);
        } catch (Exception e) {
            // Log pour l'exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable int id, @RequestBody Produit produitDetails) {
        return produitService.findById(id)
                .map(produit -> {
                    produit.setProductName(produitDetails.getProductName());
                    produit.setDescription(produitDetails.getDescription());
                    produit.setPrix(produitDetails.getPrix());
                    produit.setQuantity(produitDetails.getQuantity());
                    produit.setCategorie(produitDetails.getCategorie());
                    Produit updatedProduit = produitService.save(produit);
                    return ResponseEntity.ok().body(updatedProduit);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable int id) {
        produitService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
