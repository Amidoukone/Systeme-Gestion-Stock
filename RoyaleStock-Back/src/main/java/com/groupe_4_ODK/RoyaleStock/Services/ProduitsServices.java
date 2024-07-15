package com.groupe_4_ODK.RoyaleStock.Services;

import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.Repositories.ProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitsServices {

  @Autowired
  private ProduitsRepository produitsRepository;

  public List<Produits> findAll() {
    return produitsRepository.findAll();
  }

  public Optional<Produits> findById(Long id) {
    return produitsRepository.findById(id);
  }

  public Produits save(Produits produit) {
    return produitsRepository.save(produit);
  }

  public void deleteById(Long id) {
    produitsRepository.deleteById(id);
  }

  public Produits updateProduit(Long id, Produits updatedProduit) {
    return produitsRepository.findById(id).map(produit -> {
      produit.setNom(updatedProduit.getNom());
      produit.setDescription(updatedProduit.getDescription());
      produit.setPrixAchats(updatedProduit.getPrixAchats());
      produit.setPrixVente(updatedProduit.getPrixVente());
      produit.setQuantite(updatedProduit.getQuantite());
      produit.setCategories(updatedProduit.getCategories());
      produit.setEntrepots(updatedProduit.getEntrepots());
      return produitsRepository.save(produit);
    }).orElseGet(() -> {
      updatedProduit.setId(id);
      return produitsRepository.save(updatedProduit);
    });
  }
}
