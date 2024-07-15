package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @PostMapping("/connexion")
  public String login(Authentication authentication) {
    return "Utilisateur connecte: " + authentication.getName();
  }

  @GetMapping("/deconnexion")
  public String logout() {
    return "Utilisateur deconnecter avc success";
  }
}
