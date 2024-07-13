package com.groupe_4_ODK.RoyaleStock.Controllers;

import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.Services.ProduitsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
public class ProduitsControllers {

  @Autowired
  private ProduitsServices produitsServices;

  @GetMapping
  public List<Produits> getAllProduits() {
    return produitsServices.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Produits> getProduitById(@PathVariable Long id) {
    Optional<Produits> produit = produitsServices.findById(id);
    return produit.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Produits createProduit(@RequestBody Produits produit) {
    return produitsServices.save(produit);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Produits> updateProduit(@PathVariable Long id, @RequestBody Produits produit) {
    Produits updatedProduit = produitsServices.updateProduit(id, produit);
    return ResponseEntity.ok(updatedProduit);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
    produitsServices.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
