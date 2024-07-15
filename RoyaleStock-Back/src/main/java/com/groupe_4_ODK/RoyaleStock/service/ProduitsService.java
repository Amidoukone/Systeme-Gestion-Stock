package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.repository.CategoriesRepository;
import com.groupe_4_ODK.RoyaleStock.repository.ProduitsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProduitsService {

  private final ProduitsRepository produitsRepository;

  public Produits creerProduits(Produits produits) {

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

}
