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

    //Creer un bon Entre
    @Transactional
    public BonEntree createBonEntrees(BonEntree bonEntre) {

        // Sauvegarder le BonEntree sans mettre à jour les quantités
        bonEntre = bonEntreeRepository.save(bonEntre);

        int prixTotal = 0;

        // Associer les détails du BonEntree
        if (bonEntre.getDetailsEntrees() != null) {
            System.out.println("La liste des détails d'entrées n'est nulle");
            for (DetailEntree detailsEntre : bonEntre.getDetailsEntrees()) {
                detailsEntre.setBonEntree(bonEntre);
                detailEntreeRepository.save(detailsEntre);
            }
            // Sauvegarder le BonEntree mis à jour
            bonEntre = bonEntreeRepository.save(bonEntre);
            return bonEntre;
        } else {
            throw new RuntimeException("La liste des détails d'entrées est nulle");
        }
    }
}
