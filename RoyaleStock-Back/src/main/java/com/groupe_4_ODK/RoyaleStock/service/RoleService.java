package com.groupe_4_ODK.RoyaleStock.service;
import com.groupe_4_ODK.RoyaleStock.entite.Role;
import com.groupe_4_ODK.RoyaleStock.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService{
  private final RoleRepository roleRepository;


  public Role createRole(Role role) {
    return roleRepository.save(role);
  }


  public Role updateRole(Role role, int id) {
    Optional<Role> roles = roleRepository.findById(id);
    if (roles.isPresent()) {
      Role r  = roles.get();
      r.setTypeRole(role.getTypeRole());
      Role updateRole = roleRepository.save(r);
      return updateRole;
    }
    else {
      return null;
    }
  }


  public String deleteRole(int id) {
    if (roleRepository.existsById(id)) {
      roleRepository.deleteById(id);
      return "Supprimé avec succès";
    } else {
      return "Non trouvé";
    }

  }

  public Role findById(int id) {
    return roleRepository.findById(id).orElse(null);}


  public List<Role> readRole() {
    return  roleRepository.findAll();
  }
  public int nombreRole() {
    return roleRepository.countRole();
  }
}
