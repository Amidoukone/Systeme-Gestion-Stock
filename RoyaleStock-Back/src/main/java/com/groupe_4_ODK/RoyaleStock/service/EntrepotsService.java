package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.repository.EntrepotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepotsService {

  @Autowired
  private EntrepotsRepository entrepotsRepository;

  // Methode pour afficher la liste des entrepots
  public List<Entrepots> findAll() {
    return entrepotsRepository.findAll();
  }

  // Methode pour rechercher un entrepot par Id
  public Optional<Entrepots> findById(Long id) {
    return entrepotsRepository.findById(id);
  }

  // Methode pour creer un entrepot
  public Entrepots save(Entrepots entrepot) {
    return entrepotsRepository.save(entrepot);
  }

  // Methode pour supprimer un entrepot par son id
  public void deleteById(Long id) {
    entrepotsRepository.deleteById(id);
  }

  // Methode pour modifier un entrepot
  public Entrepots updateEntrepot(Long id, Entrepots updatedEntrepot) {
    return entrepotsRepository.findById(id).map(entrepot -> {
      entrepot.setNom(updatedEntrepot.getNom());
      entrepot.setLieu(updatedEntrepot.getLieu());
      entrepot.setStatut(updatedEntrepot.getStatut());
      entrepot.setUtilisateurs(updatedEntrepot.getUtilisateurs());
      entrepot.setProduits(updatedEntrepot.getProduits());
      return entrepotsRepository.save(entrepot);
    }).orElseGet(() -> {
      updatedEntrepot.setId(id);
      return entrepotsRepository.save(updatedEntrepot);
    });
  }
}
