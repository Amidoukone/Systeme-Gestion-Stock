package com.groupe_4_ODK.RoyaleStock.config;


import com.groupe_4_ODK.RoyaleStock.service.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final UtilisateurService utilisateurService;

  public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UtilisateurService utilisateurService) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.utilisateurService =utilisateurService;
  }

  @Bean
  public CommandLineRunner initAdmin() {
    return args -> utilisateurService.createDefaultAdmin();
  }

  //script pour poser un filter sur mes endpointes
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(authz -> authz
        .requestMatchers("auth/connexion", "auth/deconnexion").permitAll()
        .requestMatchers("/api/utilisateurs/admin","/api/utilisateurs/manager","/api/utilisateurs/vendeur", "/api/entrepots/{entrepotId}/manager/{userId}", "/api/entrepots/create").hasRole("Admin")
        .requestMatchers("/api/utilisateurs/vendeur").hasRole("Manager")
        .anyRequest().authenticated()
      )
      .httpBasic(withDefaults());

    return http.build();
  }

  //script pour le cryptage de mots de passe
  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
    return daoAuthenticationProvider;
  }


}
