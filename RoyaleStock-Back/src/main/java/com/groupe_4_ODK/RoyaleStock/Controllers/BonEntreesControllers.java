package com.groupe_4_ODK.RoyaleStock.Controllers;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import com.groupe_4_ODK.RoyaleStock.Services.BonEntreesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bonentrees")
public class BonEntreesControllers {

  @Autowired
  private BonEntreesServices bonEntreesServices;

  @GetMapping
  public List<BonEntrees> getAllBonEntrees() {
    return bonEntreesServices.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BonEntrees> getBonEntreeById(@PathVariable Long id) {
    Optional<BonEntrees> bonEntree = bonEntreesServices.findById(id);
    return bonEntree.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public BonEntrees createBonEntree(@RequestBody BonEntrees bonEntree) {
    return bonEntreesServices.save(bonEntree);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BonEntrees> updateBonEntree(@PathVariable Long id, @RequestBody BonEntrees bonEntree) {
    BonEntrees updatedBonEntree = bonEntreesServices.updateBonEntree(id, bonEntree);
    return ResponseEntity.ok(updatedBonEntree);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBonEntree(@PathVariable Long id) {
    bonEntreesServices.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
