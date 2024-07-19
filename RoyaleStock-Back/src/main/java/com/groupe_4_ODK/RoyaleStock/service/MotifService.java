package com.groupe_4_ODK.RoyaleStock.service;

import com.groupe_4_ODK.RoyaleStock.entite.Categories;
import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import com.groupe_4_ODK.RoyaleStock.entite.Motif;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.exception.MotifNotFoundException;

import com.groupe_4_ODK.RoyaleStock.repository.MotifRepository;
import com.groupe_4_ODK.RoyaleStock.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotifService {
  @Autowired
  private MotifRepository motifRepository;
  @Autowired
  private MethodeUtil methodeUtil;



  public Motif createMotif(Motif motif) {
    Motif existingMotif = motifRepository.findByTitle(motif.getTitle());
    if (existingMotif != null) {
      return existingMotif;
    }

    Long currentUserId = methodeUtil.getCurrentUserId();
    if (currentUserId == null) {
      throw new RuntimeException("Utilisateur non trouvé ou non authentifié");
    }

    Entrepots entrepot = methodeUtil.getEntrepotByUserId(currentUserId);
    if (entrepot == null) {
      throw new RuntimeException("Utilisateur n'est associé à aucun entrepôt");
    }

    motif.setCreateBy(currentUserId);
    motif.setEntrepot(entrepot);
    return motifRepository.save(motif);
  }

  public List<Motif> getAllMotifs() {
    return motifRepository.findAll();
  }

  public Optional<Motif> getMotifById(int id) {
    Optional<Motif> existMotif = motifRepository.findById(id);
    if (existMotif.isPresent()) {
      throw new MotifNotFoundException(String.format("motif id %s nest pas trouve" + id));
    }
    return motifRepository.findById(id);
  }

  public Motif updateMotif(Motif motif, int id) {
    Optional<Motif> existMotif = motifRepository.findById(id);
    if (existMotif.isPresent()) {
      throw new MotifNotFoundException(String.format("motif id %s nest pas trouve" + id));
    }
    return motifRepository.save(motif);
  }

  public void deleteMotif(int id) {
    Optional<Motif> existMotif = motifRepository.findById(id);
    if (existMotif.isPresent()) {
      throw new MotifNotFoundException(String.format("motif id %s nest pas trouve" + id));
    }
    motifRepository.deleteById(id);
  }

  public int getNombreMotif() {
  return motifRepository.countMotifs();
  }
}
