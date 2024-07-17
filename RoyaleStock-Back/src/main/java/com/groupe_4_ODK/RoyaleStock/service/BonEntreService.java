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
  private DetailsEntreRepository detailsEntreRepository;

  // Méthode pour récupérer tous les BonEntre
  public List<BonEntrees> getAllBonEntrees() {
    return bonEntreRepository.findAll();
  }
  // Méthode pour récupérer un BonEntre par son ID
  public BonEntrees getBonEntreeById(Integer id) {
    return bonEntreRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("BonEntree non trouve avec ID: " + id));
  }
  //Creer un bon Entre
  @Transactional
  public BonEntrees creeBonEntre(BonEntrees bonEntre) {
    if (bonEntre.getDateCommande() == null) {
      bonEntre.setDateCommande(new Date()); // Utilisation de la date actuelle
    }
    bonEntre.setStatut("encours"); // Définir le statut par défaut à "encours"

    // Sauvegarder le BonEntree sans mettre à jour les quantités
    bonEntre = bonEntreRepository.save(bonEntre);

    // Associer les détails du BonEntree
    for (DetailsEntrees detailsEntre : bonEntre.getDetailsEntrees()) {
      if (detailsEntre.getProduits() != null && detailsEntre.getProduits().getId() != null) {
        Produits produit = produitRepository.findById(detailsEntre.getProduits().getId())
          .orElseThrow(() -> new RuntimeException("Pas de produit avec ce ID: " + detailsEntre.getProduits().getId()));
        detailsEntre.setProduits(produit);
      } else {
        throw new RuntimeException("Produit est null ou n'a pas ce ID");
      }
      detailsEntre.setBonEntrees(bonEntre);
      detailsEntreRepository.save(detailsEntre);
    }

    return bonEntre;
  }

  @Transactional
  public BonEntrees validerBonEntre(Integer bonEntreId) {
    BonEntrees bonEntre = bonEntreRepository.findById(bonEntreId)
      .orElseThrow(() -> new RuntimeException("BonEntree pas trouver"));

    // Mettre à jour la quantité du produit pour chaque DetailsEntree
    for (DetailsEntrees detailsEntre : bonEntre.getDetailsEntrees()) {
      if (detailsEntre.getProduits() != null && detailsEntre.getProduits().getId() != null) {
        Produits produit = produitRepository.findById(detailsEntre.getProduits().getId())
          .orElseThrow(() -> new RuntimeException("Produit non trouvé avec ce ID: " + detailsEntre.getProduits().getId()));
        // Ajouter la quantité spécifiée à la quantité actuelle du produit
        produit.setQuantite(produit.getQuantite() + detailsEntre.getQuantite());
        produitRepository.save(produit);
      } else {
        throw new RuntimeException("Produit est null ou n'a pas ce ID");
      }
      detailsEntreRepository.save(detailsEntre);
    }

    // Changer le statut à "livré"
    bonEntre.setStatut("livré");
    bonEntre = bonEntreRepository.save(bonEntre);

    return bonEntre;
  }
  // Méthode pour mettre à jour un BonEntre existant
  public BonEntrees updateBonEntree(Integer id, BonEntrees bonEntreeDetails) {
    BonEntrees bonEntree = getBonEntreeById(id);

    bonEntree.setDateCommande(bonEntreeDetails.getDateCommande());
    bonEntree.setPrixTotal(bonEntreeDetails.getPrixTotal());
    bonEntree.setStatut(bonEntreeDetails.getStatut());
    bonEntree.setFournisseurs(bonEntreeDetails.getFournisseurs());
    bonEntree.setDetailsEntrees(bonEntreeDetails.getDetailsEntrees());
    bonEntree.setManager(bonEntreeDetails.getManager());
    bonEntree.setAdmin(bonEntreeDetails.getAdmin());

    return bonEntreRepository.save(bonEntree);
  }

  //Supprimer Un bon Entre
  public void deleteBonEntree(Integer id) {
    BonEntrees bonEntree = getBonEntreeById(id);
    bonEntreRepository.delete(bonEntree);
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
