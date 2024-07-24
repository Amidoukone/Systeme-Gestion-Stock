package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.entite.DetailsEntrees;
import com.groupe_4_ODK.RoyaleStock.entite.DetailsSorties;
import com.groupe_4_ODK.RoyaleStock.entite.Entrepots;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailsSortieRepository extends JpaRepository<DetailsSorties,Integer> {

}
