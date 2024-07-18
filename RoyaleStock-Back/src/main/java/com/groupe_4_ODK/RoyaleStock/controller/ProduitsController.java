package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.service.ProduitsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/produits")
@AllArgsConstructor
public class ProduitsController {

  private final ProduitsService produitsService;

  @PostMapping(value = "/creer", consumes = APPLICATION_JSON_VALUE)
  public Produits creerProduits(@RequestBody Produits produits) {
    return produitsService.creerProduits(produits);
  }

  @GetMapping
  public List<Produits> lireProduits() {
    return produitsService.lireProduits();
  }

  @GetMapping("/{id}")
  public Produits getProduitById(@PathVariable Long id) {
    return produitsService.getProduitById(id);
  }

  @GetMapping("/{nom}")
  public Produits getProduitByNom(@PathVariable String nom) {
    return produitsService.getProduitByNom(nom);
  }

  @GetMapping("/{categorie}")
  public List<Produits> getProduitsByCategorie(@PathVariable String categorie) {
    return produitsService.getProduitsByCategorie(categorie);
  }

  @PutMapping(value = "/modifier/{id}", consumes = APPLICATION_JSON_VALUE)
  public Produits modifierProduits(@PathVariable Long id, @RequestBody Produits produits) {
    return produitsService.modifierProduits(id, produits);
  }

  @DeleteMapping(value = "/supprimer/{id}")
  public String supprimerProduits(@PathVariable Long id) {
    return produitsService.supprimerProduit(id);
  }
}
