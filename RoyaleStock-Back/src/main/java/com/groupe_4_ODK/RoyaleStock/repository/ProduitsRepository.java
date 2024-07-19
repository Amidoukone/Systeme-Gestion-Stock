package com.groupe_4_ODK.RoyaleStock.repository;

import com.groupe_4_ODK.RoyaleStock.dto.TopEntreeDTO;
import com.groupe_4_ODK.RoyaleStock.dto.TopVenduDTO;
import com.groupe_4_ODK.RoyaleStock.entite.Produits;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitsRepository extends JpaRepository<Produits, Long> {


//    @Query("SELECT new com.groupe_4_ODK.RoyaleStock.dto.TopVenduDTO(p.nom, p.description, COUNT(p.Id)) " +
//      "FROM DetailsSorties d JOIN d.bonSorties b JOIN d.produits p JOIN d.motif m " +
//      "WHERE m.title = 'vente' " +
//      "GROUP BY p.nom, p.description " +
//      "ORDER BY COUNT(p.Id) DESC")
//    List<TopVenduDTO> findTopVendus();
@Query("SELECT new com.groupe_4_ODK.RoyaleStock.dto.TopVenduDTO(p.nom, p.description, COUNT(p.id)) " +
  "FROM DetailsSorties d JOIN d.bonSorties b JOIN d.produits p JOIN d.bonSorties.motif m " +
  "WHERE m.title = 'vente' " +
  "GROUP BY p.nom, p.description " +
  "ORDER BY COUNT(p.Id) DESC")
List<TopVenduDTO> findTopVendus();

  //Entre
  @Query("SELECT new com.groupe_4_ODK.RoyaleStock.dto.TopEntreeDTO(p.nom, p.description, COUNT(p.Id)) " +
    "FROM DetailsEntrees d JOIN d.produits p JOIN d.bonEntrees b " +
    "GROUP BY p.nom, p.description " +
    "ORDER BY COUNT(p.Id) DESC")
  List<TopEntreeDTO> findTopEntrees();

//    List<Produits> findByQuantiteLessThan(int i);
//
//  List<Produits> findTop10BySortieByQuantiteVenduDesc();
}

