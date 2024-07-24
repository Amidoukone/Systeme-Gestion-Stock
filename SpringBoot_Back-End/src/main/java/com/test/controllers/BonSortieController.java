package com.test.controllers;

import com.test.entities.BonSortie;
import com.test.entities.DetailSortie;
import com.test.entities.Produit;
import com.test.services.BonSortieService;
import com.test.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bon-sorties")
public class BonSortieController {

    @Autowired
    private BonSortieService bonSortieService;

    @Autowired
    private ProduitService produitService;

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
    public ResponseEntity<BonSortie> createBonSortie(@RequestBody BonSortie bonSortie) {
        try {
            BonSortie savedBonSortie = bonSortieService.save(bonSortie);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBonSortie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BonSortie> updateBonSortie(@PathVariable int id, @RequestBody BonSortie bonSortieDetails) {
        return bonSortieService.findById(id)
                .map(bonSortie -> {
                    bonSortie.setDateSortie(bonSortieDetails.getDateSortie());
                    bonSortie.setMotif(bonSortieDetails.getMotif());
                    bonSortie.setUtilisateur(bonSortieDetails.getUtilisateur());
                    bonSortie.setDetailsSorties(bonSortieDetails.getDetailsSorties());

                    /*for (DetailSortie detail : bonSortie.getDetailsSorties()) {
                        Produit produit = detail.getProduit();
                        if (produit != null && produit.getId() == 0) {
                            produitService.save(produit);
                        }
                    }*/

                    BonSortie updatedBonSortie = bonSortieService.save(bonSortie);
                    return ResponseEntity.ok().body(updatedBonSortie);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBonSortie(@PathVariable int id) {
        bonSortieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //Creer une sortie
    @PostMapping("/create")
    public ResponseEntity<BonSortie> createBonSorties(@RequestBody BonSortie bonSortie) {
        try {
            BonSortie createBonSorties = bonSortieService.createBonSorties(bonSortie);
            return new ResponseEntity<>(createBonSorties, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/topProduitByMotif")
    public ResponseEntity<Map<String, Map<String, Integer>>> getTopProductsByMotif() {
        Map<String, Map<String, Integer>> topProductsByMotif = bonSortieService.getTopProductsByMotif();
        return ResponseEntity.ok(topProductsByMotif);
    }

    // pour mettre à jour un BonSortie existant par son ID
    @PutMapping("/sortie/update/{id}")
    public ResponseEntity<BonSortie> updateBonSortie(@PathVariable Integer id, @RequestBody BonSortie bonSortie) {
        BonSortie updatedBonSortie = bonSortieService.updateBonSortie(id, bonSortie);
        if (updatedBonSortie != null) {
            return ResponseEntity.ok(updatedBonSortie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
