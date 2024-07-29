package com.test.controllers;

import com.test.entities.BonEntree;
import com.test.services.BonEntreeService;
import com.test.services.FournisseurService;
import com.test.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bon-entrees")
public class BonEntreeController {

    @Autowired
    private BonEntreeService bonEntreeService;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping
    public List<BonEntree> getAllBonEntrees() {
        return bonEntreeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BonEntree> getBonEntreeById(@PathVariable int id) {
        return bonEntreeService.findById(id)
                .map(bonEntree -> ResponseEntity.ok().body(bonEntree))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BonEntree> createBonEntree(@RequestBody BonEntree bonEntree) {
        BonEntree savedBonEntree = bonEntreeService.save(bonEntree);
        return ResponseEntity.ok(savedBonEntree);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BonEntree> updateBonEntree(@PathVariable int id, @RequestBody BonEntree bonEntreeDetails) {
        return bonEntreeService.findById(id)
                .map(bonEntree -> {
                    bonEntree.setDateCommande(bonEntreeDetails.getDateCommande());
                    bonEntree.setStatut(bonEntreeDetails.getStatut());
                    bonEntree.setUtilisateur(bonEntreeDetails.getUtilisateur());
                    bonEntree.setFournisseur(bonEntreeDetails.getFournisseur());
                    BonEntree updatedBonEntree = bonEntreeService.save(bonEntree);
                    return ResponseEntity.ok().body(updatedBonEntree);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBonEntree(@PathVariable int id) {
        bonEntreeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
