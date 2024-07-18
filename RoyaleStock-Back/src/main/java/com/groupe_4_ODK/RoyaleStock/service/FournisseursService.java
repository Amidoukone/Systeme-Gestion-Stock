package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Fournisseurs;
import com.groupe_4_ODK.RoyaleStock.repository.FournisseursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FournisseursService {

  @Autowired
  private FournisseursRepository fournisseursRepository;

  // methode pour afficher la liste des fourisseurs
  public List<Fournisseurs> findAll() {
    return fournisseursRepository.findAll();
  }

  // Methode pour rechercher un fournisseur par Id
  public Optional<Fournisseurs> findById(int id) {
    return fournisseursRepository.findById(id);
  }

  // Methode pour creer un fournisseur
  public Fournisseurs save(Fournisseurs fournisseur) {
    return fournisseursRepository.save(fournisseur);
  }

  //Methode pour supprimer un fournisseur par son id
  public void deleteById(int id) {
    fournisseursRepository.deleteById(id);
  }

  // Methode pour modifier un fournisseur
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
  public int getFournisseursCount() {
    return fournisseursRepository.countFournisseurs();
  }

}
