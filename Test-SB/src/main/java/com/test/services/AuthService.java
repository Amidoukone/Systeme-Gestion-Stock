package com.test.services;

import com.test.entities.Utilisateur;
import com.test.repositories.UtilisateurRepository;
import com.test.entities.LoginRequest;
import com.test.entities.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        logger.info("Attempting to log in user with email: {}", request.getEmail());

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", request.getEmail());
                    return new RuntimeException("User not found");
                });

        if (passwordEncoder.matches(request.getPassword(), utilisateur.getPassword())) {
            logger.info("User logged in successfully: {}", request.getEmail());
            // Generate a JWT token or any other login response
            return new LoginResponse("Login successful", "generated-jwt-token");
        } else {
            logger.error("Invalid credentials for user: {}", request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }
    }
}
