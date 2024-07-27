package com.test.services;

import com.test.entities.Entrepot;
import com.test.entities.Utilisateur;
import com.test.repositories.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MethodeUtil {

    private final UtilisateurRepository utilisateurRepository;
    private static final Logger logger = Logger.getLogger(MethodeUtil.class.getName());

    public MethodeUtil(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Utilisateur) {
            String email = ((Utilisateur) authentication.getPrincipal()).getEmail();
            logger.info("Authenticated user email: " + email);
            Utilisateur user = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            return user.getId();
        }
        logger.warning("Authentication is null or principal is not an instance of Utilisateur.");
        return null;
    }

    public Entrepot getEntrepotByUserId(Integer userId) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return user.getEntrepot();
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Utilisateur) {
            return ((Utilisateur) authentication.getPrincipal()).getEmail();
        }
        return null;
    }

  /*  private final UtilisateurRepository utilisateurRepository;

    public MethodeUtil(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Utilisateur) {
            String email = ((Utilisateur) authentication.getPrincipal()).getEmail();
            Utilisateur user = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            return user.getId();
        }
        return null;
    }

    public Entrepot getEntrepotByUserId(Integer userId) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return user.getEntrepot();
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Utilisateur) {
            return ((Utilisateur) authentication.getPrincipal()).getEmail();
        }
        return null;
    }*/
}
