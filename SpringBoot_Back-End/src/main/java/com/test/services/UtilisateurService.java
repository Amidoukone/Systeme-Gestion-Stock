package com.test.services;

import com.test.entities.Utilisateur;
import com.test.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> findAll() {

        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> findById(int id) {

        return utilisateurRepository.findById(id);
    }

    public Utilisateur save(Utilisateur utilisateur) {

        return utilisateurRepository.save(utilisateur);
    }

    public void deleteById(int id) {

        utilisateurRepository.deleteById(id);
    }
}

/*
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

  private void validateEmail(String email) {
    if (!email.contains("@") || !email.contains(".")) {
      throw new RuntimeException("Votre email est invalide");
    }
  }

  private void checkIfEmailExists(String email) {
    if (utilisateurRepository.findByEmail(email).isPresent()) {
      throw new RuntimeException("Votre email est déjà utilisé");
    }
  }

  public Long getCurrentUserId() {
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
    validateEmail(utilisateur.getEmail());
    checkIfEmailExists(utilisateur.getEmail());
    utilisateur.setRole(getOrCreateRole(typeRole));
    utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
    utilisateur.setCreatedBy(getCurrentUserId());
    return utilisateurRepository.save(utilisateur);
  }

  public void createDefaultAdmin() {
    if (utilisateurRepository.findByEmail("admin@gmail.com").isEmpty()) {
      Utilisateur admin = new Utilisateur();
      admin.setNom("Admin");
      admin.setEmail("admin@gmail.com");
      admin.setPassword("$2a$10$0vZrUM15Q32e.2G8DAhYL.AGCaZUr.5xtmnfMZ/OfXgZzTXGS46Qm");
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
 */
