package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.enums.TypeRole;
import com.groupe_4_ODK.RoyaleStock.service.UtilisateurService;
import jdk.jshell.execution.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
  private final UtilisateurService utilisateurService;

  public UtilisateurController(UtilisateurService utilisateurService) {
    this.utilisateurService = utilisateurService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(path = "/admin", consumes = APPLICATION_JSON_VALUE)
  public void createAdmin(@RequestBody Utilisateur admin){
    this.utilisateurService.createAdmin(admin);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(path = "/manager", consumes = APPLICATION_JSON_VALUE)
  public void createFormateur(@RequestBody Utilisateur manager){
    this.utilisateurService.createFormateur(manager);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(path = "/vendeur", consumes = APPLICATION_JSON_VALUE)
  public void createApprenant(@RequestBody Utilisateur vendeur){
    this.utilisateurService.createApprenant(vendeur);
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
}
