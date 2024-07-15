package com.groupe_4_ODK.RoyaleStock.Services;

import com.groupe_4_ODK.RoyaleStock.entite.Fournisseurs;
import com.groupe_4_ODK.RoyaleStock.Repositories.FournisseursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FournisseursServices {

  @Autowired
  private FournisseursRepository fournisseursRepository;

  // Méthode pour afficher la liste des fournisseurs
  public List<Fournisseurs> findAll() {
    return fournisseursRepository.findAll();
  }

  // Méthode pour rechercher un fournisseur par Id
  public Optional<Fournisseurs> findById(int id) {
    return fournisseursRepository.findById(id);
  }

  // Méthode pour créer un fournisseur
  public Fournisseurs save(Fournisseurs fournisseur) {
    return fournisseursRepository.save(fournisseur);
  }

  // Méthode pour supprimer un fournisseur par son id
  public void deleteById(int id) {
    fournisseursRepository.deleteById(id);
  }

  // Méthode pour modifier un fournisseur
  public Fournisseurs updateFournisseur(int id, Fournisseurs updatedFournisseur) {
    return fournisseursRepository.findById(id).map(fournisseur -> {
      fournisseur.setNom(updatedFournisseur.getNom());
      fournisseur.setAdresse(updatedFournisseur.getAdresse());
      fournisseur.setTelephone(updatedFournisseur.getTelephone());
      fournisseur.setBonEntrees(updatedFournisseur.getBonEntrees());
      return fournisseursRepository.save(fournisseur);
    }).orElseGet(() -> {
      updatedFournisseur.setId(id);
      return fournisseursRepository.save(updatedFournisseur);
    });
  }
}
