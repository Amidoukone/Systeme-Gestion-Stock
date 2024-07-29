package com.test.controllers;

import com.test.entities.Utilisateur;
import com.test.services.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        //logger.info("GET /api/utilisateurs");
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id) {
        return utilisateurService.findById(id)
                .map(utilisateur -> ResponseEntity.ok().body(utilisateur))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.save(utilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateurDetails) {
        return utilisateurService.findById(id)
                .map(utilisateur -> {
                    utilisateur.setUsername(utilisateurDetails.getUsername());
                    utilisateur.setContact(utilisateurDetails.getContact());
                    utilisateur.setEmail(utilisateurDetails.getEmail());
                    utilisateur.setPassword(utilisateurDetails.getPassword());
                    utilisateur.setEntrepot(utilisateurDetails.getEntrepot());
                    utilisateur.setRole(utilisateurDetails.getRole());
                    Utilisateur updatedUtilisateur = utilisateurService.save(utilisateur);
                    return ResponseEntity.ok().body(updatedUtilisateur);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
        utilisateurService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
