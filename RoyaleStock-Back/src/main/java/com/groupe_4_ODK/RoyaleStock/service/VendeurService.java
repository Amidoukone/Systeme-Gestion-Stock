package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Role;
import com.groupe_4_ODK.RoyaleStock.entite.Vendeur;
import com.groupe_4_ODK.RoyaleStock.repository.RoleRepository;
import com.groupe_4_ODK.RoyaleStock.repository.VendeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VendeurService {
  @Autowired
  private VendeurRepository vendeurRepository;
  @Autowired
  private RoleRepository roleRepository;

  //liste Produit
  public List<Vendeur> allVendeurs() {
    return  vendeurRepository.findAll();
  }

  //GET BY id
  public Optional<Vendeur> getVendeurById(Integer id) {
    return vendeurRepository.findById(id);
  }
  //Create Vendeur
  public Vendeur addVendeur(Vendeur vendeur) {
    Role role = roleRepository.findByNom("Vendeur");
    vendeur.setRole(role);
    return  vendeurRepository.save(vendeur);
  }
  //Delete Vendeur
  public void deleteVendeur(){
    vendeurRepository.deleteAll();
  }

  // Update Vendeur
  public Vendeur updateVendeur(Integer id, Vendeur vendeur) {
    if (vendeurRepository.existsById(id)) {
      Vendeur existingVendeur = vendeurRepository.findById(id).orElse(null);
      if (existingVendeur != null) {
        if (vendeur.getNom() != null && !vendeur.getNom().equals(existingVendeur.getNom())) {
          existingVendeur.setNom(vendeur.getNom());
        }
        if (vendeur.getEmail() != null && !vendeur.getEmail().equals(existingVendeur.getEmail())) {
          existingVendeur.setEmail(vendeur.getEmail());
        }
        if (vendeur.getPassword() != null && !vendeur.getPassword().equals(existingVendeur.getPassword())) {
          existingVendeur.setPassword(vendeur.getPassword());
        }
        return vendeurRepository.save(existingVendeur);
      }
    }
    return null;
  }
}
