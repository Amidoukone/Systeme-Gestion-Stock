package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.DetailsEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsSorties;
import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsEntreRepository extends JpaRepository<DetailsEntrees, Integer> {
//  List<DetailsEntrees> findByEntrepot(Entrepots entrepot);

}
