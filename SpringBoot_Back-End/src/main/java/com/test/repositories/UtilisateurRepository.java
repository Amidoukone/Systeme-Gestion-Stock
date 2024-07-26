package com.test.repositories;

import com.test.entities.Role;
import com.test.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByUsername(String username);
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByEntrepotId(int entrepotId);
    List<Utilisateur> findByRoleAndEntrepotId(Role role, int entrepot_id);
    Optional<Utilisateur> findByUsernameOrEmail(String username, String email);
}
