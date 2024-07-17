package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.dto.TopEntreeDTO;
import com.groupe_4_ODK.RoyaleStock.dto.TopVenduDTO;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.repository.CategoriesRepository;
import com.groupe_4_ODK.RoyaleStock.repository.ProduitsRepository;
import com.groupe_4_ODK.RoyaleStock.service.CategoriesService;
import com.groupe_4_ODK.RoyaleStock.service.ProduitsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("api/produits")
@AllArgsConstructor
public class ProduitsController {

  private final ProduitsService produitsService;


  @PostMapping
  public ResponseEntity<Produits> createProduit(@RequestBody Produits produit,
                                                @RequestParam Long userId,
                                                @RequestParam Long entrepotId) {
    try {
      return new ResponseEntity<>(produitsService.createProduit(produit, userId, entrepotId), HttpStatus.CREATED);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @GetMapping
  public ResponseEntity<List<Produits>> getAllProduits() {
    return ResponseEntity.ok(produitsService.getAllProduits());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Produits> getProduitById(@PathVariable Long id) {
    Optional<Produits> produit = produitsService.getProduitById(id);
    return produit.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
  @GetMapping
  public List<Produits> lireProduits(){
    return produitsService.lireProduits();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Produits> updateProduit(@PathVariable Long id, @RequestBody Produits updatedProduit) {
    try {
      return ResponseEntity.ok(produitsService.updateProduit(id, updatedProduit));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/entrepot/{entrepotId}")
  public ResponseEntity<List<Produits>> getProduitsByEntrepot(@PathVariable Long entrepotId) {
    return ResponseEntity.ok(produitsService.getProduitsByEntrepot(entrepotId));
  }
  @DeleteMapping(value = "/supprimer/{id}")
  public String supprimerApprenant(@PathVariable Long id){
    return produitsService.supprimerProduit(id);
  }

  @GetMapping("/top-vendus")
  public List<TopVenduDTO> getTopVendus() {
    return produitsService.getTopVendus();
  }
  //Entre
  @GetMapping("/top-entrees")
  public List<TopEntreeDTO> getTopEntrees() {
    return produitsService.getTopEntrees();
  }

}
