package com.groupe_4_ODK.RoyaleStock.Services;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import com.groupe_4_ODK.RoyaleStock.Repositories.BonEntreesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BonEntreesServices {

  @Autowired
  private BonEntreesRepository bonEntreesRepository;

  public List<BonEntrees> findAll() {
    return bonEntreesRepository.findAll();
  }

  public Optional<BonEntrees> findById(Long id) {
    return bonEntreesRepository.findById(id);
  }

  public BonEntrees save(BonEntrees bonEntree) {
    return bonEntreesRepository.save(bonEntree);
  }

  public void deleteById(Long id) {
    bonEntreesRepository.deleteById(id);
  }

  public BonEntrees updateBonEntree(Long id, BonEntrees updatedBonEntree) {
    return bonEntreesRepository.findById(id).map(bonEntree -> {
      bonEntree.setDateCommande(updatedBonEntree.getDateCommande());
      bonEntree.setQuantite(updatedBonEntree.getQuantite());
      bonEntree.setPrixTotal(updatedBonEntree.getPrixTotal());
      bonEntree.setStatut(updatedBonEntree.getStatut());
      bonEntree.setFournisseurs(updatedBonEntree.getFournisseurs());
      bonEntree.setDetailsEntrees(updatedBonEntree.getDetailsEntrees());
      return bonEntreesRepository.save(bonEntree);
    }).orElseGet(() -> {
      updatedBonEntree.setId(id);
      return bonEntreesRepository.save(updatedBonEntree);
    });
  }
}
