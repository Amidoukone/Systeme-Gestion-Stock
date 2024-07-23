package com.test.controllers;

import com.test.entities.Entrepot;
import com.test.services.EntrepotService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entrepots")
public class EntrepotController {

    @Autowired
    private EntrepotService entrepotService;

    @GetMapping
    public List<Entrepot> getAllEntrepots() {
        return entrepotService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrepot> getEntrepotById(@PathVariable int id) {
        return entrepotService.findById(id)
                .map(entrepot -> ResponseEntity.ok().body(entrepot))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Entrepot createEntrepot(@RequestBody Entrepot entrepot) {
        return entrepotService.save(entrepot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrepot> updateEntrepot(@PathVariable int id, @RequestBody Entrepot entrepotDetails) {
        return entrepotService.findById(id)
                .map(entrepot -> {
                    entrepot.setEntrepotName(entrepotDetails.getEntrepotName());
                    entrepot.setLieu(entrepotDetails.getLieu());
                    entrepot.setStatut(entrepotDetails.getStatut());
                    Entrepot updatedEntrepot = entrepotService.save(entrepot);
                    return ResponseEntity.ok().body(updatedEntrepot);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrepot(@PathVariable int id) {
        entrepotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
