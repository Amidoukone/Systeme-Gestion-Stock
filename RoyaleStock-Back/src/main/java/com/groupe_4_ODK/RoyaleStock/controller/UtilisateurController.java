package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.enums.TypeRole;
import com.groupe_4_ODK.RoyaleStock.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
  private final UtilisateurService utilisateurService;

  public UtilisateurController(UtilisateurService utilisateurService) {
    this.utilisateurService = utilisateurService;
  }
  @GetMapping
  public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
    return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
  }

  @GetMapping("/{nom}")
  public ResponseEntity<Utilisateur> getUtilisateurByName(@PathVariable String nom) {
    Utilisateur utilisateur = utilisateurService.findUtilisateurByName(nom);
    return utilisateur != null ? ResponseEntity.ok(utilisateur) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur, @RequestParam TypeRole typeRole) {
    Utilisateur createdUtilisateur = utilisateurService.createUtilisateur(utilisateur, typeRole);
    return new ResponseEntity<>(createdUtilisateur, HttpStatus.CREATED);
  }

  @PostMapping("/default-admin")
  public ResponseEntity<String> createDefaultAdmin() {
    utilisateurService.createDefaultAdmin();
    return ResponseEntity.ok("Admin creer avec successs");
  }
}
