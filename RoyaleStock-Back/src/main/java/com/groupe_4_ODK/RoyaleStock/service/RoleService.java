package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Role;

import java.util.List;

public interface RoleService {
  Role createRole(Role role);
  Role updateRole(Role role, int id);
  String deleteRole(int id);
  List<Role> readRole();
}
