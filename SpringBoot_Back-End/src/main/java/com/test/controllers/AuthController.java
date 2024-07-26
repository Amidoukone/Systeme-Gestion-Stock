package com.test.controllers;

import com.test.config.CustomUserDetails;
import com.test.entities.LoginRequest;
import com.test.entities.LoginResponse;
import com.test.services.AuthService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.test.entities.Utilisateur;
import com.test.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/utilisateurs")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Utilisateur utilisateur = authService.login(email, password);
        String token = authService.generateToken(utilisateur);
        String sessionId = authService.generateSessionId();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("username", utilisateur.getUsername());
        response.put("email", utilisateur.getEmail());
        response.put("role", utilisateur.getRole().getName());
        response.put("session", sessionId);
        response.put("id", utilisateur.getId());

        if (utilisateur.getEntrepot() != null) {
            Map<String, Object> entrepotInfo = new HashMap<>();
            entrepotInfo.put("entrepotId", utilisateur.getEntrepot().getId());
            entrepotInfo.put("entrepotNom", utilisateur.getEntrepot().getEntrepotName());
            entrepotInfo.put("entrepotAdresse", utilisateur.getEntrepot().getLieu());
            /*entrepotInfo.put("entrepotDebutAbonnementStart", utilisateur.getEntrepot().getAbonnementStart());
            entrepotInfo.put("entrepotDebutAbonnementEnd", utilisateur.getEntrepot().getAbonnementEnd());
            entrepotInfo.put("entrepotDebutCreate", utilisateur.getEntrepot().getDateCreate());
            entrepotInfo.put("entrepotStatut", utilisateur.getEntrepot().getStatut());*/
            response.put("entrepot", entrepotInfo);
        }
        return ResponseEntity.ok(response);
    }
}
