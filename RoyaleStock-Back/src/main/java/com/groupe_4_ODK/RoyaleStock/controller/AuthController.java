package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
  private final AuthenticationManager authenticationManager;

  public AuthController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
  @PostMapping("/connexion")
  public String login(@RequestBody Utilisateur loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return "Utilisateur connecté: " + authentication.getName();
    } catch (AuthenticationException e) {
      return "Échec de l'authentification";
    }
  }

  @GetMapping("/deconnexion")
  public String logout() {
    SecurityContextHolder.clearContext();
    return "Utilisateur déconnecté avec succès";
  }
}
