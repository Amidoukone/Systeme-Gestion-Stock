package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.Fournisseurs;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FournisseursRepository extends JpaRepository<Fournisseurs, Integer> {

  @Query("SELECT COUNT(f) FROM Fournisseurs f")
  int countFournisseurs();



}
