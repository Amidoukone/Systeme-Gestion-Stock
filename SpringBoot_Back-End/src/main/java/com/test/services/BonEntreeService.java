package com.test.services;

import com.test.entities.BonEntree;
import com.test.entities.DetailEntree;
import com.test.entities.Fournisseur;
import com.test.repositories.BonEntreeRepository;
import com.test.repositories.DetailEntreeRepository;
import com.test.repositories.FournisseurRepository;
import com.test.repositories.ProduitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BonEntreeService {

    @Autowired
    private BonEntreeRepository bonEntreeRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private DetailEntreeRepository detailEntreeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    public List<BonEntree> findAll() {
        return bonEntreeRepository.findAll();
    }

    public Optional<BonEntree> findById(int id) {
        return bonEntreeRepository.findById(id);
    }

    @Transactional
    public BonEntree save(BonEntree bonEntree) {
        Fournisseur fournisseur = bonEntree.getFournisseur();
        if (fournisseur != null && fournisseur.getId() == 0) {
            fournisseur = fournisseurRepository.save(fournisseur);
            bonEntree.setFournisseur(fournisseur);
        }
        return bonEntreeRepository.save(bonEntree);
    }

    public void deleteById(int id) {
        bonEntreeRepository.deleteById(id);
    }

}
