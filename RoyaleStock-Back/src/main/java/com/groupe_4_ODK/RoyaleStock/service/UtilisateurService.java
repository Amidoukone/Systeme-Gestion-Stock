package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService implements UserDetailsService {

  private UtilisateurRepository utilisateurRepository;

  public UtilisateurService(UtilisateurRepository utilisateurRepository) {
    this.utilisateurRepository = utilisateurRepository;
  }


  //script pour permettre a l'utilisateur de s'auth
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.utilisateurRepository
      .findByEmail(username)
      .orElseThrow( ()-> new UsernameNotFoundException("Utilisateur indisponible"));
  }
}
