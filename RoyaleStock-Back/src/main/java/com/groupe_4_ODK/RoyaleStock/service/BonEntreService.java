package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BonEntreService {
  @Autowired
  private BonEntreRepository bonEntreRepository;

  @Autowired
  private DetailsEntreRepository detailEntreRepository;
  @Autowired
  private ProduitsRepository produitRepository;
  @Autowired
  private AdminRepository adminRepository;
  @Autowired
  private ManagerRepository managerRepository;
  @Autowired
  private FournisseursRepository fournisseurRepository;
  @Autowired
  private DetailsEntreRepository detailsEntreRepository;



  // Méthode pour récupérer tous les BonEntre
  public List<BonEntrees> getAllBonEntres() {
    return bonEntreRepository.findAll();
  }

  // Méthode pour récupérer un BonEntre par son ID
  public Optional<BonEntrees> getBonEntreById(Integer id) {
    return bonEntreRepository.findById(id);
  }
  @Transactional
  public BonEntrees creeBonEntre(BonEntrees bonEntre) {
    if (bonEntre.getDateCommande() == null) {
      bonEntre.setDateCommande(new Date()); // Utilisation de la date actuelle
    }

    // Vérification et mise à jour des entités associées
//    if (bonEntre.getManager() != null && bonEntre.getManager().getId() != null) {
//      bonEntre.setManager(managerRepository.findById(bonEntre.getManager().getId()).orElse(null));
//    }
//    if (bonEntre.getAdmin() != null && bonEntre.getAdmin().getId() != null) {
//      bonEntre.setAdmin(adminRepository.findById(bonEntre.getAdmin().getId()).orElse(null));
//    }
//    if (bonEntre.getFournisseurs() != null && bonEntre.getFournisseurs().getId() != null) {
//      bonEntre.setFournisseurs(fournisseurRepository.findById(bonEntre.getFournisseurs().getId()).orElse(null));
//    }

    // Sauvegarder le BonEntre
    bonEntre = bonEntreRepository.save(bonEntre);

    // Mettre à jour la quantité du produit pour chaque DetailsEntre
    for (DetailsEntrees detailsEntre : bonEntre.getDetailsEntrees()) {
      detailsEntre.setBonEntrees(bonEntre);
      if (detailsEntre.getProduits() != null && detailsEntre.getProduits().getId() != null) {
        Produits produit = produitRepository.findById(detailsEntre.getProduits().getId()).orElse(null);
        if (produit != null) {
          // Ajouter la quantité spécifiée à la quantité actuelle du produit
          produit.setQuantite(produit.getQuantite() + Integer.parseInt(String.valueOf(detailsEntre.getProduits().getQuantite())));
          produitRepository.save(produit);
        }
        detailsEntre.setProduits(produit);
      }
      detailsEntreRepository.save(detailsEntre);
    }

    return bonEntre;
  }
  // Méthode pour mettre à jour un BonEntre existant
  public BonEntrees updateBonEntre(Integer id, BonEntrees bonEntre) {
    Optional<BonEntrees> existingBonEntreOptional = bonEntreRepository.findById(id);
    if (existingBonEntreOptional.isEmpty()) {
      // Gérer l'exception ou retourner null selon votre logique
      return null;
    }

    BonEntrees existingBonEntre = existingBonEntreOptional.get();
    existingBonEntre.setDateCommande(bonEntre.getDateCommande());
    existingBonEntre.setStatut(bonEntre.getStatut());
    existingBonEntre.setDetailsEntrees(bonEntre.getDetailsEntrees());
    existingBonEntre.setManager(bonEntre.getManager());
    existingBonEntre.setAdmin(bonEntre.getAdmin());
    existingBonEntre.setFournisseurs(bonEntre.getFournisseurs());

    // Sauvegarder et retourner le BonEntre mis à jour
    return bonEntreRepository.save(existingBonEntre);
  }

  // Méthode pour supprimer un BonEntre par son ID
  public void deleteBonEntre(Integer id) {
    bonEntreRepository.deleteById(id);
  }
  // Méthode pour récupérer les DetailsEntre d'un BonEntre par son ID
  public List<DetailsEntrees> getDetailsEntresByBonEntreId(Long bonEntreId) {
    Optional<BonEntrees> bonEntreOptional = bonEntreRepository.findById(Math.toIntExact(bonEntreId));
    return bonEntreOptional.map(BonEntrees::getDetailsEntrees).orElse(null);
  }
  // Méthode pour récupérer un DetailsEntre par son ID
  public Optional<DetailsEntrees> getDetailsEntreById(Integer id) {
    return detailsEntreRepository.findById(id);
  }
}
