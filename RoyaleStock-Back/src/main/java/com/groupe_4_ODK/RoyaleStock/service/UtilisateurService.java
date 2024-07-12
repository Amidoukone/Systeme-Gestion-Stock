package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Role;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.repository.RoleRepository;
import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService implements UserDetailsService {

  private UtilisateurRepository utilisateurRepository;
  private RoleRepository roleRepository;

  public UtilisateurService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository) {
    this.utilisateurRepository = utilisateurRepository;
    this.roleRepository = roleRepository;
  }

  public Utilisateur createUtilisateur(Utilisateur utilisateur){
    Role neRole = new Role();
    neRole.setNom("Admin");
    roleRepository.save(neRole);
    utilisateur.setRole(neRole);
      Utilisateur utilisateur1 =  utilisateurRepository.save(utilisateur);
      return utilisateur1;
    }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.utilisateurRepository
      .findByEmail(username)
      .orElseThrow( ()-> new UsernameNotFoundException("Utilisateur indisponible"));
  }
}
