package com.test.services;

import com.test.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UtilisateurService utilisateurService) {
        this.authenticationManager = authenticationManager;
        this.utilisateurService = utilisateurService;
    }

    public Utilisateur login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.getPrincipal() instanceof Utilisateur) {
            Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
            return utilisateur;
        } else {
            throw new RuntimeException("Authentication principal is not an instance of Utilisateur");
        }
    }

    public String generateToken(Utilisateur utilisateur) {
        try {
            String tokenData = utilisateur.getEmail() + ":" + utilisateur.getPassword() + ":" + System.currentTimeMillis();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(tokenData.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }
    /*
        @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return utilisateurRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
     */
}
