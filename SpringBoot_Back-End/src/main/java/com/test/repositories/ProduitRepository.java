package com.test.repositories;

import com.test.dto.TopEntreeDTO;
import com.test.dto.TopVenduDTO;
import com.test.entities.Produit;
import com.test.entities.DetailSortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    @Query("SELECT p.categorie.name, COUNT(p) FROM Produit p GROUP BY p.categorie.name")
    List<Object[]> countByCategory();

    /*@Query("SELECT new com.test.dto.TopVenduDTO(p.productName, p.description, COUNT(p.Id)) " +
            "FROM DetailSortie d JOIN d.bonSortie b JOIN d.produit p JOIN d.bonSortie.motif m " +
            "WHERE m.title = 'vente' " +
            "GROUP BY p.productName, p.description " +
            "ORDER BY COUNT(p.Id) DESC")
    List<TopVenduDTO> findTopVendus();

    @Query("SELECT new com.test.dto.TopEntreeDTO(p.productName, p.description, COUNT(p.Id)) " +
            "FROM DetailEntree d JOIN d.produit p JOIN d.bonEntree b " +
            "GROUP BY p.productName, p.description " +
            "ORDER BY COUNT(p.Id) DESC")
    List<TopEntreeDTO> findTopEntrees();*/

    /*Nombre de Produits dune entrepots
    @Query("SELECT COUNT(*) FROM Produit p WHERE p.entrepots.Id = :entrepotId")
    long countProductsByEntrepotId(@Param("entrepotId") Long entrepotId);

    //Liste des produits d'une entrepot
    @Query("SELECT p FROM Produit p WHERE p.entrepots.Id = :entrepotId")
    List<Produit> findProductsByEntrepotId(@Param("entrepotId") Long entrepotId);*/

    Produit findByproductName(String nom);
}
