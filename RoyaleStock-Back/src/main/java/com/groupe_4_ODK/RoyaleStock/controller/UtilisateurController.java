package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Role;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.repository.RoleRepository;
import com.groupe_4_ODK.RoyaleStock.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UtilisateurController {

  private UtilisateurService utilisateurService;
  private RoleRepository roleRepository;

  public UtilisateurController(UtilisateurService utilisateurService, RoleRepository roleRepository) {
    this.utilisateurService = utilisateurService;
    this.roleRepository = roleRepository;
  }

  @PostMapping
  @RequestMapping(path = "utilisateur", consumes = APPLICATION_JSON_VALUE)
  private Utilisateur create(@RequestBody Utilisateur utilisateur){
    return utilisateurService.createUtilisateur(utilisateur);
  }
}
