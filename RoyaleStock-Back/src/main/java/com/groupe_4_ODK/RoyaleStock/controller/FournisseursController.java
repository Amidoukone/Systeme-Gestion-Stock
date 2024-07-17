package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Fournisseurs;
import com.groupe_4_ODK.RoyaleStock.service.FournisseursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseursController {

  @Autowired
  private FournisseursService fournisseursService;

  @GetMapping
  public List<Fournisseurs> getAllFournisseurs() {
    return fournisseursService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Fournisseurs> getFournisseurById(@PathVariable int id) {
    Optional<Fournisseurs> fournisseur = fournisseursService.findById(id);
    return fournisseur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/creer")
  public Fournisseurs createFournisseur(@RequestBody Fournisseurs fournisseur) {
    return fournisseursService.save(fournisseur);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Fournisseurs> updateFournisseur(@PathVariable int id, @RequestBody Fournisseurs fournisseur) {
    Fournisseurs updatedFournisseur = fournisseursService.updateFournisseur(id, fournisseur);
    return ResponseEntity.ok(updatedFournisseur);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFournisseur(@PathVariable int id) {
    fournisseursService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
  @GetMapping("/fournisseurs-count")
  public int getFournisseursCount() {
    return fournisseursService.getFournisseursCount();
  }


}

