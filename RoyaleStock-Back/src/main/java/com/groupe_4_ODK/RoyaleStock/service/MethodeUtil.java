package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MethodeUtil {
  private final UtilisateurRepository utilisateurRepository;

  public MethodeUtil(UtilisateurRepository utilisateurRepository) {
    this.utilisateurRepository = utilisateurRepository;
  }

  public Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof Utilisateur) {
      String email = ((Utilisateur) authentication.getPrincipal()).getEmail();
      Utilisateur user = utilisateurRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
      return user.getId();
    }
    return null;
  }

  public Entrepots getEntrepotByUserId(Long userId) {
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
}
