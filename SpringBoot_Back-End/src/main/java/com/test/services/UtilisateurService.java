package com.test.services;

import com.test.entities.Entrepot;
import com.test.entities.Role;
import com.test.entities.Utilisateur;
import com.test.repositories.EntrepotRepository;
import com.test.repositories.RoleRepository;
import com.test.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final EntrepotRepository entrepotRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MethodeUtil methodeUtil;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, EntrepotRepository entrepotRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MethodeUtil methodeUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.entrepotRepository = entrepotRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.methodeUtil = methodeUtil;
    }
    public Utilisateur createAdmin(String username, String contact, String email, String password) {
        return createUser(username, contact, email, password, "ADMIN", null);
    }

    /*public Utilisateur createManager(String username, String contact, String email, String password, Entrepot entrepot) {
        validateEmail(email);
        checkUserAlreadyAssignedToEntrepot(email);
        return createUser(username,contact, email, password,"MANAGER", entrepot);
    }*/

    public Utilisateur createManager(String username, String email, String contact, String password) {
        Integer userId = methodeUtil.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("Utilisateur non authentifié.");
        }

        Utilisateur admin = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        if (!"ADMIN".equals(admin.getRole().getName())) {
            throw new RuntimeException("Seul un admin peut créer un manager.");
        }

        Entrepot entrepot = null;
        if (methodeUtil.getEntrepotByUserId(userId) != null) {
            throw new RuntimeException("Cet utilisateur est déjà affecté à un entrepôt.");
        }
        return createUser(username, contact, email, password, "MANAGER", entrepot);
    }

    public Utilisateur createVendeur(String username, String contact, String email, String password) {
        validateEmail(email);
        checkUserAlreadyAssignedToEntrepot(email);

        Integer userId = methodeUtil.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("Utilisateur non authentifié.");
        }

        Utilisateur manager = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Manager non trouvé"));

        if (!"MANAGER".equals(manager.getRole().getName())) {
            throw new RuntimeException("Seul un manager peut créer un vendeur.");
        }

        List<Utilisateur> vendeurs = utilisateurRepository.findByRoleAndEntrepotId(manager.getRole(), manager.getEntrepot().getId());
        if (vendeurs.size() >= 2) {
            throw new RuntimeException("Un manager ne peut pas créer plus de deux vendeurs pour son entrepôt.");
        }

        Entrepot entrepot = manager.getEntrepot();
        return createUser(username, contact, email, password, "VENDEUR", entrepot);
    }

    private Utilisateur createUser(String username, String contact, String email, String password, String roleName, Entrepot entrepot) {
        validateEmail(email);
        checkUserAlreadyAssignedToEntrepot(email);

        Optional<Utilisateur> existingUser = utilisateurRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Cet utilisateur existe déjà.");
        }

        Utilisateur newUser = new Utilisateur();
        newUser.setUsername(username);
        newUser.setContact(contact);
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));

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

    private void validateEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new RuntimeException("Votre email est invalide");
        }
    }

    private void checkUserAlreadyAssignedToEntrepot(String email) {
        Optional<Utilisateur> existingUser = utilisateurRepository.findByEmail(email);
        if (existingUser.isPresent() && existingUser.get().getEntrepot() != null) {
            throw new RuntimeException("Cet utilisateur est déjà affecté à un entrepôt.");
        }
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
                userToUpdate.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
            }
            return utilisateurRepository.save(userToUpdate);
        } else {
            return null;
        }
    }

    public Optional<Entrepot> findEntrepotById(int entrepotId) {
        return entrepotRepository.findById(entrepotId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur indisponible"));
    }
}
/*

    private UtilisateurRepository utilisateurRepository;

    private RoleRepository roleRepository;

    private EntrepotRepository entrepotRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final MethodeUtil methodeUtil;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, EntrepotRepository entrepotRepository, MethodeUtil methodeUtil, BCryptPasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.entrepotRepository = entrepotRepository;
        this.passwordEncoder = passwordEncoder;
        this.methodeUtil = methodeUtil;
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

    public Utilisateur createVendeur(String username, String email, String password) {
        validateEmail(email);
        checkUserAlreadyAssignedToEntrepot(email);
        Integer userId = methodeUtil.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("Utilisateur non authentifié.");
        }

        Utilisateur manager = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Manager non trouvé"));

        if (!"MANAGER".equals(manager.getRole().getName())) {
            throw new RuntimeException("Seul un manager peut créer un vendeur.");
        }

        List<Utilisateur> vendeurs = utilisateurRepository.findByRoleAndEntrepotId(manager.getRole(), manager.getEntrepot().getId());
        if (vendeurs.size() >= 2) {
            throw new RuntimeException("Un manager ne peut pas créer plus de deux vendeurs pour son entrepôt.");
        }

        Entrepot managerEntrepot = methodeUtil.getEntrepotByUserId(userId);
        return createUser(username, email, password, "VENDEUR", managerEntrepot);
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
 */
