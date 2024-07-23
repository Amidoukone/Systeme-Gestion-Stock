package com.groupe_4_ODK.RoyaleStock.entite;

import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utilisateur")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;


  private String nom;
  private String contact;
  @Email
  private String email;
  private String password;
  private boolean actif=true;
  private Long createdBy;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;
 /*@ManyToOne
  @JoinColumn(name = "createdBy")
  private Utilisateur createdBy;*/

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "entrepot_id")
  private Entrepots entrepot;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "notification_id")
  private Notification notification;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.getTypeRole()));
  }


  @Override
  public String getUsername() {
    return email;
  }
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return actif;
  }

  @Override
  public boolean isAccountNonLocked() {
    return actif;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return actif;
  }

  @Override
  public boolean isEnabled() {
    return actif;
  }
}
