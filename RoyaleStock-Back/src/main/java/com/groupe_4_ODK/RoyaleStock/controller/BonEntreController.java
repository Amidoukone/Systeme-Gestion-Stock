package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsEntrees;
import com.groupe_4_ODK.RoyaleStock.service.BonEntreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bonEntre")
public class BonEntreController {
  @Autowired
  private BonEntreService bonEntreService;
  //Create BonEntre

  @PostMapping("creer")
  public ResponseEntity<BonEntrees> createBonEntre(@RequestBody BonEntrees bonEntre) {
    BonEntrees createdBonEntre = bonEntreService.creeBonEntre(bonEntre);
    return new ResponseEntity<>(createdBonEntre, HttpStatus.CREATED);
  }
  // Endpoint pour récupérer les DetailsEntre d'un BonEntre par son ID
  @GetMapping("/{id}/details-entres")
  public ResponseEntity<List<DetailsEntrees>> getDetailsEntresByBonEntreId(@PathVariable("id") Long bonEntreId) {
    List<DetailsEntrees> detailsEntres = bonEntreService.getDetailsEntresByBonEntreId(bonEntreId);
    if (detailsEntres != null) {
      return ResponseEntity.ok(detailsEntres);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  // Endpoint pour récupérer tous les BonEntre
  @GetMapping
  public List<BonEntrees> getAllBonEntres() {
    return bonEntreService.getAllBonEntres();
  }

  // Endpoint pour récupérer un BonEntre par son ID
  @GetMapping("/entre/{id}")
  public ResponseEntity<BonEntrees> getBonEntreById(@PathVariable("id") Integer id) {
    Optional<BonEntrees> bonEntreOptional = bonEntreService.getBonEntreById(id);
    return bonEntreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }


  // Endpoint pour mettre à jour un BonEntre existant
  @PutMapping("/{id}")
  public ResponseEntity<BonEntrees> updateBonEntre(@PathVariable("id") Integer id, @RequestBody BonEntrees bonEntre) {
    BonEntrees updatedBonEntre = bonEntreService.updateBonEntre(id, bonEntre);
    if (updatedBonEntre != null) {
      return ResponseEntity.ok(updatedBonEntre);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // Endpoint pour supprimer un BonEntre par son ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBonEntre(@PathVariable("id") Integer id) {
    bonEntreService.deleteBonEntre(id);
    return ResponseEntity.noContent().build();
  }
  // Endpoint pour récupérer un DetailsEntre par son ID
  @GetMapping("/{id}")
  public ResponseEntity<DetailsEntrees> getDetailsEntreById(@PathVariable("id") Integer id) {
    Optional<DetailsEntrees> detailsEntreOptional = bonEntreService.getDetailsEntreById(id);
    return detailsEntreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
