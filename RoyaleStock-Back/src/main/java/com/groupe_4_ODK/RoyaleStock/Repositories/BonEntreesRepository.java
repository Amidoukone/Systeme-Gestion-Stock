package com.groupe_4_ODK.RoyaleStock.Repositories;

import com.groupe_4_ODK.RoyaleStock.entite.BonEntrees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonEntreesRepository extends JpaRepository<BonEntrees, Long> {
}
