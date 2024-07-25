package com.test.controllers;

import com.test.entities.Categorie;
import com.test.entities.Motif;
import com.test.services.CategorieService;
import com.test.services.MotifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/motif")
public class MotifController {

    @Autowired
    private MotifService motifService;

    //Nombre de Motif
    @GetMapping("/motifNombre")
    public int getMotifNombre() {
        return motifService.getNombreMotif();
    }

    @GetMapping
    public List<Motif> getMotif() {
        return motifService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motif> findById(@PathVariable int id) {
        return motifService.findById(id)
                .map(motif -> ResponseEntity.ok().body(motif))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Motif save(@RequestBody Motif motif) {
        return motifService.save(motif);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motif> save(@PathVariable int id, @RequestBody Motif motifDetails) {
        return motifService.findById(id)
                .map(motif -> {
                    motif.setTitle(motifDetails.getTitle());
                    motif.setCreateBy(motifDetails.getCreateBy());
                    Motif updateMotif = motifService.save(motif);
                    return ResponseEntity.ok().body(updateMotif);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        motifService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
