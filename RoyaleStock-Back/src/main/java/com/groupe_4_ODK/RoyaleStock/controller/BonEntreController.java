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
  //BonEntre
  //Create BonEntre
  @PostMapping("/create")
  public ResponseEntity<BonEntrees> createBonEntree(@RequestBody BonEntrees bonEntree) {
    BonEntrees createdBonEntree = bonEntreService.creeBonEntre(bonEntree);
    return ResponseEntity.ok(createdBonEntree);
  }

  @PutMapping("/validate/{id}")
  public ResponseEntity<BonEntrees> validateBonEntree(@PathVariable Integer id) {
    BonEntrees validatedBonEntree = bonEntreService.validerBonEntre(id);
    return ResponseEntity.ok(validatedBonEntree);
  }
  @GetMapping("/list")
  public ResponseEntity<List<BonEntrees>> getAllBonEntrees() {
    List<BonEntrees> bonEntreesList = bonEntreService.getAllBonEntrees();
    return ResponseEntity.ok(bonEntreesList);
  }

  @GetMapping("/getbonById/{id}")
  public ResponseEntity<BonEntrees> getBonEntreeById(@PathVariable Integer id) {
    BonEntrees bonEntree = bonEntreService.getBonEntreeById(id);
    return ResponseEntity.ok(bonEntree);
  }

  // Endpoint pour mettre à jour un BonEntre existant
  @PutMapping("/update/{id}")
  public ResponseEntity<BonEntrees> updateBonEntree(@PathVariable Integer id, @RequestBody BonEntrees bonEntreeDetails) {
    BonEntrees updatedBonEntree = bonEntreService.updateBonEntree(id, bonEntreeDetails);
    return ResponseEntity.ok(updatedBonEntree);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteBonEntree(@PathVariable Integer id) {
    bonEntreService.deleteBonEntree(id);
    return ResponseEntity.noContent().build();
  }
  // Endpoint pour récupérer un DetailsEntre par son ID
  @GetMapping("/get/{id}")
  public ResponseEntity<DetailsEntrees> getDetailsEntreById(@PathVariable("id") Integer id) {
    Optional<DetailsEntrees> detailsEntreOptional = bonEntreService.getDetailsEntreById(id);
    return detailsEntreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
