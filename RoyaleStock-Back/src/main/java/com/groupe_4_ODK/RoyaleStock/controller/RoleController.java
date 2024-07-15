package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Role;
import com.groupe_4_ODK.RoyaleStock.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {
  private RoleService roleService;

  @PostMapping("/create")
  public Role createRole(@RequestBody Role role) {
    return roleService.createRole(role);
  }

  @PutMapping("/update/{id}")
  public Role updateRole(@RequestBody Role role, @PathVariable int id) {
    return roleService.updateRole(role,id);
  }

  @DeleteMapping("/delete/{id}")
  public String deleteRole(@PathVariable int id) {
    return roleService.deleteRole(id);
  }

  @GetMapping
  public List<Role> readRole() {
    return roleService.readRole();
  }
}
