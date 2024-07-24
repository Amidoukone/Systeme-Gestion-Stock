package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.BonSorties;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsSorties;
import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonSortieRepository extends JpaRepository<BonSorties, Integer> {
//  List<BonSorties> findTop10BySortieByDateSortieDesc();
List<BonSorties> findByEntrepot(Entrepots entrepot);

}
