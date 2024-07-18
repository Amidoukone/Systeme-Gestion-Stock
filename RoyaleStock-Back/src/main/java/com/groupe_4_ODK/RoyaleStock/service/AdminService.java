package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Admin;
import com.groupe_4_ODK.RoyaleStock.entite.Role;
import com.groupe_4_ODK.RoyaleStock.repository.AdminRepository;
import com.groupe_4_ODK.RoyaleStock.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
  @Autowired
  private AdminRepository adminRepository;
  @Autowired
  private RoleRepository roleRepository;

  public List<Admin> allAdmin(){
    return adminRepository.findAll();
  }
  public Admin createAdmin(Admin admin){
    Role role = roleRepository.findByNom("Admin" ) ;
    admin.setRole(role);
    return this.adminRepository.save(admin);
  }
    public void deleteAdmin(Integer Id){
    if (Id != null){
      adminRepository.deleteById(Id);
      }
    }

  //update Admin
  public Admin updateAdmin(Integer id, Admin admin) {
    if (adminRepository.existsById(id)) {
      Admin existingAdmin = adminRepository.findById(id).orElse(null);
      if (existingAdmin != null) {
        if (admin.getNom() != null && !admin.getNom().equals(existingAdmin.getNom())) {
          existingAdmin.setNom(admin.getNom());
        }
        if (admin.getEmail() != null && !admin.getEmail().equals(existingAdmin.getEmail())) {
          existingAdmin.setEmail(admin.getEmail());
        }
        if (admin.getPassword() != null && ! admin.getPassword().equals(existingAdmin.getPassword())) {
          existingAdmin.setPassword(admin.getPassword());
        }
        return adminRepository.save(existingAdmin);
      }
    }
    return null;
  }

}
