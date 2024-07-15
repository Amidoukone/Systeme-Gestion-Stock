package com.groupe_4_ODK.RoyaleStock.config;

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
  private final UserDetailsService userDetailsService;

  public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
  }

  //script pour poser un filter sur mes endpointes
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(authz -> authz
        .requestMatchers("auth/connexion", "auth/deconnexion").permitAll()
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
