package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.dto.TopEntreeDTO;
import com.groupe_4_ODK.RoyaleStock.dto.TopVenduDTO;
import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.repository.CategoriesRepository;
import com.groupe_4_ODK.RoyaleStock.repository.ProduitsRepository;
import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProduitsService {

  private final ProduitsRepository produitsRepository;
  private final MethodeUtil methodeUtil;

  public Produits creerProduits(Produits produits) {
    Produits existingProduit = produitsRepository.findByNom(produits.getNom());

    if (existingProduit != null) {
      return existingProduit;
    }

    Long currentUserId = methodeUtil.getCurrentUserId();
    if (currentUserId == null) {
      throw new RuntimeException("Utilisateur non trouvé ou non authentifié");
    }

    Entrepots entrepot = methodeUtil.getEntrepotByUserId(currentUserId);
    if (entrepot == null) {
      throw new RuntimeException("Utilisateur n'est associé à aucun entrepôt");
    }

    produits.setCreateBy(currentUserId);
    produits.setEntrepots(entrepot);
    return produitsRepository.save(produits);
  }

  public List<Produits> lireProduits() {

    return produitsRepository.findAll();
  }

  public Produits produits(Long id) {
    return produitsRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Produit non trouvée !"));
  }

  public Produits modifierProduits(Long id, Produits produits) {
    return produitsRepository.findById(id)
      .map(p-> {
        p.setNom(produits.getNom());
        p.setDescription(produits.getDescription());
        p.setPrixAchats(produits.getPrixAchats());
        p.setPrixVente(produits.getPrixVente());
        p.setQuantite(produits.getQuantite());
        return produitsRepository.save(p);
      }).orElseThrow(() -> new RuntimeException("Produit non trouvé !"));
  }

  public String supprimerProduit(Long id) {
    produitsRepository.deleteById(id);
    return "Produit Supprimé !";
  }

  public List<TopVenduDTO> getTopVendus() {
    return produitsRepository.findTopVendus();
  }
  //Entre
  public List<TopEntreeDTO> getTopEntrees() {
    return produitsRepository.findTopEntrees();
  }

  public long countProductsByEntrepotId(Long entrepotId) {
    return produitsRepository.countProductsByEntrepotId(entrepotId);
  }

  public List<Produits> findProductsByEntrepotId(Long entrepotId) {
    return produitsRepository.findProductsByEntrepotId(entrepotId);
  }
}
