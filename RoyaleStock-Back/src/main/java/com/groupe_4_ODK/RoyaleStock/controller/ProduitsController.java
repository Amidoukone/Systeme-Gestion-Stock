package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.dto.TopEntreeDTO;
import com.groupe_4_ODK.RoyaleStock.dto.TopVenduDTO;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.repository.CategoriesRepository;
import com.groupe_4_ODK.RoyaleStock.repository.ProduitsRepository;
import com.groupe_4_ODK.RoyaleStock.service.CategoriesService;
import com.groupe_4_ODK.RoyaleStock.service.ProduitsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/produits")
@AllArgsConstructor
public class ProduitsController {

  private final ProduitsService produitsService;


  @PostMapping(value = "/creer", consumes = APPLICATION_JSON_VALUE)
  public Produits creerProduits(@RequestBody Produits produits){
    return produitsService.creerProduits(produits);
  }

  @GetMapping
  public List<Produits> lireProduits(){
    return produitsService.lireProduits();
  }

  @PutMapping(value = "/modifier/{id}", consumes = APPLICATION_JSON_VALUE)
  public Produits modifierProduits(@PathVariable Long id, @RequestBody Produits produits){
    return produitsService.modifierProduits(id, produits);
  }

  @DeleteMapping(value = "/supprimer/{id}")
  public String supprimerApprenant(@PathVariable Long id){
    return produitsService.supprimerProduit(id);
  }
//
//  @GetMapping("/top-vendus")
//  public List<TopVenduDTO> getTopVendus() {
//    return produitsService.getTopVendus();
//  }
  //Entre
  @GetMapping("/top-entrees")
  public List<TopEntreeDTO> getTopEntrees() {
    return produitsService.getTopEntrees();
  }

}
