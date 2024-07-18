package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.enums.TypeRole;
import com.groupe_4_ODK.RoyaleStock.repository.EntrepotsRepository;
import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EntrepotsService {

  @Autowired
  private EntrepotsRepository entrepotsRepository;

  @Autowired
  private UtilisateurRepository utilisateurRepository;

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
  public Entrepots assignManager(Long entrepotId, Long userId) throws Exception {
    Entrepots entrepot = entrepotsRepository.findById(entrepotId)
      .orElseThrow(() -> new Exception("Entrepot not found"));

    Utilisateur manager = utilisateurRepository.findById(userId)
      .orElseThrow(() -> new Exception("Utilisateur not found"));

    if (manager.getRole().getTypeRole() != TypeRole.Manager) {
      throw new Exception("Utilisateur is not a Manager");
    }

    for (Utilisateur utilisateur : entrepot.getUtilisateurs()) {
      if (utilisateur.getRole().getTypeRole() == TypeRole.Manager) {
        throw new Exception("This entrepot already has a manager");
      }
    }

    entrepot.getUtilisateurs().add(manager);
    return entrepotsRepository.save(entrepot);
  }

  public Entrepots assignVendeur(Long entrepotId, Long userId) throws Exception {
    Entrepots entrepot = entrepotsRepository.findById(entrepotId)
      .orElseThrow(() -> new Exception("Entrepot not found"));

    Utilisateur vendeur = utilisateurRepository.findById(userId)
      .orElseThrow(() -> new Exception("Utilisateur not found"));

    if (vendeur.getRole().getTypeRole() != TypeRole.Vendeur) {
      throw new Exception("Utilisateur is not a Vendeur");
    }

    long vendeurCount = entrepot.getUtilisateurs().stream()
      .filter(user -> user.getRole().getTypeRole() == TypeRole.Vendeur)
      .count();

    if (vendeurCount >= 2) {
      throw new Exception("This entrepot already has two vendeurs");
    }

    entrepot.getUtilisateurs().add(vendeur);
    return entrepotsRepository.save(entrepot);
  }

  public boolean isAccessible(Entrepots entrepot) {
    Date currentDate = new Date();
    return entrepot.getFinAbonnement().after(currentDate);
  }

}
