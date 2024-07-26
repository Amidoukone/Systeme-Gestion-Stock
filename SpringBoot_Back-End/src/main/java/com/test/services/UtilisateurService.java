package com.test.services;

import com.test.entities.Entrepot;
import com.test.entities.Role;
import com.test.entities.Utilisateur;
import com.test.repositories.EntrepotRepository;
import com.test.repositories.RoleRepository;
import com.test.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private UtilisateurRepository utilisateurRepository;

    private RoleRepository roleRepository;

    private EntrepotRepository entrepotRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, EntrepotRepository entrepotRepository, BCryptPasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.entrepotRepository = entrepotRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> findById(int id) {
        return utilisateurRepository.findById(id);
    }

    public void deleteById(int id) {
        utilisateurRepository.deleteById(id);
    }

    public List<Utilisateur> findByEntrepot(int entrepotId) {
        return utilisateurRepository.findByEntrepotId(entrepotId);
    }

    public Utilisateur update(Utilisateur utilisateur) {
        Optional<Utilisateur> existingUser = utilisateurRepository.findById(utilisateur.getId());
        if (existingUser.isPresent()) {
            Utilisateur userToUpdate = existingUser.get();
            userToUpdate.setUsername(utilisateur.getUsername());
            userToUpdate.setContact(utilisateur.getContact());
            userToUpdate.setEmail(utilisateur.getEmail());
            userToUpdate.setRole(utilisateur.getRole());
            userToUpdate.setEntrepot(utilisateur.getEntrepot());
            if (!utilisateur.getPassword().equals(userToUpdate.getPassword())) {
                userToUpdate.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            }
            return utilisateurRepository.save(userToUpdate);
        } else {
            return null;
        }
    }

    public Utilisateur createAdmin(String username, String email, String password) {
        validateEmail(email);
        return createUser(username, email, password, "ADMIN", null);
    }

    public Utilisateur createManager(String username, String email, String password, Entrepot entrepot) {
        validateEmail(email);
        checkUserAlreadyAssignedToEntrepot(email);
        return createUser(username, email, password, "MANAGER", entrepot);
    }

    public Utilisateur createVendeur(String username, String email, String password, Utilisateur manager) {
        validateEmail(email);
        checkUserAlreadyAssignedToEntrepot(email);
        if (manager == null || manager.getRole() == null || !manager.getRole().getName().equals("MANAGER")) {
            throw new RuntimeException("Seul un manager peut créer un vendeur.");
        }
        // Vérifier que le manager n'a pas déjà deux vendeurs
        List<Utilisateur> vendeurs = utilisateurRepository.findByRoleAndEntrepotId(manager.getRole(), manager.getEntrepot().getId());
        if (vendeurs.size() >= 2) {
            throw new RuntimeException("Un manager ne peut pas créer plus de deux vendeurs pour son entrepôt.");
        }
        return createUser(username, email, password, "VENDEUR", manager.getEntrepot());
    }


    private void validateEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new RuntimeException("Votre email est invalide");
        }
    }

    private void checkUserAlreadyAssignedToEntrepot( String email) {
        Optional<Utilisateur> existingUser = utilisateurRepository.findByEmail(email);
        if (existingUser.isPresent() && existingUser.get().getEntrepot() != null) {
            throw new RuntimeException("Cet utilisateur est déjà affecté à un entrepôt.");
        }
    }

    private Utilisateur createUser(String username, String email, String password, String roleName, Entrepot entrepot) {
        Optional<Utilisateur> existingUser = utilisateurRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Cet utilisateur existe déjà 😁");
        }

        Utilisateur newUser = new Utilisateur();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByName(roleName).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(roleName);
            return roleRepository.save(newRole);
        });
        newUser.setRole(role);

        if (entrepot != null) {
            newUser.setEntrepot(entrepot);
        }

        return utilisateurRepository.save(newUser);
    }

    public Optional<Entrepot> findEntrepotById(int entrepotId) {
        return entrepotRepository.findById(entrepotId);
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
