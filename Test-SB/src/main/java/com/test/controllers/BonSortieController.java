package com.test.controllers;

import com.test.entities.BonSortie;
import com.test.services.BonSortieService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bon-sorties")
public class BonSortieController {

    @Autowired
    private BonSortieService bonSortieService;

    @GetMapping
    public List<BonSortie> getAllBonSorties() {
        return bonSortieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BonSortie> getBonSortieById(@PathVariable int id) {
        return bonSortieService.findById(id)
                .map(bonSortie -> ResponseEntity.ok().body(bonSortie))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BonSortie createBonSortie(@RequestBody BonSortie bonSortie) {
        return bonSortieService.save(bonSortie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BonSortie> updateBonSortie(@PathVariable int id, @RequestBody BonSortie bonSortieDetails) {
        return bonSortieService.findById(id)
                .map(bonSortie -> {
                    bonSortie.setMotif(bonSortieDetails.getMotif());
                    bonSortie.setDateSortie(bonSortieDetails.getDateSortie());
                    bonSortie.setUtilisateur(bonSortieDetails.getUtilisateur());
                    BonSortie updatedBonSortie = bonSortieService.save(bonSortie);
                    return ResponseEntity.ok().body(updatedBonSortie);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBonSortie(@PathVariable int id) {
        bonSortieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
