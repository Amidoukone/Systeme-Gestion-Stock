package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
  private final AuthenticationManager authenticationManager;

  public AuthController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
 /* @PostMapping("/connexion")
  public ResponseEntity<String> login(@RequestBody Utilisateur loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return ResponseEntity.ok("Utilisateur connecté: " + authentication.getName());
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification");
    }
  }*/
 @PostMapping("/connexion")
 public ResponseEntity<?> login(@RequestBody Utilisateur loginRequest) {
   try {
     Authentication authentication = authenticationManager.authenticate(
       new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
     );
     SecurityContextHolder.getContext().setAuthentication(authentication);

     Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
     List<String> roles = utilisateur.getAuthorities().stream()
       .map(GrantedAuthority::getAuthority)
       .collect(Collectors.toList());

     Map<String, Object> response = new HashMap<>();
     response.put("email", utilisateur.getEmail());
     response.put("roles", roles);

     return ResponseEntity.ok(response);
   } catch (AuthenticationException e) {
     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification");
   }
 }


  @GetMapping("/deconnexion")
  public String logout() {
    SecurityContextHolder.clearContext();
    return "Utilisateur déconnecté avec succès";
  }
}
