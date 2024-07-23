package com.test.config;

import com.test.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.requestMatchers("/api/utilisateurs/login").permitAll()
                //.requestMatchers("/api/utilisateurs/**", "/api/roles/**", "/api/entrepots/**").hasRole("ADMIN")
                //.requestMatchers("/api/bonentrees/**", "/api/detailsentrees/**").hasAnyRole("ADMIN", "MANAGER")
                //.requestMatchers("/api/bonsorties/**", "/api/detailssorties/**").hasAnyRole("ADMIN", "MANAGER")
                //.requestMatchers("/api/produits/**").hasAnyRole("ADMIN", "MANAGER", "VENDEUR")
                //.requestMatchers("/api/fournisseurs/**", "/api/categories/**", "/api/notifications/**").hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                //.loginProcessingUrl("/api/utilisateurs/login")
                //.usernameParameter("pseudo")
                //.passwordParameter("motdepasse")
                //.defaultSuccessUrl("/dashboard", true)
                .and()
                .logout().permitAll()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
