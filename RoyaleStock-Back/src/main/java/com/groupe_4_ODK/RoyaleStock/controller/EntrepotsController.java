package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.service.EntrepotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entrepots")
public class EntrepotsController {

  @Autowired
  private EntrepotsService entrepotsService;

  @PostMapping("/create")
  public Entrepots createEntrepot(@RequestBody Entrepots entrepots) {
    return entrepotsService.save(entrepots);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Entrepots> updateEntrepot(@PathVariable Long id, @RequestBody Entrepots updatedEntrepot) {
    try {
      return ResponseEntity.ok(entrepotsService.updateEntrepot(id, updatedEntrepot));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{entrepotId}/manager/{managerId}")
  public ResponseEntity<Entrepots> assignManager(@PathVariable Long entrepotId, @PathVariable Long managerId) {
    try {
      Entrepots updatedEntrepot = entrepotsService.assignManager(entrepotId, managerId);
      return ResponseEntity.ok(updatedEntrepot);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @PostMapping("/{entrepotId}/vendeur/{vendeurId}")
  public ResponseEntity<Entrepots> assignVendeur(@PathVariable Long entrepotId, @PathVariable Long vendeurId) {
    try {
      return ResponseEntity.ok(entrepotsService.assignVendeur(entrepotId, vendeurId));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @PostMapping("/{id}/upload-logo")
  public ResponseEntity<String> uploadLogo(@PathVariable Long id, @RequestParam("logo") MultipartFile logo) {
    try {
      Optional<Entrepots> entrepotOptional = entrepotsService.findById(id);
      if (entrepotOptional.isPresent()) {
        Entrepots entrepot = entrepotOptional.get();
        entrepot.setLogo(Arrays.toString(logo.getBytes()));
        entrepotsService.updateEntrepot(id, entrepot);
        return ResponseEntity.ok("Logo uploaded successfully");
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading logo");
    }
  }

  @GetMapping("/{id}/is-accessible")
  public ResponseEntity<Boolean> isAccessible(@PathVariable Long id) {
    Optional<Entrepots> entrepotOptional = entrepotsService.findById(id);
    if (entrepotOptional.isPresent()) {
      return ResponseEntity.ok(entrepotsService.isAccessible(entrepotOptional.get()));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/read")
  public List<Entrepots> getAllEntrepots() {
    return entrepotsService.findAll();
  }

  @GetMapping("/read/{id}")
  public Optional<Entrepots> getById(@PathVariable Long id) {
    return entrepotsService.findById(id);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteEntrepot(@PathVariable Long id) {
    entrepotsService.deleteById(id);
  }
}
