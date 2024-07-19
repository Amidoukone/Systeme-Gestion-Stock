package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonEntreRepository extends JpaRepository<BonEntrees,Integer> {
//    List<BonEntrees> findTop10ByEntreByDateCommandeDesc();
}
