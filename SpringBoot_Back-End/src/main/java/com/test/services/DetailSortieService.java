package com.test.services;

import com.test.entities.DetailSortie;
import com.test.entities.Produit;
import com.test.repositories.DetailSortieRepository;
import com.test.repositories.ProduitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailSortieService {

    @Autowired
    private DetailSortieRepository detailSortieRepository;

    @Autowired
    private ProduitRepository produitRepository;
    public List<DetailSortie> findAll() {
        return detailSortieRepository.findAll();
    }

    public Optional<DetailSortie> findById(int id) {
        return detailSortieRepository.findById(id);
    }

    @Transactional
    public DetailSortie save(DetailSortie detailSortie) {
        // Sauvegarder le détail de sortie
        DetailSortie savedDetailSortie = detailSortieRepository.save(detailSortie);

        // Mettre à jour la quantité du produit associé
        Produit produit = detailSortie.getProduit();
        if (produit != null) {
            produit.setQuantity(produit.getQuantity() - detailSortie.getQuantity());
            System.out.println(produit.getQuantity());
            produitRepository.save(produit);
        }

        return savedDetailSortie;
    }

    public void deleteById(int id) {
        detailSortieRepository.deleteById(id);
    }
}
