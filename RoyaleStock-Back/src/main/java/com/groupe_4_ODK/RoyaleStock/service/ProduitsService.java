package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.dto.TopEntreeDTO;
import com.groupe_4_ODK.RoyaleStock.dto.TopVenduDTO;
import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.repository.CategoriesRepository;
import com.groupe_4_ODK.RoyaleStock.repository.EntrepotsRepository;
import com.groupe_4_ODK.RoyaleStock.repository.ProduitsRepository;
import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProduitsService {

  private final ProduitsRepository produitsRepository;

  private UtilisateurRepository utilisateurRepository;
  private EntrepotsRepository entrepotsRepository;

  public Produits createProduit(Produits produit, Long utilisateurId, Long id) throws Exception {
    Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
      .orElseThrow(() -> new Exception("Utilisateur not found"));
    Entrepots entrepot = entrepotsRepository.findById(id)
      .orElseThrow(() -> new Exception("Entrepot not found"));

    produit.setEntrepots(entrepot);
    // Optionally, you can set the user who created it
    // produit.setCreatedBy(utilisateur);

    return produitsRepository.save(produit);
  }
  public List<Produits> getAllProduits() {
    return produitsRepository.findAll();
  }

  public List<Produits> lireProduits() {

    return produitsRepository.findAll();
  }

  public Produits produits(Long id) {
    return produitsRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Produit non trouvée !"));
  }

  /*public Produits produits(String nom) {
    return produitsRepository.findById(nom)
      .orElseThrow(() -> new RuntimeException("Produit non trouvée !"));
  }*/

  public Produits updateProduit(Long id, Produits updatedProduit) throws Exception {
    Produits existingProduit = produitsRepository.findById(id)
      .orElseThrow(() -> new Exception("Produit not found"));

    existingProduit.setNom(updatedProduit.getNom());
    existingProduit.setDescription(updatedProduit.getDescription());
    existingProduit.setPrixAchats(updatedProduit.getPrixAchats());
    existingProduit.setPrixVente(updatedProduit.getPrixVente());
    existingProduit.setQuantite(updatedProduit.getQuantite());
    existingProduit.setDetailsEntrees(updatedProduit.getDetailsEntrees());
    existingProduit.setDetailsSorties(updatedProduit.getDetailsSorties());
    existingProduit.setCategories(updatedProduit.getCategories());
    existingProduit.setEntrepots(updatedProduit.getEntrepots());

    return produitsRepository.save(existingProduit);
  }

  public String supprimerProduit(Long id) {
    produitsRepository.deleteById(id);
    return "Produit Supprimé !";
  }

  public List<Produits> getProduitsByEntrepot(Long entrepotId) {
    return produitsRepository.findByEntrepotsId(entrepotId);
  }
  public Optional<Produits> getProduitById(Long id) {
    return produitsRepository.findById(id);
  }
  // Produit vendu le plus
  public List<TopVenduDTO> getTopVendus() {
    return produitsRepository.findTopVendus();
  }
  //Entre
  public List<TopEntreeDTO> getTopEntrees() {
    return produitsRepository.findTopEntrees();
  }

}
