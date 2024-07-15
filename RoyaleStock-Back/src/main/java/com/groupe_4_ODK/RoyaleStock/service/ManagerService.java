package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Manager;
import com.groupe_4_ODK.RoyaleStock.entite.Role;
import com.groupe_4_ODK.RoyaleStock.repository.ManagerRepository;
import com.groupe_4_ODK.RoyaleStock.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ManagerService {
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private ManagerRepository managerRepository;

  //List Manager
  public List<Manager> allManager(){
    return managerRepository.findAll();
  }
  public Manager createManager(Manager manager){
    Role role = roleRepository.findByNom("Manager" ) ;
    manager.setRole(role);
    return managerRepository.save(manager);
  }
  public void deleteManager(Long id){
    managerRepository.deleteById(Math.toIntExact(id));
  }

  //update Admin
  public Manager updateManager(Long id, Manager manager) {
    if (managerRepository.existsById(Math.toIntExact(id))) {
      Manager existingManager = managerRepository.findById(Math.toIntExact(id)).orElse(null);
      if (existingManager != null) {
        if (manager.getNom() != null && !manager.getNom().equals(existingManager.getNom())) {
          existingManager.setNom(manager.getNom());
        }
        if (manager.getEmail() != null && !manager.getEmail().equals(existingManager.getEmail())) {
          existingManager.setEmail(manager.getEmail());
        }
        if (manager.getPassword() != null && ! existingManager.getPassword().equals(existingManager.getPassword())) {
          existingManager.setPassword(manager.getPassword());
        }
        if (manager.getContact() != null && ! existingManager.getContact().equals(existingManager.getContact())) {
          existingManager.setContact(manager.getContact());
        }
        return managerRepository.save(existingManager);
      }
    }
    return null;
}
}
