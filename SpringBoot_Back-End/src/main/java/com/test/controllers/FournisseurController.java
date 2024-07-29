package com.test.controllers;

import com.test.entities.Fournisseur;
import com.test.services.FournisseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping("/count")
    public int getFournisseursCount() {
        return fournisseurService.getFournisseursCount();
    }

    @GetMapping
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable int id) {
        return fournisseurService.findById(id)
                .map(fournisseur -> ResponseEntity.ok().body(fournisseur))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Fournisseur createFournisseur(@RequestBody Fournisseur fournisseur) {
        return fournisseurService.save(fournisseur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable int id, @RequestBody Fournisseur fournisseurDetails) {
        return fournisseurService.findById(id)
                .map(fournisseur -> {
                    fournisseur.setFournName(fournisseurDetails.getFournName());
                    fournisseur.setAdresse(fournisseurDetails.getAdresse());
                    fournisseur.setTelephone(fournisseurDetails.getTelephone());
                    Fournisseur updatedFournisseur = fournisseurService.save(fournisseur);
                    return ResponseEntity.ok().body(updatedFournisseur);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable int id) {
        fournisseurService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
