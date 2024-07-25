package com.test.controllers;

import com.test.entities.DetailSortie;
import com.test.services.DetailSortieService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/details-sorties")
public class DetailSortieController {

    @Autowired
    private DetailSortieService detailSortieService;

    @GetMapping
    public List<DetailSortie> getAllDetailsSorties() {
        return detailSortieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailSortie> getDetailsSortieById(@PathVariable int id) {
        return detailSortieService.findById(id)
                .map(detailsSortie -> ResponseEntity.ok().body(detailsSortie))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetailSortie createDetailsSortie(@RequestBody DetailSortie detailsSortie) {
        return detailSortieService.save(detailsSortie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailSortie> updateDetailsSortie(@PathVariable int id, @RequestBody DetailSortie detailsSortieDetails) {
        return detailSortieService.findById(id)
                .map(detailsSortie -> {
                    detailsSortie.setQuantity(detailsSortieDetails.getQuantity());
                    detailsSortie.setPrix(detailsSortieDetails.getPrix());
                    detailsSortie.setBonSortie(detailsSortieDetails.getBonSortie());
                    detailsSortie.setProduit(detailsSortieDetails.getProduit());
                    DetailSortie updatedDetailsSortie = detailSortieService.save(detailsSortie);
                    return ResponseEntity.ok().body(updatedDetailsSortie);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailsSortie(@PathVariable int id) {
        detailSortieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
