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

  public void createAdmin(Utilisateur admin) {
    createUtilisateur(admin, TypeRole.Admin);
  }

  public void createManager(Utilisateur manager) {
    createUtilisateur(manager, TypeRole.Manager);
  }

  public void createVendeur(Utilisateur vendeur) {
    createUtilisateur(vendeur, TypeRole.Vendeur);
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
  public Role getOrCreateRole(TypeRole roleType) {
    return roleRepository.findByTypeRole(roleType).orElseGet(() -> {
      Role newRole = new Role();
      newRole.setTypeRole(roleType);
      return roleRepository.save(newRole);
    });
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
      admin.setContact("67567854");
      admin.setRole(getOrCreateRole(TypeRole.Admin));
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
