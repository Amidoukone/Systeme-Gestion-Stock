package com.groupe_4_ODK.RoyaleStock.Controllers;

import com.groupe_4_ODK.RoyaleStock.entite.DetailsEntrees;
import com.groupe_4_ODK.RoyaleStock.Services.DetailsEntreesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detailsentrees")
public class DetailsEntreesControllers {

  @Autowired
  private DetailsEntreesServices detailsEntreesServices;

  @GetMapping
  public List<DetailsEntrees> getAllDetailsEntrees() {
    return detailsEntreesServices.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetailsEntrees> getDetailsEntreeById(@PathVariable Long id) {
    Optional<DetailsEntrees> detailsEntree = detailsEntreesServices.findById(id);
    return detailsEntree.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public DetailsEntrees createDetailsEntree(@RequestBody DetailsEntrees detailsEntree) {
    return detailsEntreesServices.save(detailsEntree);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DetailsEntrees> updateDetailsEntree(@PathVariable Long id, @RequestBody DetailsEntrees detailsEntree) {
    DetailsEntrees updatedDetailsEntree = detailsEntreesServices.updateDetailsEntree(id, detailsEntree);
    return ResponseEntity.ok(updatedDetailsEntree);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDetailsEntree(@PathVariable Long id) {
    detailsEntreesServices.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
