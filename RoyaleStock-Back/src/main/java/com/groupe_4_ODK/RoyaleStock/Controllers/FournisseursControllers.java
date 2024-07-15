package com.groupe_4_ODK.RoyaleStock.Controllers;

import com.groupe_4_ODK.RoyaleStock.entite.Fournisseurs;
import com.groupe_4_ODK.RoyaleStock.Services.FournisseursServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseursControllers {

  @Autowired
  private FournisseursServices fournisseursServices;

  @GetMapping
  public List<Fournisseurs> getAllFournisseurs() {
    return fournisseursServices.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Fournisseurs> getFournisseurById(@PathVariable int id) {
    Optional<Fournisseurs> fournisseur = fournisseursServices.findById(id);
    return fournisseur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Fournisseurs createFournisseur(@RequestBody Fournisseurs fournisseur) {
    return fournisseursServices.save(fournisseur);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Fournisseurs> updateFournisseur(@PathVariable int id, @RequestBody Fournisseurs fournisseur) {
    Fournisseurs updatedFournisseur = fournisseursServices.updateFournisseur(id, fournisseur);
    return ResponseEntity.ok(updatedFournisseur);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFournisseur(@PathVariable int id) {
    fournisseursServices.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
