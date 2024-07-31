package com.test.controllers;

import com.test.dto.TopEntreeDTO;
import com.test.dto.TopVenduDTO;
import com.test.entities.Produit;
import com.test.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            if (produit.getCategorie() == null || produit.getCategorie().getId() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Produit savedProduit = produitService.save(produit);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduit);
        } catch (Exception e) {
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
                    produit.setCreateBy(produitDetails.getCreateBy());
                    produit.setQuantity(produitDetails.getQuantity());
                    //produit.setSeuil(produitDetails.getSeuil());
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

    /*@GetMapping("/top-vendus")
    public List<TopVenduDTO> getTopVendus() {
        return produitService.getTopVendus();
    }

    //Entre
    @GetMapping("/top-entrees")
    public List<TopEntreeDTO> getTopEntrees() {
        return produitService.getTopEntrees();
    }

    //Nombre de produits d'une entreprot
    @GetMapping("/nombreProduits/{entrepotId}")
    public ResponseEntity<Long> countProductsByEntrepotId(@PathVariable Long entrepotId) {
        long count = produitService.countProductsByEntrepotId(entrepotId);
        return ResponseEntity.ok(count);
    }
    //Liste des produits d'une Entrepots
    @GetMapping("/listProduits/{entrepotId}")
    public ResponseEntity<List<Produit>> findProductsByEntrepotId(@PathVariable Long entrepotId) {
        List<Produit> produits = produitService.findProductsByEntrepotId(entrepotId);
        return ResponseEntity.ok(produits);
    }*/
}
