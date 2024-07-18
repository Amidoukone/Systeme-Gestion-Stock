package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Role;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.enums.TypeRole;
import com.groupe_4_ODK.RoyaleStock.repository.RoleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;

import java.util.List;

@Service
public class UtilisateurService implements UserDetailsService {

  private final UtilisateurRepository utilisateurRepository;
  private final RoleRepository roleRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  public UtilisateurService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
    this.utilisateurRepository = utilisateurRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  private Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      String username = ((UserDetails) authentication.getPrincipal()).getUsername();
      Utilisateur utilisateur = utilisateurRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Utilisateur indisponible"));
      return utilisateur.getId();
    }
    return null;
  }

  public List<Utilisateur> getAllUtilisateurs() {
    return utilisateurRepository.findAll();
  }

  public Utilisateur findUtilisateurByName(String nom) {
    return utilisateurRepository.findByNom(nom);
  }

  public Utilisateur createUtilisateur(Utilisateur utilisateur, TypeRole typeRole) {
    Role role = roleRepository.findByTypeRole(typeRole)
      .orElseThrow(() -> new RuntimeException("Role non trouvé"));
    utilisateur.setRole(role);
    utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
    utilisateur.setCreatedBy(getCurrentUserId());
    return utilisateurRepository.save(utilisateur);
  }

  public void createDefaultAdmin() {
    if (utilisateurRepository.findByEmail("admin@default.com").isEmpty()) {
      Utilisateur admin = new Utilisateur();
      admin.setNom("Admin");
      admin.setEmail("admin@gmail.com");
      admin.setPassword("12345");
      admin.setContact("0000000000");
      admin.setRole(roleRepository.findByTypeRole(TypeRole.Admin).orElseThrow(() -> new RuntimeException("Role Admin indisponible")));
      utilisateurRepository.save(admin);
    }
  }

  //script pour permettre a l'utilisateur de s'auth
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.utilisateurRepository
      .findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("Utilisateur indisponible"));
  }

}
