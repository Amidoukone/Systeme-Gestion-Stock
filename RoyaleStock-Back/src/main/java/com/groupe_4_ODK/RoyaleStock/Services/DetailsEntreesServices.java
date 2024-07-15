package com.groupe_4_ODK.RoyaleStock.Services;

import com.groupe_4_ODK.RoyaleStock.entite.DetailsEntrees;
import com.groupe_4_ODK.RoyaleStock.Repositories.DetailsEntreesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailsEntreesServices {

  @Autowired
  private DetailsEntreesRepository detailsEntreesRepository;

  public List<DetailsEntrees> findAll() {
    return detailsEntreesRepository.findAll();
  }

  public Optional<DetailsEntrees> findById(Long id) {
    return detailsEntreesRepository.findById(id);
  }

  public DetailsEntrees save(DetailsEntrees detailsEntree) {
    return detailsEntreesRepository.save(detailsEntree);
  }

  public void deleteById(Long id) {
    detailsEntreesRepository.deleteById(id);
  }

  public DetailsEntrees updateDetailsEntree(Long id, DetailsEntrees updatedDetailsEntree) {
    return detailsEntreesRepository.findById(id).map(detailsEntree -> {
      detailsEntree.setDateExpiration(updatedDetailsEntree.getDateExpiration());
      detailsEntree.setBonEntrees(updatedDetailsEntree.getBonEntrees());
      detailsEntree.setProduits(updatedDetailsEntree.getProduits());
      return detailsEntreesRepository.save(detailsEntree);
    }).orElseGet(() -> {
      updatedDetailsEntree.setId(id);
      return detailsEntreesRepository.save(updatedDetailsEntree);
    });
  }
}
