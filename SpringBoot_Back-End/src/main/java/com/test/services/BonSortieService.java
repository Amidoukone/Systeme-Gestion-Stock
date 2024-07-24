package com.test.services;

import com.test.entities.BonSortie;
import com.test.entities.DetailSortie;
import com.test.entities.Motif;
import com.test.entities.Produit;
import com.test.repositories.BonSortieRepository;
import com.test.repositories.DetailSortieRepository;
import com.test.repositories.MotifRepository;
import com.test.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BonSortieService {

    @Autowired
    private BonSortieRepository bonSortieRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private DetailSortieRepository detailSortieRepository;

    @Autowired
    private MotifRepository motifRepository;

    @Autowired
    private NotificationService notificationService;

    public List<BonSortie> findAll() {
        return bonSortieRepository.findAll();
    }

    public Optional<BonSortie> findById(int id) {
        return bonSortieRepository.findById(id);
    }

    public BonSortie save(BonSortie bonSortie) {
        for (DetailSortie detail : bonSortie.getDetailsSorties()) {
            if (detail.getProduit() != null) {
                Produit managedProduit = produitRepository.findById(detail.getProduit().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                detail.setProduit(managedProduit);
            }
        }
        return bonSortieRepository.save(bonSortie);
    }

    public void deleteById(int id) {
        bonSortieRepository.deleteById(id);
    }

    // Méthode pour créer un nouveau BonSortie
    @Transactional
    public BonSortie createBonSorties(BonSortie bonSortie) {
        try {

            int totalPrix = 0;

            for (DetailSortie detailsSortie : bonSortie.getDetailsSorties()) {
                Produit produit = detailsSortie.getProduit();

                if (produit == null || produit.getId() == 0) {
                    throw new IllegalArgumentException("Produit invalide pour le détail de sortie");
                }

                Produit produit1 = produitRepository.findById(produit.getId()).orElse(null);
                if (produit1 == null) {
                    throw new IllegalArgumentException("Produit non trouvé pour l'ID: " + produit.getId());
                }


                int quantiteSortie = detailsSortie.getQuantity();
                int nouvelleQuantite = produit1.getQuantity() - quantiteSortie;

                if (nouvelleQuantite < 0) {
                    throw new IllegalArgumentException("Quantité insuffisante pour le produit: " + produit1.getProductName());
                }

                /*if (nouvelleQuantite <= 5) {
                    String message = "La quantité du produit " + produit.getNom() + " est maintenant " + nouvelleQuantite + "Pensez à faire une nouvelle commande pour ce produit.";
                    notificationService.sendNotification(methodeUtil.getCurrentUserId(), message);
                    System.out.println("Email Envoye");
                }*/

                produit1.setQuantity(nouvelleQuantite);
                produitRepository.save(produit1);

            }

            bonSortie = bonSortieRepository.save(bonSortie);

            for (DetailSortie detailsSortie : bonSortie.getDetailsSorties()) {
                detailsSortie.setBonSortie(bonSortie);
                detailSortieRepository.save(detailsSortie);
                totalPrix += detailsSortie.getPrix() * detailsSortie.getQuantity();
                detailsSortie.setBonSortie(bonSortie);
            }
            return bonSortie;
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création du bon de sortie", e);
        }
    }

    // Méthode pour mettre à jour un BonSortie existant
    @Transactional
    public BonSortie updateBonSortie(Integer id, BonSortie bonSortie) {
        Optional<BonSortie> existingBonSortieOptional = bonSortieRepository.findById(id);
        if (existingBonSortieOptional.isEmpty()) {
            return null;
        }

        BonSortie existingBonSortie = existingBonSortieOptional.get();
        existingBonSortie.setMotif(bonSortie.getMotif());
        existingBonSortie.setDateSortie(bonSortie.getDateSortie());
        existingBonSortie.setDetailsSorties(bonSortie.getDetailsSorties());

        // Sauvegarder et retourner le BonSortie mis à jour
        return bonSortieRepository.save(existingBonSortie);
    }

    //Produit la plus sortie en fonction du motif
    public Map<String, Map<String, Integer>> getTopProductsByMotif() {
        List<BonSortie> bonSorties = bonSortieRepository.findAll();
        Map<String, Map<String, Integer>> topProductsByMotif = new HashMap<>();

        for (BonSortie bonSortie : bonSorties) {
            Motif motif = bonSortie.getMotif();
            if (motif == null) {
                continue; // ignore les bons de sortie sans motif
            }
            String motifTitle = motif.getTitle();
            Map<String, Integer> productCountMap = topProductsByMotif.getOrDefault(motifTitle, new HashMap<>());

            for (DetailSortie detailsSortie : bonSortie.getDetailsSorties()) {
                Produit produit = detailsSortie.getProduit();
                if (produit != null) {
                    String productName = String.valueOf(produit.getQuantity());
                    productCountMap.put(productName, Integer.valueOf(productCountMap.getOrDefault(productName, 0) + detailsSortie.getQuantity()));
                }
            }

            topProductsByMotif.put(motifTitle, productCountMap);
        }

        return topProductsByMotif;
    }
}
