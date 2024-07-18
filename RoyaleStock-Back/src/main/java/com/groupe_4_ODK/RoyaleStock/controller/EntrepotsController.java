package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.service.EntrepotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entrepots")
public class EntrepotsController {

  @Autowired
  private EntrepotsService entrepotsService;

  @GetMapping("/read")
  public List<Entrepots> getAllEntrepots() {
    return entrepotsService.findAll();
  }

  @GetMapping("/read/{id}")
  public Optional<Entrepots> getById(@PathVariable Long id) {
    return entrepotsService.findById(id);
  }

  @PostMapping("/create")
  public Entrepots createEntrepot(@RequestBody Entrepots entrepots) {
    return entrepotsService.save(entrepots);
  }

  @PutMapping("/update/{id}")
  public Entrepots updateEntrepot(@PathVariable Long id, @RequestBody Entrepots updatedEntrepot) {
    return entrepotsService.updateEntrepot(id, updatedEntrepot);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteEntrepot(@PathVariable Long id) {
    entrepotsService.deleteById(id);
  }
}
